package com.yourorg.licenserenewalmanager.license.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpcomingRenewalViewDto {

    private Long id;
    private String productName;
    private String vendorName;
    private String departmentName;
    private LocalDate expiryDate;
    private Integer seatsPurchased;
    private BigDecimal costPerCycle;
    private String currency;

    public UpcomingRenewalViewDto() {
    }

    public UpcomingRenewalViewDto(Long id,
                                  String productName,
                                  String vendorName,
                                  String departmentName,
                                  LocalDate expiryDate,
                                  Integer seatsPurchased,
                                  BigDecimal costPerCycle,
                                  String currency) {
        this.id = id;
        this.productName = productName;
        this.vendorName = vendorName;
        this.departmentName = departmentName;
        this.expiryDate = expiryDate;
        this.seatsPurchased = seatsPurchased;
        this.costPerCycle = costPerCycle;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getSeatsPurchased() {
        return seatsPurchased;
    }

    public void setSeatsPurchased(Integer seatsPurchased) {
        this.seatsPurchased = seatsPurchased;
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