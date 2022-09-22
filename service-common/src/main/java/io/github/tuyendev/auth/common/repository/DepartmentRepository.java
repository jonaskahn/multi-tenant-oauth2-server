package io.github.tuyendev.auth.common.repository;

import io.github.tuyendev.auth.common.entity.rdb.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}