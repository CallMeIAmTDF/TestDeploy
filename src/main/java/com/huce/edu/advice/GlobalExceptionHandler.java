package com.huce.edu.advice;



import com.huce.edu.advice.exceptions.DuplicateRecordException;
import com.huce.edu.advice.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResult handlerNotFoundException(NotFoundException ex, WebRequest req) {
        System.out.println(req);

        return new ErrorResult(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DuplicateRecordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult handlerDuplicateRecordException(DuplicateRecordException ex, WebRequest req) {
        System.out.println(req);

        return new ErrorResult(HttpStatus.CONFLICT, ex.getMessage());
    }




}
