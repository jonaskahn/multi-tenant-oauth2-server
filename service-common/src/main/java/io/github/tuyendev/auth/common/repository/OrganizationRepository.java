package io.github.tuyendev.auth.common.repository;

import io.github.tuyendev.auth.common.entity.rdb.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}