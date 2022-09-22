package io.github.tuyendev.auth.common.repository;

import io.github.tuyendev.auth.common.CommonConstants;
import io.github.tuyendev.auth.common.entity.rdb.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    boolean existsByNameAndStatus(final String name, final Boolean status);

    default boolean existsEnabledByName(final String name) {
        return existsByNameAndStatus(name, CommonConstants.EntityStatus.ENABLED);
    }
}