package io.github.tuyendev.auth.common.entity.rdb;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "oauth2_authorization")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Authorization {
    @Id
    @Size(max = 255)
    @Column(name = "id", nullable = false)
    private String id;

    @Size(max = 255)
    @NotNull
    @Column(name = "registered_client_id", nullable = false)
    private String registeredClientId;

    @Size(max = 255)
    @NotNull
    @Column(name = "principal_name", nullable = false)
    private String principalName;

    @Size(max = 255)
    @NotNull
    @Column(name = "authorization_grant_type", nullable = false)
    private String authorizationGrantType;

    @Column(name = "attributes")
    private String attributes;

    @Size(max = 500)
    @Column(name = "state", length = 500)
    private String state;

    @Column(name = "authorization_code_value")
    private String authorizationCodeValue;

    @Column(name = "authorization_code_issued_at")
    private Instant authorizationCodeIssuedAt;

    @Column(name = "authorization_code_expires_at")
    private Instant authorizationCodeExpiresAt;

    @Column(name = "authorization_code_metadata")
    private String authorizationCodeMetadata;

    @Column(name = "access_token_value")
    private String accessTokenValue;

    @Column(name = "access_token_issued_at")
    private Instant accessTokenIssuedAt;

    @Column(name = "access_token_expires_at")
    private Instant accessTokenExpiresAt;

    @Column(name = "access_token_metadata")
    private String accessTokenMetadata;

    @Size(max = 255)
    @Column(name = "access_token_type")
    private String accessTokenType;

    @Size(max = 1000)
    @Column(name = "access_token_scopes", length = 1000)
    private String accessTokenScopes;

    @Column(name = "refresh_token_value")
    private String refreshTokenValue;

    @Column(name = "refresh_token_issued_at")
    private Instant refreshTokenIssuedAt;

    @Column(name = "refresh_token_expires_at")
    private Instant refreshTokenExpiresAt;

    @Column(name = "refresh_token_metadata")
    private String refreshTokenMetadata;

    @Column(name = "oidc_id_token_value")
    private String oidcIdTokenValue;

    @Column(name = "oidc_id_token_issued_at")
    private Instant oidcIdTokenIssuedAt;

    @Column(name = "oidc_id_token_expires_at")
    private Instant oidcIdTokenExpiresAt;

    @Column(name = "oidc_id_token_metadata")
    private String oidcIdTokenMetadata;

    @Column(name = "oidc_id_token_claims")
    private String oidcIdTokenClaims;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Authorization that = (Authorization) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}