package com.medi360.config;

import com.medi360.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeExchange(exchange -> exchange

                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .pathMatchers("/api/auth/**").permitAll()
                        .pathMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .pathMatchers("/user/**").permitAll()

                        .pathMatchers("/notification/**").authenticated()

                        .pathMatchers("/api/patient/**").hasAnyRole("ADMIN", "RECEPTIONIST", "NURSE", "DOCTOR")

                        .pathMatchers(HttpMethod.POST, "/api/doctor/add").permitAll()
                        .pathMatchers("/api/doctor/**").hasAnyRole("ADMIN", "DOCTOR", "RECEPTIONIST")

                        .pathMatchers("/api/ward/**").hasAnyRole("ADMIN", "NURSE")

                        .pathMatchers("/api/beds/**").hasAnyRole("ADMIN", "NURSE", "COMPLIANCE_OFFICER")

                        .pathMatchers("/api/appointment/**").hasAnyRole("ADMIN", "DOCTOR", "RECEPTIONIST", "COMPLIANCE_OFFICER")

                        .pathMatchers("/api/compliance-reports/**").hasAnyRole("ADMIN", "COMPLIANCE_OFFICER")

                        .pathMatchers("/api/kpi-report/**").hasAnyRole("ADMIN", "COMPLIANCE_OFFICER", "FINANCEOFFICER")

                        .pathMatchers("/api/invoice/**").hasAnyRole("ADMIN", "FINANCEOFFICER")

                        .pathMatchers("/api/patientbilling/**").hasAnyRole("ADMIN", "FINANCEOFFICER")

                        .pathMatchers("/api/insurance/**").hasAnyRole("ADMIN", "FINANCEOFFICER", "COMPLIANCE_OFFICER")

                        .pathMatchers("/api/notification/**").authenticated()

                        .pathMatchers("/api/vitals/**").hasAnyRole("ADMIN", "NURSE", "DOCTOR")
                        .pathMatchers("/api/care-notes/**").hasAnyRole("ADMIN", "NURSE", "DOCTOR")

                        .pathMatchers("/api/medical-notes/**").hasAnyRole("ADMIN", "NURSE", "DOCTOR")

                        .pathMatchers("/actuator/health").permitAll()
                        .pathMatchers("/actuator/**").hasRole("ADMIN")

                        .pathMatchers(HttpMethod.GET, "/auditlog/**").hasAnyRole("ADMIN", "COMPLIANCE_OFFICER")
                        .pathMatchers(HttpMethod.POST, "/auditlog/**").denyAll()

                        .anyExchange().authenticated()
                )
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}