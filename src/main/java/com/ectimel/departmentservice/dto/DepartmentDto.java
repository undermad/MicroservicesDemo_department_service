package com.ectimel.departmentservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {
    private long id;
    private String department;
    private String departmentDescription;
    private String departmentCode;
}
