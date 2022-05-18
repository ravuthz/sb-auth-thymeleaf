package com.session.auth.service.audit;

import com.session.auth.model.User;
import com.session.auth.model.audit.AuditRevisionEntity;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Created by : Vannaravuth YO
 * Date  : 16-May-22, Monday
 * Name  : 12:18 AM
 */
public class AuditRevisionListener implements RevisionListener {
    @Override
    public void newRevision(Object revisionEntity) {
        String currentUser = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(User.class::cast)
                .map(User::getUsername)
                .orElse(User.DEFAULT_USER);

        AuditRevisionEntity audit = (AuditRevisionEntity) revisionEntity;
        audit.setAuditor(currentUser);
    }
}
