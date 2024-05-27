package org.webapp.gymfreaks.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webapp.gymfreaks.auth.error.UserAlreadyExistException;
import org.webapp.gymfreaks.auth.model.UserEntity;
import org.webapp.gymfreaks.auth.model.dto.request.RegisterRequestDto;
import org.webapp.gymfreaks.auth.repository.UserRepository;
import org.webapp.gymfreaks.auth.utils.constants.Authorities;
import org.webapp.gymfreaks.core.config.CustomLogger;
import org.webapp.gymfreaks.core.service.BaseService;
import org.webapp.gymfreaks.utils.AppUtil;

@Service
public class RegisterationService extends BaseService<UserEntity, Long> {

    @Autowired
    UserRepository authRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserEntity register(RegisterRequestDto requestBody) {
        if (authRepository.existsByUserEmail(requestBody.getEmail())) {
            CustomLogger.error(new UserAlreadyExistException(), "Account with email {} already exists.",
                    requestBody.getEmail());
            throw new UserAlreadyExistException(requestBody.getEmail());
        }
        UserEntity account;
        account = UserEntity.builder().userEmail(requestBody.getEmail())
                .userPassword(passwordEncoder.encode(requestBody.getPassword()))
                .userFirstName(requestBody.getFirstName())
                .userLastName(requestBody.getLastName()).userGender(requestBody.getGender())
                .userPhoneNumber(requestBody.getPhoneNumber()).userDescription(requestBody.getDescription())
                .userImage(AppUtil.getDefaultUserPhotResource())
                .userRoles(roleService.findRoleByRoleName(Authorities.USER.toString()))
                .build();
        authRepository.save(account);
        return account;// TODO CHANGE THE RETURN TYPE FOR DTO
    }
}