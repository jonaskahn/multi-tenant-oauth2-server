package io.github.tuyendev.auth.common.repository;

import io.github.tuyendev.auth.common.CommonConstants;
import io.github.tuyendev.auth.common.entity.rdb.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPreferredUsernameAndEnabled(UUID preferredUsername, Boolean enabled);

    Optional<User> findByIdAndEnabled(Long id, Boolean enabled);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    default Optional<User> findEnabledByPreferredUsername(UUID preferredUsername) {
        return findByPreferredUsernameAndEnabled(preferredUsername, CommonConstants.EntityStatus.ENABLED);
    }

    default Optional<User> findEnabledById(Long id) {
        return findByIdAndEnabled(id, CommonConstants.EntityStatus.ENABLED);
    }
}