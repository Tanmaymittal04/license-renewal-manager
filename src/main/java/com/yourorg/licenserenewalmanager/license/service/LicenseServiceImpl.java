package com.yourorg.licenserenewalmanager.license.service;

import com.yourorg.licenserenewalmanager.common.exception.NotFoundException;
import com.yourorg.licenserenewalmanager.license.dto.LicenseRequestDto;
import com.yourorg.licenserenewalmanager.license.dto.LicenseResponseDto;
import com.yourorg.licenserenewalmanager.license.entity.Department;
import com.yourorg.licenserenewalmanager.license.entity.License;
import com.yourorg.licenserenewalmanager.license.entity.LicenseProduct;
import com.yourorg.licenserenewalmanager.license.enums.LicenseStatus;
import com.yourorg.licenserenewalmanager.license.mapper.LicenseMapper;
import com.yourorg.licenserenewalmanager.license.repository.DepartmentRepository;
import com.yourorg.licenserenewalmanager.license.repository.LicenseProductRepository;
import com.yourorg.licenserenewalmanager.license.repository.LicenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class LicenseServiceImpl implements LicenseService {

    private final LicenseRepository licenseRepository;
    private final LicenseProductRepository productRepository;
    private final DepartmentRepository departmentRepository;

    public LicenseServiceImpl(LicenseRepository licenseRepository,
                              LicenseProductRepository productRepository,
                              DepartmentRepository departmentRepository) {
        this.licenseRepository = licenseRepository;
        this.productRepository = productRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public LicenseResponseDto create(LicenseRequestDto request) {
        LicenseProduct product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        Department department = null;
        if (request.getDepartmentId() != null) {
            department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new NotFoundException("Department not found"));
        }

        License license = LicenseMapper.toEntity(request, product, department);
        License saved = licenseRepository.save(license);
        return LicenseMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public LicenseResponseDto getById(Long id) {
        License license = licenseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("License not found"));
        return LicenseMapper.toDto(license);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LicenseResponseDto> getAll() {
        return licenseRepository.findAll().stream()
                .map(LicenseMapper::toDto)
                .toList();
    }

    @Override
    public LicenseResponseDto update(Long id, LicenseRequestDto request) {
        License existing = licenseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("License not found"));

        LicenseProduct product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        Department department = null;
        if (request.getDepartmentId() != null) {
            department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new NotFoundException("Department not found"));
        }

        existing.setProduct(product);
        existing.setDepartment(department);
        existing.setLicenseKeyOrContractId(request.getLicenseKeyOrContractId());
        existing.setSeatsPurchased(request.getSeatsPurchased());
        existing.setSeatsUsed(request.getSeatsUsed());
        existing.setStartDate(request.getStartDate());
        existing.setExpiryDate(request.getExpiryDate());
        existing.setBillingCycle(request.getBillingCycle());
        existing.setAutoRenew(request.getAutoRenew());
        existing.setCostPerCycle(request.getCostPerCycle());
        existing.setCurrency(request.getCurrency());

        return LicenseMapper.toDto(existing);
    }

    @Override
    public void delete(Long id) {
        License license = licenseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("License not found"));
        license.setStatus(LicenseStatus.CANCELLED);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LicenseResponseDto> getExpiringBetween(LocalDate from, LocalDate to) {
        List<LicenseStatus> statuses = List.of(LicenseStatus.ACTIVE, LicenseStatus.GRACE_PERIOD);
        return licenseRepository
                .findByExpiryDateBetweenAndStatusIn(from, to, statuses)
                .stream()
                .map(LicenseMapper::toDto)
                .toList();
    }
}