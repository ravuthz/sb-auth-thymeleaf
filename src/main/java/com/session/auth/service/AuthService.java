package com.session.auth.service;

import com.session.auth.model.Permission;
import com.session.auth.model.Role;
import com.session.auth.model.User;
import com.session.auth.model.UserAccount;
import com.session.auth.repository.PermissionRepository;
import com.session.auth.repository.RoleRepository;
import com.session.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService implements UserDetailsService {
    private final static String message = "Username or Password is incorrect or User is disable, with username: ";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        if (username.contains("@")) {
            user = userRepository.findByEmailAndEnabledTrue(username);
        } else {
            user = userRepository.findByUsernameAndEnabledTrue(username);
        }
        if (user == null) {
            throw new UsernameNotFoundException(message + username);
        }
        return new UserAccount(user);
    }

    public User findOrCreateUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            user = makeUser(username, username);
        }
        userRepository.save(user);
        return user;
    }

    public Role findOrCreateRole(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
        }
        role.setName(name);
        role.setNote(name);
        roleRepository.save(role);
        return role;
    }

    public Permission findOrCreatePermission(String name) {
        Permission permission = permissionRepository.findByName(name);
        if (permission == null) {
            permission = new Permission();
        }
        permission.setName(name);
        permission.setNote(name);
        permissionRepository.save(permission);
        return permission;
    }

    public Role assignPermissionsToRole(Role role, List<Permission> permissions) {
        if (role != null) {
            permissions.forEach(role.getPermissions()::add);
            roleRepository.save(role);
        }
        return role;
    }

    public User assignRoleToUser(Role role, User user) {
        user.getRoles().add(role);
        userRepository.save(user);
        role.getUsers().add(user);
        roleRepository.save(role);
        return user;
    }

    public User makeUser(String firstName, String lastName) {
        User user = new User();
        user.setEmail(firstName + "@gmail.com");
        user.setUsername(firstName);
        user.setFullName(firstName, lastName);
        user.setPassword(passwordEncoder.encode("123123"));
        user.setEnabled(true);
        return user;
    }


}
