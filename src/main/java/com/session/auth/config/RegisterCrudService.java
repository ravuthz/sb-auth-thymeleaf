package com.session.auth.config;

import com.session.auth.model.Permission;
import com.session.auth.model.Role;
import com.session.auth.model.User;
import com.session.auth.repository.PermissionRepository;
import com.session.auth.repository.RoleRepository;
import com.session.auth.repository.UserRepository;
import com.session.auth.service.cruds.CrudService;
import com.session.auth.service.cruds.CrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RegisterCrudService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Autowired
    public RegisterCrudService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Bean
    public CrudService<User> userCrudService() {
        return new CrudServiceImpl<>(userRepository);
    }

    @Bean
    public CrudService<Role> roleCrudService() {
        return new CrudServiceImpl<>(roleRepository);
    }

    @Bean
    public CrudService<Permission> permissionCrudService() {
        return new CrudServiceImpl<>(permissionRepository);
    }
}
