package com.yourorg.licenserenewalmanager.license.service;

import com.yourorg.licenserenewalmanager.license.dto.DashboardMetricsDto;
import com.yourorg.licenserenewalmanager.license.dto.UpcomingRenewalViewDto;
import com.yourorg.licenserenewalmanager.license.dto.VendorSummaryDto;
import com.yourorg.licenserenewalmanager.license.entity.License;
import com.yourorg.licenserenewalmanager.license.entity.LicenseProduct;
import com.yourorg.licenserenewalmanager.license.enums.LicenseStatus;
import com.yourorg.licenserenewalmanager.license.repository.LicenseProductRepository;
import com.yourorg.licenserenewalmanager.license.repository.LicenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final LicenseRepository licenseRepository;
    private final LicenseProductRepository productRepository;

    public DashboardServiceImpl(LicenseRepository licenseRepository,
                                LicenseProductRepository productRepository) {
        this.licenseRepository = licenseRepository;
        this.productRepository = productRepository;
    }

    @Override
    public DashboardMetricsDto getDashboardMetrics() {
        List<License> all = licenseRepository.findAll();

        int activeLicenses = (int) all.stream()
                .filter(l -> l.getStatus() == LicenseStatus.ACTIVE)
                .count();

        LocalDate now = LocalDate.now();
        LocalDate in30 = now.plusDays(30);
        int expiringSoon = (int) all.stream()
                .filter(l -> l.getStatus() == LicenseStatus.ACTIVE
                        || l.getStatus() == LicenseStatus.GRACE_PERIOD)
                .filter(l -> l.getExpiryDate() != null
                        && !l.getExpiryDate().isBefore(now)
                        && !l.getExpiryDate().isAfter(in30))
                .count();

        BigDecimal annualSpend = all.stream()
                .filter(l -> l.getCostPerCycle() != null)
                .map(License::getCostPerCycle)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalSeats = all.stream()
                .filter(l -> l.getSeatsPurchased() != null)
                .mapToInt(License::getSeatsPurchased)
                .sum();
        int usedSeats = all.stream()
                .filter(l -> l.getSeatsUsed() != null)
                .mapToInt(License::getSeatsUsed)
                .sum();

        BigDecimal utilizationPercent = BigDecimal.ZERO;
        if (totalSeats > 0) {
            utilizationPercent = BigDecimal.valueOf(usedSeats * 100.0 / totalSeats)
                    .setScale(1, RoundingMode.HALF_UP);
        }

        // For now, static 0 or compute vs previous period later
        BigDecimal activeGrowth = BigDecimal.ZERO;

        return new DashboardMetricsDto(
                activeLicenses,
                expiringSoon,
                annualSpend,
                utilizationPercent,
                activeGrowth
        );
    }

    @Override
    public List<UpcomingRenewalViewDto> getUpcomingRenewals(int daysAhead) {
        LocalDate now = LocalDate.now();
        LocalDate to = now.plusDays(daysAhead);
        List<LicenseStatus> statuses = List.of(LicenseStatus.ACTIVE, LicenseStatus.GRACE_PERIOD);

        return licenseRepository
                .findByExpiryDateBetweenAndStatusIn(now, to, statuses)
                .stream()
                .sorted(Comparator.comparing(License::getExpiryDate))
                .map(l -> new UpcomingRenewalViewDto(
                        l.getId(),
                        l.getProduct() != null ? l.getProduct().getName() : null,
                        l.getProduct() != null ? l.getProduct().getVendorName() : null,
                        l.getDepartment() != null ? l.getDepartment().getName() : null,
                        l.getExpiryDate(),
                        l.getSeatsPurchased(),
                        l.getCostPerCycle(),
                        l.getCurrency()
                ))
                .toList();
    }

    @Override
    public List<VendorSummaryDto> getVendorSummary() {
        List<License> all = licenseRepository.findAll();
        BigDecimal totalSpend = all.stream()
                .filter(l -> l.getCostPerCycle() != null)
                .map(License::getCostPerCycle)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, BigDecimal> spendByVendor = new HashMap<>();
        Map<String, Integer> productCountByVendor = new HashMap<>();

        for (License license : all) {
            LicenseProduct product = license.getProduct();
            if (product == null || license.getCostPerCycle() == null) continue;

            String vendor = Optional.ofNullable(product.getVendorName()).orElse("Unknown");
            spendByVendor.merge(vendor, license.getCostPerCycle(), BigDecimal::add);
            productCountByVendor.merge(vendor, 1, Integer::sum);
        }

        return spendByVendor.entrySet().stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                .limit(5)
                .map(entry -> {
                    String vendor = entry.getKey();
                    BigDecimal spend = entry.getValue();
                    BigDecimal share = BigDecimal.ZERO;
                    if (totalSpend.compareTo(BigDecimal.ZERO) > 0) {
                        share = spend.multiply(BigDecimal.valueOf(100))
                                .divide(totalSpend, 1, RoundingMode.HALF_UP);
                    }
                    int productCount = productCountByVendor.getOrDefault(vendor, 0);
                    return new VendorSummaryDto(vendor, productCount, spend, "INR", share);
                })
                .collect(Collectors.toList());
    }
}