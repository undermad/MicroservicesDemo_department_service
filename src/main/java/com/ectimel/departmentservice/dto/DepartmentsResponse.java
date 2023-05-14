package com.ectimel.departmentservice.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentsResponse {

    public List<DepartmentDto> content;

    public int pageNo;
    public int pageSize;
    public long totalElements;
    public int totalPages;
    public boolean last;
}
