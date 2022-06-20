package com.session.auth;

import com.session.auth.model.User;
import com.session.auth.model.audit.AuditRevisionEntity;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by : Vannaravuth YO
 * Date  : 21-May-22, Saturday
 * Name  : 2:09 AM
 */
public class AuditWithRevisionTest extends BaseTest {
    @Autowired
    private AuditReader auditReader;

    @Test
    public void can_list_audit_revisions() {
//        AuditQuery q = auditReader.createQuery().forRevisionsOfEntity(User.class, false, true);
//        q.addProjection(AuditEntity.revisionNumber());
//        q.add(AuditEntity.revisionProperty("auditor").eq("admin"));
//        q.addOrder(AuditEntity.revisionNumber().desc());
//        List<Number> revisionNumbers = q.getResultList();

//        auditReader.getRevisions(User.class, 1L).forEach(System.out::println);
        auditReader.getRevisions(User.class, 1L).forEach(revision -> {
            Date revisionDate = auditReader.getRevisionDate(revision);
            Number revisionNumber = auditReader.getRevisionNumberForDate(revisionDate);
            System.out.printf("\nUser revision: %s %s \n", revision, revisionDate);
            System.out.printf("\nUser revision: %s %s \n", revision, revisionNumber);
        });

        AuditQuery query = auditReader.createQuery().forEntitiesAtRevision(User.class, 1);
//        query.add(AuditEntity.relatedId("company").eq(1));
//        query.add(AuditEntity.property("lastName").eq("Spencer"));
//        query.add(AuditEntity.or(AuditEntity.property("lastName").eq("Spencer"), AuditEntity.property("lastName").eq("Robinson")));
        List<User> resultList = (List<User>) query.getResultList();
        for (User entity : resultList) {
            System.out.println("User entity: " + entity.getId() + " => " + entity.getFullName() + "\n");
        }

        AuditQuery query1 = auditReader.createQuery().forRevisionsOfEntity(User.class, false, true);
        query1.add(AuditEntity.id().eq(1L));
        List<Object[]> results = query1.getResultList();
        if (!CollectionUtils.isEmpty(results)) {
            for (Object[] result : results) {
                User user = (User) result[0];
                AuditRevisionEntity revEntity = (AuditRevisionEntity) result[1];
                RevisionType revType = (RevisionType) result[2];

                System.out.println("Revision     : " + revEntity.getId());
                System.out.println("Revision Date: " + revEntity.getRevisionDate());
                System.out.println("User         : " + revEntity.getAuditor());
                System.out.println("Type         : " + revType);
                System.out.println("User         : " + user.getLastName() + " " + user.getFirstName());
                System.out.println("------------------------------------------------");
            }
        }

    }
}
