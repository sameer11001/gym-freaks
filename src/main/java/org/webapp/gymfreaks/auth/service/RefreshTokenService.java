package org.webapp.gymfreaks.auth.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webapp.gymfreaks.auth.model.RefreshToken;
import org.webapp.gymfreaks.auth.model.UserEntity;
import org.webapp.gymfreaks.auth.repository.RefreshTokenRepository;
import org.webapp.gymfreaks.auth.repository.UserRepository;
import org.webapp.gymfreaks.core.error.ResourceNotFoundException;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    Long REFRESH_TOKEN_VALIDITY = (long) (60 * 60 * 24 * 7);

    public RefreshToken createRefreshToken(String username) {
        Optional<UserEntity> account = userRepository.findByUserEmail(username);
        if (account.isPresent()) {
            RefreshToken refreshToken = RefreshToken.builder()
                    .tokenUser(
                            account.get())
                    .token(UUID.randomUUID().toString())
                    .expiryDate(Instant.now().plusSeconds(REFRESH_TOKEN_VALIDITY)) // 7 days
                    // configure it application.properties file
                    .build();
            return refreshTokenRepository.save(refreshToken);
        }
        return null;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");

        }
        return token;
    }

    public void deleteByToken(String token) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByToken(token);
        if (refreshToken.isPresent()) {
            refreshTokenRepository.deleteById(refreshToken.get().getId());
        } else {
            throw new ResourceNotFoundException(token + " Refresh token not found in DB");
        }

    }
}
