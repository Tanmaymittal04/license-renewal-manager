package com.yourorg.licenserenewalmanager.license.repository;

import com.yourorg.licenserenewalmanager.license.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}