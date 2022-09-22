package io.github.tuyendev.auth.common.configurer;


import io.github.tuyendev.auth.common.CommonConstants;
import io.github.tuyendev.auth.common.utils.AppContextUtils;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

@Configuration
@EntityScan(value = {"io.github.tuyendev.auth.common.entity.rdb"})
@EnableJpaRepositories(value = {"io.github.tuyendev.auth.common.repository"})
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
class DatabaseAccessConfigurer {
    @Bean
    public AuditorAware<Long> auditorProvider() {
        return new DomainAuditorAware();
    }

    static class DomainAuditorAware implements AuditorAware<Long> {
        @Override
        public Optional<Long> getCurrentAuditor() {
            Optional<Authentication> authentication = Optional.ofNullable(SecurityContextHolder.getContext())
                    .map(SecurityContext::getAuthentication);
            if (authentication.isPresent() && Objects.equals("anonymousUser", authentication.get().getPrincipal())) {
                return Optional.of(CommonConstants.User.ANONYMOUS_ID);
            }
            return Optional.of(AppContextUtils.getCurrentLoginUserId());
        }
    }

}
