package com.session.auth;

import com.session.auth.service.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AuthRunner implements CommandLineRunner {
    private final AuthService authService;

    public AuthRunner(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("AuthRunner is running ...");

        System.out.println("AuthRunner is creating user 'user' with role 'user' ...");
        authService.assignPermissionsToRole(
                authService.findOrCreateRole("USER"),
                Arrays.asList(
                        authService.findOrCreatePermission("VIEW_APP")
                ));

        authService.assignRoleToUser(
                authService.findOrCreateRole("USER"),
                authService.findOrCreateUser("user")
        );

        System.out.println("AuthRunner is creating user 'admin' with role 'ADMIN' ...");
        authService.assignPermissionsToRole(
                authService.findOrCreateRole("ADMIN"),
                Arrays.asList(
                        authService.findOrCreatePermission("CREATE_APP"),
                        authService.findOrCreatePermission("UPDATE_APP"),
                        authService.findOrCreatePermission("DELETE_APP"),
                        authService.findOrCreatePermission("VIEW_APP")
                ));

        authService.assignRoleToUser(
                authService.findOrCreateRole("ADMIN"),
                authService.findOrCreateUser("admin")
        );
    }
}
