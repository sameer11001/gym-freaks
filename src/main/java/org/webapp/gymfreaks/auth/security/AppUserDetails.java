package org.webapp.gymfreaks.auth.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.webapp.gymfreaks.auth.model.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class AppUserDetails implements UserDetails {

    private String userEmail;
    private String userPassword;

    private List<GrantedAuthority> authorities;

    public AppUserDetails() {
        super();
    }

    public AppUserDetails(UserEntity account) {

        this.userEmail = account.getUserEmail();
        this.userPassword = account.getUserPassword();

        this.authorities = new ArrayList<>();
        if (!account.getUserRoles().isEmpty()) {
            account.getUserRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));

        }

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;

    }

    @Override
    public String getPassword() {

        return userPassword;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }
    // TODO: we can make all the setting for user in the constructor as a boolean
    // but i need to make sure to understand it deeply
}
