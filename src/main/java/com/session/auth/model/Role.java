package com.session.auth.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Audited
@Getter
@Setter
@Entity
@Table(name = "roles")
@ToString
@NoArgsConstructor
public class Role extends BaseEntity {

    private static final long serialVersionUID = -3550988943824029879L;

    @NotEmpty
    @Size(min = 3, max = 250)
    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 5000)
    private String note;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permissionId"))
    private Set<Permission> permissions = new HashSet<>();

    public Role(String name) {
        this.name = name;
    }

    public void addPermission(Permission permission) {
        this.getPermissions().add(permission);
        permission.getRoles().add(this);
    }

    public void removePermission(Permission permission) {
        this.getPermissions().remove(permission);
        permission.getRoles().remove(this);
    }
}
