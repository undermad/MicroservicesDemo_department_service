package com.ectimel.departmentservice.service.impl;

import com.ectimel.departmentservice.dto.DepartmentDto;
import com.ectimel.departmentservice.dto.DepartmentsResponse;
import com.ectimel.departmentservice.entity.Department;
import com.ectimel.departmentservice.exception.DepartmentCodeAlreadyExistException;
import com.ectimel.departmentservice.exception.ResourceNotFoundException;
import com.ectimel.departmentservice.repository.DepartmentRepository;
import com.ectimel.departmentservice.service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        Optional<Department> departmentAsOptional = departmentRepository
                .findDepartmentByDepartmentCode(code);

        if (departmentAsOptional.isEmpty()) {
            throw new ResourceNotFoundException("Department", "code", code);
        }

        Department department = departmentAsOptional.get();

        return modelMapper.map(department, DepartmentDto.class);
    }

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {

        Optional<Department> dep = departmentRepository
                .findDepartmentByDepartmentCode(departmentDto.getDepartmentCode());

        if(dep.isPresent()){
            throw new DepartmentCodeAlreadyExistException(departmentDto.getDepartmentCode());
        }

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

        return DepartmentsResponse.builder()
                .content(departmentsDto)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalElements(departmentsDto.size())
                .last(departmentsList.isLast())
                .build();
    }
}
