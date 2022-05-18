package com.session.auth.model;

import com.session.auth.model.audit.AuditBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity extends AuditBase<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    private Date createdAt;
//    private Date updatedAt;
//    @PreUpdate
//    @PrePersist
//    public void updateTimeStamps() {
//        updatedAt = new Date();
//        if (createdAt == null) {
//            createdAt = new Date();
//        }
//    }
}
