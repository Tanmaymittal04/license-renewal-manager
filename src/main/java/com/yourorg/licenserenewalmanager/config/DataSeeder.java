package com.yourorg.licenserenewalmanager.config;

import com.yourorg.licenserenewalmanager.license.entity.AppUser;
import com.yourorg.licenserenewalmanager.license.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedAdminUser(UserRepository userRepository,
                                           PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("admin@licenseops.in").isEmpty()) {
                AppUser admin = AppUser.builder()
                        .fullName("Tanmay Mittal")
                        .email("admin@licenseops.in")
                        .password(passwordEncoder.encode("Admin@1234"))
                        .role("ROLE_ADMIN")
                        .enabled(true)
                        .build();
                userRepository.save(admin);
                System.out.println(">>> Default admin user seeded: admin@licenseops.in / Admin@1234");
            }
        };
    }
}