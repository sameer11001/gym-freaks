package org.webapp.gymfreaks.auth.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webapp.gymfreaks.auth.model.Role;
import org.webapp.gymfreaks.auth.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    public Set<Role> findRoleByRoleName(String roleName) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRoleName(roleName));
        return roles;
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }
}
