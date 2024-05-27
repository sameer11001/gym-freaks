package org.webapp.gymfreaks.auth.model.mapper;

import org.webapp.gymfreaks.auth.model.dto.Jwt.JWTResponseDto;

public class JwtToJwtDto {
    private JwtToJwtDto() {
        super();
    }

    public static JWTResponseDto jwtToJwtDto(String token) {
        return JWTResponseDto.builder()
                .token(token)
                .build();
    }
}
