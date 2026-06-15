package com.yourorg.licenserenewalmanager.license.dto;

public class ProductDto {

    private Long id;
    private String name;
    private String vendorName;
    private String category;
    private Integer defaultValidityMonths;
    private String description;

    public ProductDto() {
    }

    public ProductDto(Long id,
                      String name,
                      String vendorName,
                      String category,
                      Integer defaultValidityMonths,
                      String description) {
        this.id = id;
        this.name = name;
        this.vendorName = vendorName;
        this.category = category;
        this.defaultValidityMonths = defaultValidityMonths;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getDefaultValidityMonths() {
        return defaultValidityMonths;
    }

    public void setDefaultValidityMonths(Integer defaultValidityMonths) {
        this.defaultValidityMonths = defaultValidityMonths;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}