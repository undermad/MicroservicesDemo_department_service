package com.ectimel.departmentservice.dto;

import java.util.Date;

public record ErrorDetails(Date date, String message, String details) {
}

