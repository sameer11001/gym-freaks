package org.webapp.gymfreaks.auth.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.webapp.gymfreaks.auth.model.UserEntity;
import org.webapp.gymfreaks.core.repository.BaseRepository;

@Repository
public interface UserRepository extends BaseRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserEmail(String email);

    boolean existsByUserEmail(String email);
}
