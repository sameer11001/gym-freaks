package org.webapp.gymfreaks.core.config;

import java.util.ArrayList;

import java.util.List;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.UrlResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.webapp.gymfreaks.auth.model.UserEntity;
import org.webapp.gymfreaks.auth.model.Role;
import org.webapp.gymfreaks.auth.service.AuthService;
import org.webapp.gymfreaks.auth.service.RoleService;
import org.webapp.gymfreaks.auth.utils.constants.Authorities;
import org.webapp.gymfreaks.auth.utils.constants.Gender;

import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StartUpDataApp implements CommandLineRunner {

    @Autowired
    AuthService accountService;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static final String LOCATION = "src\\main\\resources\\static\\user_photo\\";
    private static final String FILENAME = "user_photo_default.jpg";

    @Override
    public void run(String... args) throws Exception {

        File file = new File(LOCATION + FILENAME);
        if (!file.exists()) {
            log.debug("File not found");
        }
        Path path = Paths.get(file.toURI());

        UrlResource resource = new UrlResource(path.toUri());
        List<UserEntity> testAccount = new ArrayList<>();

        UserEntity accountAdmin = new UserEntity();
        if (roleService.findAllRoles().isEmpty()) {
            roleService.saveRole(new Role(null, Authorities.ADMIN.toString()));
            roleService.saveRole(new Role(null, Authorities.USER.toString()));
            roleService.saveRole(new Role(null, Authorities.EDITOR.toString()));

        }

        if (accountService.findAll().isEmpty()) {

            for (int i = 0; i < 10; i++) {
                UserEntity account = new UserEntity();
                account.setUserEmail("test" + i + "@freaks.com");
                account.setUserPassword(passwordEncoder.encode("test" + i));
                account.setUserFirstName("test" + i);
                account.setUserLastName("_user");
                account.setUserPhoneNumber("067556234" + i);
                account.setUserDescription("im a test user_" + i);
                account.setUserGender(Gender.MALE.toString());
                account.setUserImage(resource.getFilename());
                account.setUserRoles(roleService.findRoleByRoleName(Authorities.USER.toString()));
                log.debug(account.toString());
                testAccount.add(account);

            }
            log.debug(testAccount.toString());
            try {
                accountService.insertAll(testAccount);
                log.debug("inseted 10 users");
                accountAdmin.setUserEmail("x@freaks.com");
                accountAdmin.setUserPassword(passwordEncoder.encode("admin"));
                accountAdmin.setUserFirstName("adminx");
                accountAdmin.setUserPhoneNumber("0000000000");
                accountAdmin.setUserRoles(roleService.findRoleByRoleName(Authorities.ADMIN.toString()));
                accountService.insert(accountAdmin);

                log.debug(accountAdmin.toString());
            } catch (Exception e) {
                log.debug(e.getMessage());
            }

        }
    }

}
