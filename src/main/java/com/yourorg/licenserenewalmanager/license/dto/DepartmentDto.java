package com.yourorg.licenserenewalmanager.license.dto;

public class DepartmentDto {

    private Long id;
    private String name;
    private String costCenter;

    public DepartmentDto() {
    }

    public DepartmentDto(Long id, String name, String costCenter) {
        this.id = id;
        this.name = name;
        this.costCenter = costCenter;
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

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }
}