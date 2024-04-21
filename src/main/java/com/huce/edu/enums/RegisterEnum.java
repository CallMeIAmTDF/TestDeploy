package com.huce.edu.enums;

import com.huce.edu.shareds.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegisterEnum {
    SUCCESS(Constants.CONFIRM_EMAIL),
    DUPLICATE_EMAIL(Constants.DUPLICATE_ERROR_EMAIL),
    DUPLICATE_USERNAME(Constants.DUPLICATE_ERROR_USERNAME),
    DUPLICATE_ID(Constants.DUPLICATE_ERROR_ID);
    private final String description;

}
