package com.ectimel.departmentservice.service;

import com.ectimel.departmentservice.dto.DepartmentDto;
import com.ectimel.departmentservice.dto.DepartmentsResponse;

public interface DepartmentService {
    DepartmentDto findDepartmentByCode(String code);
    DepartmentDto createDepartment(DepartmentDto departmentDto);
    DepartmentsResponse getAllDepartments(int pageNo, int pageSize, String sortBy, String sortDir);

}
