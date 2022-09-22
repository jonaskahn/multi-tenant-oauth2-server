package io.github.tuyendev.auth.common.configurer;

import io.github.tuyendev.auth.common.CommonConstants;
import io.github.tuyendev.auth.common.entity.rdb.Authority;
import io.github.tuyendev.auth.common.entity.rdb.User;
import io.github.tuyendev.auth.common.repository.AuthorityRepository;
import io.github.tuyendev.auth.common.repository.UserRepository;
import io.github.tuyendev.auth.common.utils.PasswordGeneratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Configuration
public class BootstrapAppConfigurer {

    private final UserRepository userRepo;

    private final AuthorityRepository authorityRepo;

    private final PasswordEncoder passwordEncoder;

    private final RegisteredClientRepository registeredClientRepository;

    private final String adminUsername;

    private final String adminPassword;

    private final String adminEmail;

    private final String realmMasterId;

    private final String realmMasterSecretKey;

    public BootstrapAppConfigurer(UserRepository userRepo, AuthorityRepository authorityRepo, PasswordEncoder passwordEncoder,
                                  RegisteredClientRepository registeredClientRepository,
                                  @Value("${app.initial.admin.username}") String adminUsername,
                                  @Value("${app.initial.admin.password}") String adminPassword,
                                  @Value("${app.initial.admin.email}") String adminEmail,
                                  @Value("${app.initial.realm.client.default.id}") String realmMasterId,
                                  @Value("${app.initial.realm.master.secret-key}") String realmMasterSecretKey) {
        this.userRepo = userRepo;
        this.authorityRepo = authorityRepo;
        this.passwordEncoder = passwordEncoder;
        this.registeredClientRepository = registeredClientRepository;
        this.adminUsername = StringUtils.defaultIfBlank(adminUsername, "root");
        this.adminPassword = StringUtils.defaultIfBlank(adminPassword, "password");
        this.adminEmail = StringUtils.defaultIfBlank(adminEmail, "root@localhost");
        this.realmMasterId = realmMasterId;
        this.realmMasterSecretKey = realmMasterSecretKey;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional(rollbackFor = Exception.class)
    public void onReady() {
        createDefaultAuthority();
        createDefaultAdminUser();
        createDefaultAnonymousUser();
        createMasterRealm();
    }

    private void createMasterRealm() {
        if (Objects.nonNull(registeredClientRepository.findByClientId(realmMasterId))) {
            return;
        }
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(realmMasterId)
                .clientSecret(passwordEncoder.encode(realmMasterSecretKey))
                .clientIdIssuedAt(Instant.now())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("https://oidcdebugger.com/debug")
                .scope(OidcScopes.OPENID)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        registeredClientRepository.save(registeredClient);
    }

    private void createDefaultAuthority() {
        createAuthorityIfNotExists(CommonConstants.Authority.ADMIN);
        createAuthorityIfNotExists(CommonConstants.Authority.ADMIN_DEPUTY);
        createAuthorityIfNotExists(CommonConstants.Authority.GROUP);
        createAuthorityIfNotExists(CommonConstants.Authority.GROUP_DEPUTY);
        createAuthorityIfNotExists(CommonConstants.Authority.BASIC);
    }

    private void createAuthorityIfNotExists(String name) {
        if (!authorityRepo.existsEnabledByName(name)) {
            Authority authority = io.github.tuyendev.auth.common.entity.rdb.Authority.builder()
                    .name(name)
                    .description(name)
                    .status(CommonConstants.EntityStatus.ENABLED)
                    .build();
            authorityRepo.save(authority);
        }
    }

    private void createDefaultAdminUser() {
        if (!userRepo.existsById(CommonConstants.User.ADMIN_ID)) {
            User admin = User.builder()
                    .username(adminUsername)
                    .email(adminEmail)
                    .emailVerified(CommonConstants.EntityStatus.VERIFIED)
                    .password(passwordEncoder.encode(adminPassword))
                    .enabled(CommonConstants.EntityStatus.ENABLED)
                    .locked(CommonConstants.EntityStatus.UNLOCKED)
                    .build();
            admin.setId(CommonConstants.User.ADMIN_ID);
            List<Authority> authorities = authorityRepo.findAll();
            admin.getAuthorities().addAll(authorities);
            userRepo.save(admin);
        }
    }

    private void createDefaultAnonymousUser() {

        if (!userRepo.existsById(CommonConstants.User.ANONYMOUS_ID)) {
            User anonymous = User.builder()
                    .username("anonymous")
                    .email("anonymous@localhost")
                    .emailVerified(CommonConstants.EntityStatus.VERIFIED)
                    .password(passwordEncoder.encode(PasswordGeneratorUtils.generateStrongPassword()))
                    .enabled(CommonConstants.EntityStatus.ENABLED)
                    .locked(CommonConstants.EntityStatus.UNLOCKED)
                    .build();
            anonymous.setId(CommonConstants.User.ANONYMOUS_ID);
            userRepo.save(anonymous);
        }
    }
}
