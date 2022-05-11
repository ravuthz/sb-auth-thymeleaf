package com.session.auth;

import com.session.auth.service.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Profile("!prd")
public class AuthApplicationRunner implements CommandLineRunner {
    private final Environment environment;
    private final AuthService authService;

    public AuthApplicationRunner(Environment environment, AuthService authService) {
        this.environment = environment;
        this.authService = authService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.printf("\nAuthApplicationRunner is running on profile %s ...\n", environment.getActiveProfiles());

        System.out.println("AuthApplicationRunner is creating user 'user' with role 'USER' ...");
        authService.assignPermissionsToRole(
                authService.findOrCreateRole("USER"),
                Arrays.asList(
                        authService.findOrCreatePermission("CREATE_APP"),
                        authService.findOrCreatePermission("UPDATE_APP"),
                        authService.findOrCreatePermission("DELETE_APP"),
                        authService.findOrCreatePermission("VIEW_APP"),

                        authService.findOrCreatePermission("VIEW_USER"),
                        authService.findOrCreatePermission("VIEW_ROLE"),
                        authService.findOrCreatePermission("VIEW_PERMISSION")
                ));

        authService.assignRoleToUser(
                authService.findOrCreateRole("USER"),
                authService.findOrCreateUser("user")
        );

        System.out.println("AuthApplicationRunner is creating user 'admin' with role 'ADMIN' ...");
        authService.assignPermissionsToRole(
                authService.findOrCreateRole("ADMIN"),
                Arrays.asList(
                        authService.findOrCreatePermission("CREATE_APP"),
                        authService.findOrCreatePermission("UPDATE_APP"),
                        authService.findOrCreatePermission("DELETE_APP"),
                        authService.findOrCreatePermission("VIEW_APP"),

                        authService.findOrCreatePermission("CREATE_USER"),
                        authService.findOrCreatePermission("UPDATE_USER"),
                        authService.findOrCreatePermission("DELETE_USER"),
                        authService.findOrCreatePermission("VIEW_USER"),

                        authService.findOrCreatePermission("CREATE_ROLE"),
                        authService.findOrCreatePermission("UPDATE_ROLE"),
                        authService.findOrCreatePermission("DELETE_ROLE"),
                        authService.findOrCreatePermission("VIEW_ROLE"),

                        authService.findOrCreatePermission("CREATE_PERMISSION"),
                        authService.findOrCreatePermission("UPDATE_PERMISSION"),
                        authService.findOrCreatePermission("DELETE_PERMISSION"),
                        authService.findOrCreatePermission("VIEW_PERMISSION")
                ));

        authService.assignRoleToUser(
                authService.findOrCreateRole("ADMIN"),
                authService.findOrCreateUser("admin")
        );

        System.out.println("AuthApplicationRunner finished. \n");
    }
}
