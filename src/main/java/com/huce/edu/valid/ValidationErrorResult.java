package com.huce.edu.valid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class ValidationErrorResult {

    private HttpStatus httpStatus;
    private String message;
    private List<Violation> violations = new ArrayList<>();

    // ...
}