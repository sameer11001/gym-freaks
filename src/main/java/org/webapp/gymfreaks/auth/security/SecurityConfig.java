package org.webapp.gymfreaks.auth.security;

import java.util.Arrays;
import java.util.HashSet;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.webapp.gymfreaks.auth.service.AuthService;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

        @Autowired
        JwtAuthenticationEntryPoint authEntryPoint;

        @Autowired
        JwtAuthFilter jwtAuthFilter;

        @Autowired
        AuthService authService;

        // Use AntPathMatcher for pattern matching
        @Bean
        public PathMatcher pathMatcher() {
                return new AntPathMatcher();
        }

        // this is the public req can any one access
        // put it into jwt Auth filter
        Set<String> publicRequest = new HashSet<>(
                        Arrays.asList("/api/v1/auth/login", "/api/v1/auth/register", "/api/v1/auth/refresh_token",
                                        "/swagger-ui/**", "/api/v1/products/**")); // we can add

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter)
                        throws Exception {
                return http.csrf(AbstractHttpConfigurer::disable)
                                .exceptionHandling(ex -> ex.authenticationEntryPoint(
                                                authEntryPoint))

                                .sessionManagement(
                                                sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(request -> request
                                                // give the all permission to public
                                                .requestMatchers(publicRequest.stream().map(AntPathRequestMatcher::new)
                                                                .toArray(RequestMatcher[]::new))
                                                .permitAll()
                                                // must put hasAuthority cause the userDetail give authority not role
                                                .requestMatchers("/api/v1/user/**").hasAuthority("ADMIN")
                                                .anyRequest().authenticated())
                                .cors(Customizer.withDefaults())
                                .authenticationProvider(
                                                authenticationProvider())
                                .addFilterBefore(
                                                jwtAuthFilter,
                                                UsernamePasswordAuthenticationFilter.class)

                                // .authenticationProvider(authenticationProvider())
                                .build();
        }

        // lookout about authenticated and unauthenticated and hasRole and configure
        // for using bcrypt encoder for all app
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder(); // Or use a stronger encoder
        }

        // put this to auth manger to use
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

        // look about provider for what
        @Bean
        AuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

                authProvider.setUserDetailsService(authService);
                authProvider.setPasswordEncoder(passwordEncoder());

                return authProvider;
        }

}
