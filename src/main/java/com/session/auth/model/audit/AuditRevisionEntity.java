package com.session.auth.model.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.session.auth.service.audit.AuditRevisionListener;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by : Vannaravuth YO
 * Date  : 16-May-22, Monday
 * Name  : 12:20 AM
 */

@Setter
@Getter
@Entity
@Table(name = "revision_info")
@RevisionEntity(AuditRevisionListener.class)
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "revision_id")),
        @AttributeOverride(name = "timestamp", column = @Column(name = "revision_timestamp"))
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AuditRevisionEntity implements Serializable {

    private static final long serialVersionUID = 3024382334682049818L;

    @Id
    @GeneratedValue
    @RevisionNumber
    private int id;

    @RevisionTimestamp
    private long timestamp;

    @Column(name = "auditor")
    private String auditor;

    @Transient
    public Date getRevisionDate() {
        return new Date(this.timestamp);
    }
}