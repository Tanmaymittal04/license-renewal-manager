package com.yourorg.licenserenewalmanager.config;

import com.yourorg.licenserenewalmanager.license.service.AppUserDetailsService;
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AppUserDetailsService userDetailsService;

    public SecurityConfig(AppUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests(auth -> auth
                        // ✅ Must be FIRST — allow Spring's internal JSP FORWARD dispatches
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                        // ✅ Public static resources and login/error pages
                        .requestMatchers(
                                "/css/**", "/js/**", "/images/**",
                                "/ui/login", "/ui/login-error",
                                "/error"
                        ).permitAll()
                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/ui/login")           // GET — show custom login JSP
                        .loginProcessingUrl("/ui/login")  // POST — Spring Security processes credentials
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/ui/dashboard", true)
                        .failureUrl("/ui/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/ui/logout")
                        .logoutSuccessUrl("/ui/login?logout=true")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")      // ✅ Clear session cookie on logout
                        .permitAll()
                )
                .csrf(Customizer.withDefaults());     // CSRF enabled — JSP forms include ${_csrf} token

        return http.build();
    }
}