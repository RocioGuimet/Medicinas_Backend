package com.medicina.config;

import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Headers de seguridad
                .cors(cors -> {})
                .headers(headers -> headers
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("default-src 'self' https://*.render.com; script-src 'self'; style-src 'self'; img-src 'self' data:")
                        )
                        .httpStrictTransportSecurity(hsts -> hsts
                                .includeSubDomains(true)
                                .preload(true)
                                .maxAgeInSeconds(31536000) // 1 año
                        )
                        .frameOptions(frame -> frame.sameOrigin())
                        .xssProtection(xss -> xss
                                .headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
                        )
                        .addHeaderWriter(new StaticHeadersWriter("X-Content-Type-Options", "nosniff"))
                        .addHeaderWriter(new StaticHeadersWriter("X-Frame-Options", "DENY"))
                        .addHeaderWriter(new StaticHeadersWriter("Referrer-Policy", "strict-origin-when-cross-origin"))
                )
                // Autorización de endpoints
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**", "/health", "/").permitAll()
                )
                // Deshabilitar CSRF para APIs REST
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}