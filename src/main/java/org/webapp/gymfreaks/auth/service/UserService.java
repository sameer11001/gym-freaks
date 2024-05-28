package org.webapp.gymfreaks.auth.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webapp.gymfreaks.auth.error.UserNotFoundException;
import org.webapp.gymfreaks.auth.mapper.UserMapper;
import org.webapp.gymfreaks.auth.model.UserEntity;
import org.webapp.gymfreaks.auth.repository.UserRepository;
import org.webapp.gymfreaks.core.config.CustomLogger;
import org.webapp.gymfreaks.core.error.NullItemException;
import org.webapp.gymfreaks.core.service.BaseService;

@Service
public class UserService extends BaseService<UserEntity, Long> {

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

    @Override
    public List<UserEntity> insertAll(List<UserEntity> entities) {
        if (entities == null) {
            CustomLogger.error(new NullItemException(), "Account list is null.");
            throw new NullItemException();
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
        try {
            if (!accountRepository.existsById(id)) {
                CustomLogger.error(new UserNotFoundException(), "Account with id {} does not exist.", id);
                throw new UserNotFoundException();
            }
            return accountRepository.save(account);
        } catch (Exception e) {
            CustomLogger.error(new RuntimeException(), "Error while updating account with id {}." + e.toString(), id);
            throw new RuntimeException();
        }
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
}

// TODO handl the rest of user service and mapping the dto