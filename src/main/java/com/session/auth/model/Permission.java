package com.session.auth.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Audited
@Getter
@Setter
@Entity
@Table(name = "permissions")
@ToString
public class Permission extends BaseEntity {

    private static final long serialVersionUID = -7085459662547835139L;

    @NotEmpty
    @Size(min = 3, max = 250)
    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 5000)
    private String note;

    @ToString.Exclude
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();

}
