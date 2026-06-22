package com.yourorg.licenserenewalmanager.license.dto;

import com.yourorg.licenserenewalmanager.license.enums.BillingCycle;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LicenseRequestDto {

    private Long productId;
    private Long departmentId;
    private String licenseKeyOrContractId;
    private Integer seatsPurchased;
    private Integer seatsUsed;
    private LocalDate startDate;
    private LocalDate expiryDate;
    private BillingCycle billingCycle;
    private Boolean autoRenew;
    private BigDecimal costPerCycle;
    private String currency;
    private Integer tenure;
    private String vendorName;

    public LicenseRequestDto() {
    }

    public LicenseRequestDto(Long productId,
                             Long departmentId,
                             String licenseKeyOrContractId,
                             Integer seatsPurchased,
                             Integer seatsUsed,
                             LocalDate startDate,
                             LocalDate expiryDate,
                             BillingCycle billingCycle,
                             Boolean autoRenew,
                             BigDecimal costPerCycle,
                             String currency,
                             Integer tenure,
                             String vendorName) {
        this.productId = productId;
        this.departmentId = departmentId;
        this.licenseKeyOrContractId = licenseKeyOrContractId;
        this.seatsPurchased = seatsPurchased;
        this.seatsUsed = seatsUsed;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.billingCycle = billingCycle;
        this.autoRenew = autoRenew;
        this.costPerCycle = costPerCycle;
        this.currency = currency;
        this.tenure = tenure;
        this.vendorName = vendorName;
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

    public Integer getTenure() {
        return tenure;
    }

    public void setTenure(Integer tenure) {
        this.tenure = tenure;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
}