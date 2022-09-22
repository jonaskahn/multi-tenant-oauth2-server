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
@Table(name = "oauth2_client")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    @Size(max = 255)
    @Column(name = "id", nullable = false)
    private String id;

    @Size(max = 255)
    @NotNull
    @Column(name = "client_id", nullable = false)
    private String clientId;

    @NotNull
    @Column(name = "client_id_issued_at", nullable = false)
    private Instant clientIdIssuedAt;

    @Size(max = 255)
    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "client_secret_expires_at")
    private Instant clientSecretExpiresAt;

    @Size(max = 255)
    @NotNull
    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Size(max = 1000)
    @NotNull
    @Column(name = "client_authentication_methods", nullable = false, length = 1000)
    private String clientAuthenticationMethods;

    @Size(max = 1000)
    @NotNull
    @Column(name = "authorization_grant_types", nullable = false, length = 1000)
    private String authorizationGrantTypes;

    @Size(max = 1000)
    @Column(name = "redirect_uris", length = 1000)
    private String redirectUris;

    @Size(max = 1000)
    @NotNull
    @Column(name = "scopes", nullable = false, length = 1000)
    private String scopes;

    @NotNull
    @Column(name = "client_settings", nullable = false)
    private String clientSettings;

    @NotNull
    @Column(name = "token_settings", nullable = false)
    private String tokenSettings;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Client client = (Client) o;
        return id != null && Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}