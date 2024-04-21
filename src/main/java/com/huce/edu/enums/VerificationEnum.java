package com.huce.edu.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum VerificationEnum {
    SUCCESS,
    FAILED,
    TIME_OUT,
    FAIL_ATTEMPT,
    NOT_FOUND
}
