package com.session.auth.config;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

/**
 * Created by : Vannaravuth YO
 * Date  : 16-May-22, Monday
 * Name  : 1:05 AM
 */
@Configuration
public class AuditConfig {

    private final EntityManagerFactory entityManagerFactory;

    AuditConfig(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    AuditReader auditReader() {
        return AuditReaderFactory.get(entityManagerFactory.createEntityManager());
    }

}
