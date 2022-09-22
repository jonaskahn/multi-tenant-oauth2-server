package io.github.tuyendev.auth.common.security;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface DomainUserDetailsService extends UserDetailsService {

    SecuredUserDetails loadUserByUserId(final Long userId);

    SecuredUserDetails loadUserByPreferredUsername(final String preferredUsername);
}
