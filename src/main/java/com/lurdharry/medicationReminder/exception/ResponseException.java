package com.lurdharry.medicationReminder.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class ResponseException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String message;

    public ResponseException( String message, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
