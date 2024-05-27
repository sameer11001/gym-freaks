package org.webapp.gymfreaks.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webapp.gymfreaks.auth.error.UserNotFoundException;
import org.webapp.gymfreaks.auth.model.RefreshToken;
import org.webapp.gymfreaks.auth.model.dto.Jwt.JWTResponseDto;
import org.webapp.gymfreaks.auth.model.dto.request.LoginRequestDto;
import org.webapp.gymfreaks.auth.security.JwtTokenUtils;
import org.webapp.gymfreaks.core.config.CustomLogger;

@Service
public class LoginService {

    @Autowired
    AuthService authService;

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RefreshTokenService refreshTokenService;

    public JWTResponseDto login(LoginRequestDto requestBody) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestBody.getEmail(), requestBody.getPassword()));
        if (authService.existsByUserEmail(requestBody.getEmail())) {
            if (authentication.isAuthenticated()) {
                CustomLogger.debug("doAuthenticate complete with " + requestBody.getEmail());

                // Security Best Practices
                SecurityContextHolder.getContext().setAuthentication(authentication); // Optional based on framework

                // Refresh Token Generation
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(authentication.getName());

                // JWT Token Generation (Improved)
                String accessToken = jwtTokenUtils.generateToken(requestBody.getEmail());

                return JWTResponseDto.builder()
                        .accessToken(accessToken)
                        .token(refreshToken.getToken())
                        .build();
            } else {
                throw new BadCredentialsException("invalid with password"); // More specific exception
            }
        } else {
            throw new UserNotFoundException("User Not Found");
        }
    }

}
