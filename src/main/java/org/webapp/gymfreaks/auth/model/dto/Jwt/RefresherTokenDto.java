package org.webapp.gymfreaks.auth.model.dto.Jwt;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefresherTokenDto {

    private String token;

}
