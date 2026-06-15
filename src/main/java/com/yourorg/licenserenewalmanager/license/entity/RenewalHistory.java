package com.yourorg.licenserenewalmanager.license.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "renewal_history")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RenewalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "license_id")
    private License license;

    private LocalDate oldExpiryDate;
    private LocalDate newExpiryDate;
    private LocalDate renewalDate;

    private BigDecimal amountPaid;

    @Column(length = 1000)
    private String notes;
}