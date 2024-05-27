package org.webapp.gymfreaks.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.webapp.gymfreaks.auth.error.TokenNotFoundException;
import org.webapp.gymfreaks.auth.mapper.UserMapper;
import org.webapp.gymfreaks.auth.model.RefreshToken;
import org.webapp.gymfreaks.auth.model.UserEntity;
import org.webapp.gymfreaks.auth.model.dto.Jwt.JWTResponseDto;
import org.webapp.gymfreaks.auth.model.dto.Jwt.RefresherTokenDto;
import org.webapp.gymfreaks.auth.model.dto.request.LoginRequestDto;
import org.webapp.gymfreaks.auth.model.dto.request.LogoutRequestDto;
import org.webapp.gymfreaks.auth.model.dto.request.RegisterRequestDto;
import org.webapp.gymfreaks.auth.model.dto.user.UserViewDto;

import org.webapp.gymfreaks.auth.security.JwtTokenUtils;
import org.webapp.gymfreaks.auth.service.LoginService;
import org.webapp.gymfreaks.auth.service.RefreshTokenService;
import org.webapp.gymfreaks.auth.service.RegisterationService;
import org.webapp.gymfreaks.core.config.CustomLogger;
import org.webapp.gymfreaks.core.model.dto.CustomApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth Controller", description = "Auth API")
public class AuthController {

    @Autowired
    private RegisterationService registerationService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private LoginService loginService;

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Autowired
    UserMapper userMapper;

    @PostMapping("/login")
    @Operation(summary = "Login API", description = "Logs in a user and generates a JWT token")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful with JWT token"),
            @ApiResponse(responseCode = "400", description = "Invalid credentials")
    })
    public CustomApiResponse<JWTResponseDto> login(@Valid @RequestBody final LoginRequestDto requestBody) {

        try {
            JWTResponseDto token;
            CustomLogger.debug("Login request received with email " + requestBody.getEmail());
            token = loginService.login(requestBody);
            CustomLogger.debug("Token generated " + token);
            return CustomApiResponse.successOf(token, "Login successful");

        } catch (BadCredentialsException e) {
            CustomLogger.error("Invalid credentials " + e.getMessage());
            return CustomApiResponse.errorOf(HttpStatus.BAD_REQUEST, "error : " + e.getMessage());
        }
    }

    @Operation(summary = "register API", description = "register a user")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "account created successful with all account information"),
            @ApiResponse(responseCode = "400", description = "Invalid credentials with message")
    })
    @PostMapping("/register")
    public CustomApiResponse<UserViewDto> register(@RequestBody @Valid final RegisterRequestDto requestBody) {
        try {
            UserEntity account = registerationService.register(requestBody);

            UserViewDto accountViewDto = userMapper.toDto(account);
            CustomLogger.debug("Account created successfully with email " + account.getUserEmail());
            return CustomApiResponse.successOf(accountViewDto, "Account created successfully");
        } catch (Exception e) {
            CustomLogger.error("Error while creating account " + e.getMessage());
            return CustomApiResponse.errorOf(HttpStatus.BAD_REQUEST, "error : " + e.getMessage());
        }
    }

    @Operation(summary = "refresh token API", description = "refresh access token")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Refresh Token generated successfully with JWT token"),
            @ApiResponse(responseCode = "400", description = "Invalid credentials with message")
    })
    @PostMapping("/refresh_token")
    public CustomApiResponse<JWTResponseDto> refreshToken(@RequestBody RefresherTokenDto requestBody) {
        Optional<RefreshToken> optioanlRefreshToken = refreshTokenService.findByToken(requestBody.getToken());
        if (optioanlRefreshToken.isPresent()) {
            try {
                RefreshToken refreshToken = refreshTokenService.verifyExpiration(optioanlRefreshToken.get());
                String userInfoUserName = refreshToken.getTokenUser().getUserEmail(); // Assuming user info is stored in

                String accessToken = jwtTokenUtils.generateToken(userInfoUserName);
                CustomLogger.debug("Token generated " + accessToken);
                JWTResponseDto jwtResponse = JWTResponseDto.builder()
                        .accessToken(accessToken)
                        .token(requestBody.getToken())
                        .build();
                return CustomApiResponse.successOf(jwtResponse, "Refresh Token generated successfully");
            } catch (Exception e) {
                CustomLogger.error("Bad Request" + e.getMessage());
                return CustomApiResponse.errorOf(HttpStatus.BAD_REQUEST, "Bad Request" + e.getMessage());
            }
        } else {
            CustomLogger.error("there is no token in database");
            throw new TokenNotFoundException("there is no token in database");
        }
    }

    @Operation(summary = "logout API", description = "logout a user")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout successful"),
            @ApiResponse(responseCode = "400", description = "error while logging out")
    })
    @PostMapping("/logout")
    public CustomApiResponse<String> logout(@RequestBody LogoutRequestDto requestBody) {

        try {
            refreshTokenService.deleteByToken(requestBody.getToken());
            CustomLogger.debug("Token deleted " + requestBody.getToken());
            return CustomApiResponse.successOf("request processed", "Logout successful");
        } catch (TokenNotFoundException e) {
            CustomLogger.error("error while logging out : " + e.getMessage());
            return CustomApiResponse.errorOf(HttpStatus.BAD_REQUEST, "error while logging out : " + e.getMessage());
        }
    }
}
