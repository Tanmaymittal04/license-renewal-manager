package com.yourorg.licenserenewalmanager.license.service;

import com.yourorg.licenserenewalmanager.license.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {

    List<DepartmentDto> getAll();

    DepartmentDto getById(Long id);

    DepartmentDto create(DepartmentDto dto);

    DepartmentDto update(Long id, DepartmentDto dto);
}