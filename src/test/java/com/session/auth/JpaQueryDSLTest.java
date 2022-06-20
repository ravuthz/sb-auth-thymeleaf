package com.session.auth;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.session.auth.model.QRole;
import com.session.auth.model.QUser;
import com.session.auth.model.User;
import com.session.auth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by : Vannaravuth YO
 * Date  : 17-May-22, Tuesday
 * Name  : 11:47 PM
 */

public class JpaQueryDSLTest extends BaseTest {

    @Autowired
    private UserRepository userRepository;
    QRole role = QRole.role;
    QUser user = QUser.user;

    @Test
    void given_roles_where_has_name_equal() {
        JPAQuery<QRole> query = new JPAQuery<>(entityManager);
        int expected = 0;
        query.from(role).where(role.name.eq("ADMIN"));
        assertThat(query.fetch().size()).isEqualTo(expected);
        assertEquals(expected, query.fetch().size());
    }

    @Test
    void given_roles_where_has_name_in() {
        JPAQuery<QRole> query = new JPAQuery<>(entityManager);
        int expected = 0;
        query.from(role).where(role.name.in("USER", "ADMIN"));
        assertThat(query.fetch().size()).isEqualTo(expected);
        assertEquals(expected, query.fetch().size());
    }

    @Test
    void given_users_has_username_equal() {
        BooleanExpression equalAdmin = user.username.eq("admin");

        JPAQuery<?> query = new JPAQuery<Void>(entityManager);
        List<User> users = query.select(user).from(user).where(equalAdmin).fetch();
        System.out.println(users);

        userRepository.findAll(equalAdmin).forEach(System.out::println);
    }

    @Test
    void given_users_has_username_like() {
        BooleanExpression likeAdmin = user.username.like("admin");

        JPAQuery<?> query = new JPAQuery<Void>(entityManager);
        List<User> users = query.select(user).from(user).where(likeAdmin).fetch();
        System.out.println(users);

        userRepository.findAll(likeAdmin).forEach(System.out::println);
    }

    @Test
    void given_users() {
        List<Object[]> resultList1 = entityManager.createNativeQuery("SELECT * FROM role", Object[].class)
                .getResultList();
        if (resultList1 != null && resultList1.size() > 0) {
            resultList1.forEach(result -> {
                System.out.print("\n");
                for (Object item : result) {
                    System.out.print(item + "\t");
                }
            });
        }

//        List<User> resultList2 =
        entityManager.createNativeQuery("SELECT * FROM user", User.class)
                .getResultList().forEach(System.out::println);
//        if (resultList2 != null && resultList2.size() > 0) {
//            resultList2.forEach(System.out::println);
//        }
    }
}
