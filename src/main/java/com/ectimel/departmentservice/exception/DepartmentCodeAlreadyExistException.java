package com.ectimel.departmentservice.exception;

public class DepartmentCodeAlreadyExistException extends RuntimeException{

    public DepartmentCodeAlreadyExistException(String code) {
        super(String.format("Department with code %s already exist", code));
    }
}
