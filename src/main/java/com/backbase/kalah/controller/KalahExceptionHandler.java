package com.backbase.kalah.controller;

import com.backbase.kalah.exception.KalahException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class KalahExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(KalahException.class)
    public final ResponseEntity<String> handle(final KalahException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
