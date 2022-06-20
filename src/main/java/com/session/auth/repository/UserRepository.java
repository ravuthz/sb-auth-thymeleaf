package com.session.auth.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.session.auth.model.QUser;
import com.session.auth.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, QuerydslPredicateExecutor<User> {
    User findByEmailAndEnabledTrue(String email);

    User findByUsernameAndEnabledTrue(String username);

    User findByEmail(String email);

    User findByUsername(String username);

    List<User> findAllByFirstName(String firstName);

    List<User> findAllByLastName(String lastName);

//    List<User> findAllByFirstNameOrLastNameContainsIgnoreCase(String text, Pageable pageable);
//    List<User> findAllByEmailContainsIgnoreCase(String email, Pageable pageable);
//    List<User> findAllByUsernameContainsIgnoreCase(String username, Pageable pageable);

    @Query(value = "select id, username, firstName, lastName from User")
    Page<User> getQueryOnlyName(Pageable pageable);

//    Page<User> findAllByRolesEquals(List<Role> roles, Pageable pageable);
//    Page<User> findAllByRolesContains(List<Role> roles, Pageable pageable);

    default List<User> getUsersByName(String firstName, String lastName) {
        QUser user = QUser.user;

        BooleanExpression likeFirstName = user.firstName.like(firstName);
        BooleanExpression likeLastName = user.lastName.like(lastName);

        return (List<User>) findAll(likeFirstName.and(likeLastName));
    }
}
