package com.spring.csmis.configuration;

import com.spring.csmis.component.util.JwtUtil;
import com.spring.csmis.security.filter.JwtAuthenticationFilter;
import com.spring.csmis.security.filter.JwtAuthorizationFilter;
import com.spring.csmis.service.user.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(JwtUtil jwtUtil,UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtUtil);
        JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(jwtUtil, userDetailsService);

        http
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF protection
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/admin/menu/**").permitAll()
                        .requestMatchers("/admin/menuweek/**").permitAll()
                        .requestMatchers("/reports/**").permitAll()
                        .requestMatchers("/admin/meal/**").permitAll()
                        .requestMatchers("/admin/payment/**").permitAll()
                        .requestMatchers("/admin/cateringcentre/**").permitAll()
                        .requestMatchers("/admin/categories/**").permitAll()
                        .requestMatchers("/admin/category/**").permitAll()
                        .requestMatchers("/admin/feedbacktype/**").permitAll()
                        .requestMatchers("/admin/feedback/**").permitAll()
                        .requestMatchers("/admin/feedbackcategory/**").permitAll()
                        .requestMatchers("/admin/mealtype/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("SYSTEM_ADMIN")
                        .anyRequest().authenticated()  // All other requests require authentication
                )
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Stateless session policy
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)  // Add authentication filter before UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthorizationFilter, JwtAuthenticationFilter.class)  // Add authorization filter
                .cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration corsConfiguration = new CorsConfiguration();
                            corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
                            corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                            corsConfiguration.setAllowedHeaders(List.of("*"));
                            corsConfiguration.setAllowCredentials(true);
                            return corsConfiguration;
                        })
                );

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}