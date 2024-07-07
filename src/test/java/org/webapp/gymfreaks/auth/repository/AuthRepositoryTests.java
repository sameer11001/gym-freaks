package org.webapp.gymfreaks.auth.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.webapp.gymfreaks.auth.model.Role;
import org.webapp.gymfreaks.auth.model.UserEntity;
import org.webapp.gymfreaks.auth.utils.constants.Authorities;
import org.webapp.gymfreaks.auth.utils.constants.Gender;

import java.util.HashSet;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthRepositoryTests {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Test
    void UserRepository_SaveUser_ReturnsSavedUser() {

        // Arrange
        if (roleRepository.findAll().isEmpty()) {
            roleRepository.save(new Role(null, Authorities.ADMIN.toString()));
            roleRepository.save(new Role(null, Authorities.USER.toString()));
            roleRepository.save(new Role(null, Authorities.EDITOR.toString()));

        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRoleName(Authorities.USER.toString()));

        UserEntity account = new UserEntity();
        account.setUserEmail("test@freaks.com");
        account.setUserPassword("12345678");
        account.setUserFirstName("test");
        account.setUserLastName("_user");
        account.setUserPhoneNumber("067556234");
        account.setUserDescription("im a test user_");
        account.setUserGender(Gender.MALE.toString());
        account.setUserImage("");
        account.setUserRoles(roles);

        // Act
        userRepository.save(account);
        // Assert
        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat(account.getUserId()).isGreaterThan(0);
    }

}
