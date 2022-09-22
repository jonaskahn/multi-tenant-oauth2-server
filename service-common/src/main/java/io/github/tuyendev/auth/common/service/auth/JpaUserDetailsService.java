package io.github.tuyendev.auth.common.service.auth;

import io.github.tuyendev.auth.common.security.DomainUserDetailsService;
import io.github.tuyendev.auth.common.security.SecuredUserDetails;
import io.github.tuyendev.auth.common.service.userinfo.UserInfoDto;
import io.github.tuyendev.auth.common.service.userinfo.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Slf4j
@Service
public class JpaUserDetailsService implements DomainUserDetailsService {

    private static final EmailValidator emailValidator = new EmailValidator();

    private final UserInfoService userService;

    public JpaUserDetailsService(UserInfoService userService) {
        this.userService = userService;
    }

    @Override
    public SecuredUserDetails loadUserByUsername(String principal) throws UsernameNotFoundException {
        log.debug("start authenticating {}", principal);
        final String lowercasePrincipal = principal.toLowerCase(Locale.ENGLISH);
        UserInfoDto user = emailValidator.isValid(principal, null) ? userService.findUserByEmail(lowercasePrincipal)
                : userService.findUserByUsername(lowercasePrincipal);
        return new SecuredUserDetails(user, lowercasePrincipal);
    }

    @Override
    public SecuredUserDetails loadUserByUserId(Long userId) {
        UserInfoDto user = userService.findActiveUserById(userId);
        return new SecuredUserDetails(user, userId.toString());
    }

    @Override
    public SecuredUserDetails loadUserByPreferredUsername(String preferredUsername) {
        UserInfoDto user = userService.findActiveUserByPreferredUsername(preferredUsername);
        return new SecuredUserDetails(user, preferredUsername);
    }
}
