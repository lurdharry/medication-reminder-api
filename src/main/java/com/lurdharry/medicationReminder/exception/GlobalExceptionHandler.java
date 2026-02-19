package com.lurdharry.medicationReminder.exception;

import com.lurdharry.medicationReminder.exception.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<ResponseDTO> res(ResponseException exception) {

        return ResponseEntity.status(exception.getHttpStatus())
                .body(ResponseDTO.builder()
                        .statusCode(exception.getCode())
                        .message(exception.getMessage())
                        .build()
                );
    }
}
