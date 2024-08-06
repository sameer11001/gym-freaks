package org.webapp.gymfreaks.auth.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.webapp.gymfreaks.auth.error.UserNotFoundException;
import org.webapp.gymfreaks.auth.mapper.UserMapper;
import org.webapp.gymfreaks.auth.model.UserEntity;
import org.webapp.gymfreaks.auth.repository.UserRepository;
import org.webapp.gymfreaks.auth.security.AppUserDetails;
import org.webapp.gymfreaks.core.config.CustomLogger;
import org.webapp.gymfreaks.core.error.EmptyItemsException;
import org.webapp.gymfreaks.core.service.BaseService;

@Service
public class AuthService extends BaseService<UserEntity, Long> implements UserDetailsService {

    @Autowired
    UserRepository accountRepository;

    @Autowired
    UserMapper userMapper;

    @Override
    public UserEntity findById(Long id) {
        Optional<UserEntity> account = accountRepository.findById(id);
        if (!account.isPresent()) {
            CustomLogger.error(new UserNotFoundException(), "Account with id {} does not exist.", id);
            throw new UserNotFoundException();
        }
        return account.get();
    }

    public Optional<UserEntity> findByUserEmail(String email) {
        Optional<UserEntity> account = accountRepository.findByUserEmail(email);
        if (!account.isPresent()) {
            CustomLogger.error(new UserNotFoundException(), "Account with email {} does not exist.", email);
            throw new UserNotFoundException();
        }
        return account;
    }

    @Override
    public List<UserEntity> findAll() {
        CustomLogger.info("Fetching all accounts for admin");
        return accountRepository.findAll();
    }

    public Long findUserIdbyUserEmail(String email) {
        return accountRepository.findUserIdByUserEmail(email);
    }

    @Override
    public List<UserEntity> insertAll(List<UserEntity> entities) {
        if (entities == null) {
            CustomLogger.error(new EmptyItemsException(), "Account list is null.");
            throw new EmptyItemsException();
        }

        return accountRepository.saveAll(entities);
    }

    @Override
    public UserEntity update(UserEntity account) {
        if (!accountRepository.existsById(account.getUserId())) {
            CustomLogger.error(new UserNotFoundException(), "Account with id {} does not exist.", account.getUserId());
            throw new UserNotFoundException();
        }
        return accountRepository.save(account);
    }

    @Override
    public UserEntity updateById(Long id, UserEntity account) {
        if (!accountRepository.existsById(id)) {
            CustomLogger.error(new UserNotFoundException(), "Account with id {} does not exist.", id);
            throw new UserNotFoundException();
        }
        return accountRepository.save(account);
    }

    @Override
    public void deleteById(Long id) {
        if (!accountRepository.existsById(id)) {
            CustomLogger.error(new UserNotFoundException(), "Account with id {} does not exist.", id);
            throw new UserNotFoundException();
        }
        accountRepository.deleteById(id);
    }

    public boolean existsByUserEmail(String email) {
        return accountRepository.existsByUserEmail(email);
    }

    @Override
    public AppUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<UserEntity> account = accountRepository.findByUserEmail(email);
        if (!account.isPresent()) {
            CustomLogger.error(new UserNotFoundException(), "Account with email {} does not exist.", email);
            throw new UserNotFoundException();
        }
        return new AppUserDetails(account.get());
    }
}
