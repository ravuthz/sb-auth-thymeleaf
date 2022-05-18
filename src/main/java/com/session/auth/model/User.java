package com.session.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Audited
@Getter
@Setter
@Entity
@Table(name = "users")
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity {

    private static final long serialVersionUID = -7721781417455120512L;
    public static final String DEFAULT_USER = "system-user";

    @Email
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String email;

    @NotEmpty
    @Size(min = 3, max = 250)
    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    @Transient
    private String confirmPassword;

    @NotEmpty
    @Size(min = 3, max = 250)
    private String firstName;

    @NotEmpty
    @Size(min = 3, max = 250)
    private String lastName;

    private boolean enabled;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "userRole",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<Role> roles = new HashSet<>();

    public User(User user) {
        this.setId(user.getId());
        this.email = user.email;
        this.username = user.username;
        this.password = user.password;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.enabled = user.enabled;
        this.roles = user.roles;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public void setFullName(String first, String last) {
        this.firstName = first;
        this.lastName = last;
    }

    public void addRole(Role role) {
        this.getRoles().add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        this.getRoles().remove(role);
        role.getUsers().remove(this);
    }
}

