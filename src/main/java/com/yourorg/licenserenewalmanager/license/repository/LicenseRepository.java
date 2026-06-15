package com.yourorg.licenserenewalmanager.license.repository;

import com.yourorg.licenserenewalmanager.license.entity.License;
import com.yourorg.licenserenewalmanager.license.enums.LicenseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LicenseRepository extends JpaRepository<License, Long> {

    List<License> findByExpiryDateBetweenAndStatusIn(
            LocalDate from,
            LocalDate to,
            List<LicenseStatus> statuses
    );
}