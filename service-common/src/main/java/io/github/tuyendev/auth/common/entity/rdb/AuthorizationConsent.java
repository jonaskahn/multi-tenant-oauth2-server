package io.github.tuyendev.auth.common.entity.rdb;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "oauth2_authorization_consent")
@IdClass(AuthorizationConsent.AuthorizationConsentId.class)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorizationConsent {
    @Id
    private String registeredClientId;
    @Id
    private String principalName;
    @Column(length = 1000)
    private String authorities;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AuthorizationConsent that = (AuthorizationConsent) o;
        return registeredClientId != null && Objects.equals(registeredClientId, that.registeredClientId)
                && principalName != null && Objects.equals(principalName, that.principalName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registeredClientId, principalName);
    }

    @Data
    public static class AuthorizationConsentId implements Serializable {
        private String registeredClientId;
        private String principalName;
    }
}

