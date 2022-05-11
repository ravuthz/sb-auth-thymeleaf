package com.session.auth;

import com.session.auth.model.Permission;
import com.session.auth.model.Role;
import com.session.auth.model.User;
import com.session.auth.repository.PermissionRepository;
import com.session.auth.repository.RoleRepository;
import com.session.auth.repository.UserRepository;
import com.session.auth.service.cruds.CrudService;
import com.session.auth.service.cruds.CrudServiceImpl;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthApplication {

//    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
//    private final PermissionRepository permissionRepository;
//
//    @Autowired
//    public AuthApplication(
//            UserRepository userRepository,
//            RoleRepository roleRepository,
//            PermissionRepository permissionRepository) {
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//        this.permissionRepository = permissionRepository;
//    }

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }


//    @Bean
//    public CrudService<User> userCrudService() {
//        return new CrudServiceImpl<>(userRepository);
//    }
//
//    @Bean
//    public CrudService<Role> roleCrudService() {
//        return new CrudServiceImpl<>(roleRepository);
//    }
//
//    @Bean
//    public CrudService<Permission> permissionCrudService() {
//        return new CrudServiceImpl<>(permissionRepository);
//    }

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
