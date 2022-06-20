package com.session.auth.repository;

import com.session.auth.BaseTest;
import com.session.auth.model.QUser;
import com.session.auth.model.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by : Vannaravuth YO
 * Date  : 18-May-22, Wednesday
 * Name  : 11:49 PM
 */

//@DataJpaTest
//@ActiveProfiles("test")
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest extends BaseTest {

    @Autowired
    private UserRepository userRepository;

    private int userCount = 0;

    @BeforeEach
    void setup() {
        List<User> userList = Arrays.asList(
                makeUser("user01"),
                makeUser("user02"),
                makeUser("user03")
        );
        userCount = userList.size();
        userRepository.saveAll(userList);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @Order(1)
    void should_have_list_of_users() {
        List<User> users = (List<User>) userRepository.findAll();
        assertEquals(userCount, users.size());
    }

    @Test
    @Order(2)
    void should_create_single_user() {
        User user = makeUser("user04");
        User savedUser = userRepository.save(user);
        assertThat(savedUser)
                .usingRecursiveComparison()
                .ignoringFields("password")
                .isEqualTo(user);
    }

    @Test
    @Order(3)
    void should_update_single_user() {
        User user = userRepository.findByUsername("user02");
        user.setEnabled(false);
        User savedUser = userRepository.save(user);
        assertThat(savedUser)
                .usingRecursiveComparison()
                .ignoringFields("password")
                .isEqualTo(user);
    }

    @Test
    @Order(4)
    void should_delete_single_user() {
        userRepository.delete(userRepository.findByUsername("user03"));
        assertNull(userRepository.findByUsername("user03"));
    }

    @Test
    @Order(5)
    void should_get_users_using_jpq_query_dsl() {
        QUser user = QUser.user;
        User user1 = userRepository.findOne(user.username.eq("user01")).orElse(null);
        assertNotNull(user1);

        List<User> users = (List<User>) userRepository.findAll(user.username.like("user%"));
        assertEquals(userCount, users.size());
    }

    private User makeUser(String username) {
        User user = new User();
        user.setEmail(username + "@gm.com");
        user.setUsername(username);
        user.setPassword("123123");
        user.setFullName("demo", username);
        user.setEnabled(true);
        user.setCreatedAt(new Date());
        user.setCreatedBy("system-test");
        return user;
    }

}