package com.yourorg.licenserenewalmanager.license.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;   // BCrypt hash

    @Column(nullable = false)
    private String role;       // e.g. ROLE_ADMIN, ROLE_USER

    @Column(nullable = false)
    private boolean enabled = true;
}
