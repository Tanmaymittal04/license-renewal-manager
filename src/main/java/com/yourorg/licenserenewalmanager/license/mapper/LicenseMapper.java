package com.yourorg.licenserenewalmanager.license.mapper;

import com.yourorg.licenserenewalmanager.license.dto.LicenseRequestDto;
import com.yourorg.licenserenewalmanager.license.dto.LicenseResponseDto;
import com.yourorg.licenserenewalmanager.license.entity.Department;
import com.yourorg.licenserenewalmanager.license.entity.License;
import com.yourorg.licenserenewalmanager.license.entity.LicenseProduct;
import com.yourorg.licenserenewalmanager.license.enums.LicenseStatus;

public class LicenseMapper {

    public static License toEntity(LicenseRequestDto dto,
                                   LicenseProduct product,
                                   Department department) {
        License license = new License();
        license.setProduct(product);
        license.setDepartment(department);
        license.setLicenseKeyOrContractId(dto.getLicenseKeyOrContractId());
        license.setSeatsPurchased(dto.getSeatsPurchased());
        license.setSeatsUsed(dto.getSeatsUsed());
        license.setStartDate(dto.getStartDate());
        license.setExpiryDate(dto.getExpiryDate());
        license.setBillingCycle(dto.getBillingCycle());
        license.setAutoRenew(dto.getAutoRenew());
        license.setCostPerCycle(dto.getCostPerCycle());
        license.setCurrency(dto.getCurrency());
        license.setStatus(LicenseStatus.ACTIVE);
        return license;
    }

    public static LicenseResponseDto toDto(License license) {
        return new LicenseResponseDto(
                license.getId(),
                license.getProduct() != null ? license.getProduct().getId() : null,
                license.getDepartment() != null ? license.getDepartment().getId() : null,
                license.getProduct() != null ? license.getProduct().getName() : null,
                license.getProduct() != null ? license.getProduct().getVendorName() : null,
                license.getDepartment() != null ? license.getDepartment().getName() : null,
                license.getLicenseKeyOrContractId(),
                license.getSeatsPurchased(),
                license.getSeatsUsed(),
                license.getStartDate(),
                license.getExpiryDate(),
                license.getBillingCycle(),
                license.getAutoRenew(),
                license.getStatus(),
                license.getCostPerCycle(),
                license.getCurrency()
        );
    }
}