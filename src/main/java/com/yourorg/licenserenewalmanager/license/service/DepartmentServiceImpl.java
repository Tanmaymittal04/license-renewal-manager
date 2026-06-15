package com.yourorg.licenserenewalmanager.license.service;

import com.yourorg.licenserenewalmanager.common.exception.NotFoundException;
import com.yourorg.licenserenewalmanager.license.dto.DepartmentDto;
import com.yourorg.licenserenewalmanager.license.entity.Department;
import com.yourorg.licenserenewalmanager.license.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDto> getAll() {
        return departmentRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentDto getById(Long id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department not found"));
        return toDto(dept);
    }

    @Override
    public DepartmentDto create(DepartmentDto dto) {
        Department dept = new Department();
        dept.setName(dto.getName());
        dept.setCostCenter(dto.getCostCenter());

        Department saved = departmentRepository.save(dept);
        return toDto(saved);
    }

    @Override
    public DepartmentDto update(Long id, DepartmentDto dto) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department not found"));

        dept.setName(dto.getName());
        dept.setCostCenter(dto.getCostCenter());

        return toDto(dept);
    }

    private DepartmentDto toDto(Department entity) {
        return new DepartmentDto(
                entity.getId(),
                entity.getName(),
                entity.getCostCenter()
        );
    }
}