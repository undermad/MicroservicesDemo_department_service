package com.ectimel.departmentservice.repository;

import com.ectimel.departmentservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findDepartmentByDepartmentCode(String departmentCode);
}
