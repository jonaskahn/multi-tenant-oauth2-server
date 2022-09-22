package io.github.tuyendev.auth.common.repository;

import io.github.tuyendev.auth.common.entity.rdb.AuthorizationConsent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorizationConsentRepository extends JpaRepository<AuthorizationConsent, AuthorizationConsent.AuthorizationConsentId> {
    Optional<AuthorizationConsent> findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);

    void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}