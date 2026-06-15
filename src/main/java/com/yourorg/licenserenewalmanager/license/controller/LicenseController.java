package com.yourorg.licenserenewalmanager.license.controller;

import com.yourorg.licenserenewalmanager.license.dto.LicenseRequestDto;
import com.yourorg.licenserenewalmanager.license.dto.LicenseResponseDto;
import com.yourorg.licenserenewalmanager.license.service.LicenseService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/licenses")
public class LicenseController {

    private final LicenseService licenseService;

    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @PostMapping
    public ResponseEntity<LicenseResponseDto> create(@Valid @RequestBody LicenseRequestDto request) {
        return ResponseEntity.ok(licenseService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LicenseResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(licenseService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<LicenseResponseDto>> getAll() {
        return ResponseEntity.ok(licenseService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LicenseResponseDto> update(@PathVariable Long id,
                                                     @Valid @RequestBody LicenseRequestDto request) {
        return ResponseEntity.ok(licenseService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        licenseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/expiring")
    public ResponseEntity<List<LicenseResponseDto>> getExpiringBetween(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(licenseService.getExpiringBetween(from, to));
    }
}