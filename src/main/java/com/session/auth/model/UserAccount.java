package com.session.auth.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserAccount extends User implements UserDetails {

    private static final long serialVersionUID = -978466049302016432L;

    public UserAccount(User user) {
        super(user);
    }

    private SimpleGrantedAuthority makeAuthority(String name) {
        return new SimpleGrantedAuthority(name.toUpperCase());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : getRoles()) {
            authorities.add(makeAuthority(role.getName()));
            for (Permission permission : role.getPermissions()) {
                authorities.add(makeAuthority(permission.getName()));
            }
        }
        return authorities;
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

}
