package org.webapp.gymfreaks.auth.utils;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.webapp.gymfreaks.auth.error.RoleNotFoundException;
import org.webapp.gymfreaks.auth.model.Role;
import org.webapp.gymfreaks.auth.service.RoleService;
import org.webapp.gymfreaks.auth.utils.constants.Authorities;

@Component
public class RoleFactory {
    @Autowired
    RoleService roleService;

    public Set<Role> getInstance(Authorities role) throws RoleNotFoundException {
        switch (role) {
            case ADMIN -> {
                return roleService.findRoleByRoleName(Authorities.ADMIN.toString());
            }
            case USER -> {
                return roleService.findRoleByRoleName(Authorities.USER.toString());
            }
            case EDITOR -> {
                return roleService.findRoleByRoleName(Authorities.EDITOR.toString());
            }
            default -> throw new RoleNotFoundException("No role found for " + role);
        }
    }

}
