package com.session.auth.config;

import com.session.auth.service.audit.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Created by : Vannaravuth YO
 * Date  : 15-May-22, Sunday
 * Name  : 10:21 PM
 */

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class PersistenceConfig {

    @Bean
    AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }

}
