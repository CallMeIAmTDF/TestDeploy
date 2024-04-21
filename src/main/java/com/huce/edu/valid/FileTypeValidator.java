package com.huce.edu.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileTypeValidator implements ConstraintValidator<FileType, MultipartFile> {
    private String[] allowedTypes;

    @Override
    public void initialize(FileType fileType) {
        this.allowedTypes = fileType.allowedTypes();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true; // Skip validation for empty files
        }

        String fileType = file.getContentType();
        for (String allowedType : allowedTypes) {
            if (fileType != null && fileType.equals(allowedType)) {
                return true;
            }
        }

        return false;
    }
}