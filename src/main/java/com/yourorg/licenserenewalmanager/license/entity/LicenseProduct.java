package com.yourorg.licenserenewalmanager.license.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "license_products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LicenseProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;           // e.g. "Microsoft 365 E3"

    private String vendorName;     // e.g. "Microsoft"
    private String category;       // e.g. "Office Suite"
    private Integer defaultValidityMonths;
    private String description;
}