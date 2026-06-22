package com.yourorg.licenserenewalmanager.license.entity;

import com.yourorg.licenserenewalmanager.license.enums.BillingCycle;
import com.yourorg.licenserenewalmanager.license.enums.LicenseStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "licenses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private LicenseProduct product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(unique = true, nullable = true)
    private String licenseKeyOrContractId;

    private Integer seatsPurchased;
    private Integer seatsUsed;

    private LocalDate purchaseDate;
    private LocalDate startDate;
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    private BillingCycle billingCycle;

    private Boolean autoRenew;

    @Enumerated(EnumType.STRING)
    private LicenseStatus status;

    private BigDecimal costPerCycle;
    private String currency;

    private Integer tenure; // in months, nullable

    @Column(name = "vendor_name", length = 100)
    private String vendorName;
}