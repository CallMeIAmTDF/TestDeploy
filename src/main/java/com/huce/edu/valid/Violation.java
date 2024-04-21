package com.huce.edu.valid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Violation {

    private final String fieldName;

    private final String message;

    // ...
}