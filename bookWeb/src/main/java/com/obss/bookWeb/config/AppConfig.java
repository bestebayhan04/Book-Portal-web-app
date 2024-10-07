package com.obss.bookWeb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class AppConfig {
    private final CustomAuthenticationSuccessHandler successHandler;
    public AppConfig(CustomAuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain springSecurityConfiguration(HttpSecurity http) throws Exception {
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(HttpMethod.POST, "/bookweb/admin").permitAll()
                            .requestMatchers(HttpMethod.POST, "/bookweb/customers").permitAll()
                            .requestMatchers(HttpMethod.GET, "/bookweb/signIn").authenticated()
                            .requestMatchers(HttpMethod.GET,  "/bookweb/products/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/bookweb/product/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/bookweb/product/**", "/bookweb/favlist/**","/bookweb/readlist/**").hasRole("USER")
                            .requestMatchers(HttpMethod.PUT, "/bookweb/admin/**", "/bookweb/products/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/bookweb/admin/**", "/bookweb/favlist/**","/bookweb/readlist/**").hasRole("USER")
                            .requestMatchers(HttpMethod.DELETE, "/bookweb/products/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/bookweb/favlist/remove-product/**","/bookweb/readlist/remove-product/**").hasRole("USER")
                            .requestMatchers("/swagger-ui*/**", "/v3/api-docs/**").permitAll()
                            .anyRequest().authenticated();
                })
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(successHandler))
                .addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class);
        return http.build();}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:3000", "https://my_shop/"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;}
}