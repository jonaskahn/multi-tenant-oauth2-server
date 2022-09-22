package io.github.tuyendev.auth.common.configurer;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import io.github.tuyendev.auth.common.utils.JwksUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.time.Duration;

@Configuration(value = AuthorizationServiceConfigurer.BEAN_ID)
public class AuthorizationServiceConfigurer {

    public static final String BEAN_ID = "AuthorizationServiceConfigurer@000";

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer<>();
        RequestMatcher endpointsMatcher = authorizationServerConfigurer
                .getEndpointsMatcher();
        http.requestMatcher(endpointsMatcher)
                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .exceptionHandling(e -> e.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")))
                .apply(authorizationServerConfigurer);
        return http.build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = JwksUtil.buildDefaultRsaKey();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    public TokenSettings tokenSettings() {
        return TokenSettings.builder().accessTokenTimeToLive(Duration.ofMinutes(30L)).build();
    }

    @Bean
    JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public ProviderSettings providerSettings(@Value("${app.default.url}") String issuer) {
        return ProviderSettings.builder()
                .issuer(issuer)
                .authorizationEndpoint("/api/access/authorize")
                .tokenEndpoint("/api/access/token")
                .jwkSetEndpoint("/api/access/jwks")
                .tokenRevocationEndpoint("/api/access/revoke")
                .tokenIntrospectionEndpoint("/api/access/introspect")
                .oidcClientRegistrationEndpoint("/api/connect/register")
                .oidcUserInfoEndpoint("/api/userinfo")
                .build();
    }

}
