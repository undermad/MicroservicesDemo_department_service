package com.ectimel.departmentservice.service.impl;

import com.ectimel.departmentservice.dto.DepartmentDto;
import com.ectimel.departmentservice.dto.DepartmentsResponse;
import com.ectimel.departmentservice.entity.Department;
import com.ectimel.departmentservice.repository.DepartmentRepository;
import com.ectimel.departmentservice.service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;
    private ModelMapper modelMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DepartmentDto findDepartmentByCode(String code) {

        Department department = departmentRepository
                .findDepartmentByDepartmentCode(code.toUpperCase());

        return modelMapper.map(department, DepartmentDto.class);
    }

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {

        Department department = modelMapper.map(departmentDto, Department.class);
        return modelMapper.map(departmentRepository.save(department), DepartmentDto.class);
    }

    @Override
    public DepartmentsResponse getAllDepartments(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir
                .equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Department> departmentsList = departmentRepository.findAll(pageable);
        List<Department> departments = departmentsList.getContent();

        List<DepartmentDto> departmentsDto = departments
                .stream()
                .map(d -> modelMapper.map(d, DepartmentDto.class))
                .toList();

        DepartmentsResponse departmentsResponse = new DepartmentsResponse();
        departmentsResponse.setContent(departmentsDto);
        departmentsResponse.setPageNo(pageNo);
        departmentsResponse.setPageSize(pageSize);
        departmentsResponse.setTotalElements(departmentsDto.size());
        departmentsResponse.setLast(departmentsList.isLast());

        return departmentsResponse;
    }
}
