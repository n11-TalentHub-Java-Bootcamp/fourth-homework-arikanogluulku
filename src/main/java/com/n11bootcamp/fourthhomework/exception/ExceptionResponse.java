package com.n11bootcamp.fourthhomework.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ExceptionResponse {

    private LocalDate errorDate;

    private String message;

    private String detail;
}
