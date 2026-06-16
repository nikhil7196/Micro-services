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

                        // ✅ OPTIONS always first — preflight requests
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // ✅ Public endpoints
                        .pathMatchers("/api/auth/**").permitAll()
                        .pathMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .pathMatchers("/user/**").permitAll()

                        // ✅ Notification — no /api prefix
                        .pathMatchers("/notification/**").authenticated()

                        // ✅ Patient
                        .pathMatchers("/api/patient/**").hasAnyRole("ADMIN", "RECEPTIONIST", "NURSE", "DOCTOR")

                        // ✅ Doctor — POST /add is public for registration
                        .pathMatchers(HttpMethod.POST, "/api/doctor/add").permitAll()
                        .pathMatchers("/api/doctor/**").hasAnyRole("ADMIN", "DOCTOR", "RECEPTIONIST")

                        // ✅ Ward
                        .pathMatchers("/api/ward/**").hasAnyRole("ADMIN", "NURSE")

                        // ✅ Beds — added COMPLIANCE_OFFICER for KPI calculations
                        .pathMatchers("/api/beds/**").hasAnyRole("ADMIN", "NURSE", "COMPLIANCE_OFFICER")

                        // ✅ Appointment — added COMPLIANCE_OFFICER for fulfillment KPI
                        .pathMatchers("/api/appointment/**").hasAnyRole("ADMIN", "DOCTOR", "RECEPTIONIST", "COMPLIANCE_OFFICER")

                        // ✅ Compliance Reports
                        .pathMatchers("/api/compliance-reports/**").hasAnyRole("ADMIN", "COMPLIANCE_OFFICER")

                        // ✅ KPI Reports — added FINANCEOFFICER
                        .pathMatchers("/api/kpi-report/**").hasAnyRole("ADMIN", "COMPLIANCE_OFFICER", "FINANCEOFFICER")

                        // ✅ Invoice
                        .pathMatchers("/api/invoice/**").hasAnyRole("ADMIN", "FINANCEOFFICER")

                        // ✅ Patient Billing
                        .pathMatchers("/api/patientbilling/**").hasAnyRole("ADMIN", "FINANCEOFFICER")

                        // ✅ Insurance — added COMPLIANCE_OFFICER for claim success KPI
                        .pathMatchers("/api/insurance/**").hasAnyRole("ADMIN", "FINANCEOFFICER", "COMPLIANCE_OFFICER")

                        // ✅ Notification via /api/notification
                        .pathMatchers("/api/notification/**").authenticated()

                        // ✅ Vitals + Care Notes
                        .pathMatchers("/api/vitals/**").hasAnyRole("ADMIN", "NURSE", "DOCTOR")
                        .pathMatchers("/api/care-notes/**").hasAnyRole("ADMIN", "NURSE", "DOCTOR")

                        // ✅ Medical Notes
                        .pathMatchers("/api/medical-notes/**").hasAnyRole("ADMIN", "NURSE", "DOCTOR")

                        // ✅ Actuator
                        .pathMatchers("/actuator/health").permitAll()
                        .pathMatchers("/actuator/**").hasRole("ADMIN")

                        // ✅ Audit Log
                        .pathMatchers(HttpMethod.GET, "/auditlog/**").hasAnyRole("ADMIN", "COMPLIANCE_OFFICER")
                        .pathMatchers(HttpMethod.POST, "/auditlog/**").denyAll()

                        // ✅ Everything else requires authentication
                        .anyExchange().authenticated()
                )
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}