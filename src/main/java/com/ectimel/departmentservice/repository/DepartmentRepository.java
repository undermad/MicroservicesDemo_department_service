package com.ectimel.departmentservice.repository;

import com.ectimel.departmentservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Department findDepartmentByDepartmentCode(String departmentCode);
}
