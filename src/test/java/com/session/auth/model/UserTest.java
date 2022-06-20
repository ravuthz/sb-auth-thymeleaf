package com.session.auth.model;

import com.session.auth.repository.UserRepository;
import com.session.auth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class UserTest {
    private final AuthService authService;
    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserTest(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @Test
    public void addRole() {
        Role role1 = authService.findOrCreateRole("TEST_ROLE_01");
        Role role2 = authService.findOrCreateRole("TEST_ROLE_02");
        Role role3 = authService.findOrCreateRole("TEST_ROLE_03");
        User user1 = authService.findOrCreateUser("TEST_USER_01");

        user1.addRole(role1);
        user1.addRole(role2);
        user1.addRole(role3);
        userRepository.save(user1);
    }

    public void createNativeQuery() {
        List<Object[]> resultList = entityManager.createNativeQuery("SELECT * FROM user_role", Object.class).getResultList();
        if (resultList != null && resultList.size() > 0) {
            resultList.forEach(result -> {
                System.out.print("\n");
                for (Object item : result) {
                    System.out.print(item + "\t");
                }
            });
        }
    }

}