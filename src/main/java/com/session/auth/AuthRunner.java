package com.session.auth;

import com.session.auth.service.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Profile("!prd")
public class AuthRunner implements CommandLineRunner {
    private final Environment environment;
    private final AuthService authService;

    public AuthRunner(Environment environment, AuthService authService) {
        this.environment = environment;
        this.authService = authService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.printf("\nAuthRunner is running on profile %s ...\n", environment.getActiveProfiles());

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
