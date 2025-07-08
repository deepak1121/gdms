package com.aiims.gdms.config;

import com.aiims.gdms.security.JwtAuthenticationFilter;
import com.aiims.gdms.security.JwtUtil;
import com.aiims.gdms.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String[] PUBLIC_ENDPOINTS = {
        "/api/auth/**",
        "/api/meal-master/**", 
        "/error",
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html"
    };

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil, AuthService authService) {
        return new JwtAuthenticationFilter(jwtUtil, authService);
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {}) // Lambda DSL style — empty lambda to enable CORS with default settings
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                .requestMatchers("/api/patient/**").hasRole("PATIENT")
                .requestMatchers("/api/doctor/**").hasRole("DOCTOR")
                .requestMatchers("/MealPhotos/**").permitAll()
                .requestMatchers("/uploads/**").permitAll()

                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // Configure the CorsFilter to allow cross-origin requests
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);  // Allow credentials like cookies or Authorization headers
        
        // Add allowed origins (update this with your actual front-end URL)
        config.addAllowedOriginPattern("*");  // For development, use '*' or specify exact URLs in production
        
        // Allow all headers (could be restricted to specific headers if necessary)
        config.addAllowedHeader("*");
        
        // Allow all methods (including OPTIONS for pre-flight)
        config.addAllowedMethod("*");
        
        // Register the CORS configuration
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);  // Return the CORS filter
    }
}
