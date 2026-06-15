package com.yourorg.licenserenewalmanager.license.dto;

import com.yourorg.licenserenewalmanager.license.enums.BillingCycle;
import com.yourorg.licenserenewalmanager.license.enums.LicenseStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LicenseResponseDto {

    private Long id;
    private Long productId;
    private Long departmentId;
    private String productName;
    private String vendorName;
    private String departmentName;
    private String licenseKeyOrContractId;
    private Integer seatsPurchased;
    private Integer seatsUsed;
    private LocalDate startDate;
    private LocalDate expiryDate;
    private BillingCycle billingCycle;
    private Boolean autoRenew;
    private LicenseStatus status;
    private BigDecimal costPerCycle;
    private String currency;

    public LicenseResponseDto() {
    }

    public LicenseResponseDto(Long id,
                              Long productId,
                              Long departmentId,
                              String productName,
                              String vendorName,
                              String departmentName,
                              String licenseKeyOrContractId,
                              Integer seatsPurchased,
                              Integer seatsUsed,
                              LocalDate startDate,
                              LocalDate expiryDate,
                              BillingCycle billingCycle,
                              Boolean autoRenew,
                              LicenseStatus status,
                              BigDecimal costPerCycle,
                              String currency) {
        this.id = id;
        this.productId = productId;
        this.departmentId = departmentId;
        this.productName = productName;
        this.vendorName = vendorName;
        this.departmentName = departmentName;
        this.licenseKeyOrContractId = licenseKeyOrContractId;
        this.seatsPurchased = seatsPurchased;
        this.seatsUsed = seatsUsed;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.billingCycle = billingCycle;
        this.autoRenew = autoRenew;
        this.status = status;
        this.costPerCycle = costPerCycle;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getLicenseKeyOrContractId() {
        return licenseKeyOrContractId;
    }

    public void setLicenseKeyOrContractId(String licenseKeyOrContractId) {
        this.licenseKeyOrContractId = licenseKeyOrContractId;
    }

    public Integer getSeatsPurchased() {
        return seatsPurchased;
    }

    public void setSeatsPurchased(Integer seatsPurchased) {
        this.seatsPurchased = seatsPurchased;
    }

    public Integer getSeatsUsed() {
        return seatsUsed;
    }

    public void setSeatsUsed(Integer seatsUsed) {
        this.seatsUsed = seatsUsed;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public BillingCycle getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(BillingCycle billingCycle) {
        this.billingCycle = billingCycle;
    }

    public Boolean getAutoRenew() {
        return autoRenew;
    }

    public void setAutoRenew(Boolean autoRenew) {
        this.autoRenew = autoRenew;
    }

    public LicenseStatus getStatus() {
        return status;
    }

    public void setStatus(LicenseStatus status) {
        this.status = status;
    }

    public BigDecimal getCostPerCycle() {
        return costPerCycle;
    }

    public void setCostPerCycle(BigDecimal costPerCycle) {
        this.costPerCycle = costPerCycle;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}