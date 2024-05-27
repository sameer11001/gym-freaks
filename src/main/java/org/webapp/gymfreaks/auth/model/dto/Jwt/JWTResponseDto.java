package org.webapp.gymfreaks.auth.model.dto.Jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JWTResponseDto {
    private String accessToken;

    private String token;

}
