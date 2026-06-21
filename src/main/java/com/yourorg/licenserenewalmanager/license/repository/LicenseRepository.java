package com.yourorg.licenserenewalmanager.license.repository;

import com.yourorg.licenserenewalmanager.license.entity.License;
import com.yourorg.licenserenewalmanager.license.enums.LicenseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LicenseRepository extends JpaRepository<License, Long> {

    List<License> findByExpiryDateBetweenAndStatusIn(
            LocalDate from,
            LocalDate to,
            List<LicenseStatus> statuses
    );

    @Query("SELECT l FROM License l LEFT JOIN l.product p LEFT JOIN l.department d WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(p.vendorName) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(l.licenseKeyOrContractId) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(d.name) LIKE LOWER(CONCAT('%', :q, '%'))")
    List<License> searchByKeyword(@Param("q") String q);
}