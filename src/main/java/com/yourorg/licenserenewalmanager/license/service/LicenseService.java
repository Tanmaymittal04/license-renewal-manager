package com.yourorg.licenserenewalmanager.license.service;

import com.yourorg.licenserenewalmanager.license.dto.LicenseRequestDto;
import com.yourorg.licenserenewalmanager.license.dto.LicenseResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface LicenseService {

    LicenseResponseDto create(LicenseRequestDto request);

    LicenseResponseDto getById(Long id);

    List<LicenseResponseDto> getAll();

    LicenseResponseDto update(Long id, LicenseRequestDto request);

    void delete(Long id);

    List<LicenseResponseDto> getExpiringBetween(LocalDate from, LocalDate to);
}