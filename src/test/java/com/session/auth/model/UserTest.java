package com.session.auth.model;

import com.session.auth.repository.RoleRepository;
import com.session.auth.repository.UserRepository;
import com.session.auth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class UserTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void addRole() {
        Role role1 = new Role("TEST_ROLE_01");
        Role role2 = new Role("TEST_ROLE_02");
        Role role3 = new Role("TEST_ROLE_03");
        roleRepository.saveAll(Arrays.asList(role1, role2, role3));

        User user1 = authService.findOrCreateUser("TEST_USER_01");
        user1.addRole(role1);
        user1.addRole(role2);
        user1.addRole(role3);
        userRepository.save(user1);
    }

    @Test
    public void createNativeQuery() {
        List<Object[]> resultList = entityManager.createNativeQuery("SELECT * FROM user_role").getResultList();
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