package com.yourorg.licenserenewalmanager.license.repository;

import com.yourorg.licenserenewalmanager.license.entity.LicenseProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LicenseProductRepository extends JpaRepository<LicenseProduct, Long> {
    Optional<LicenseProduct> findByName(String name);
}