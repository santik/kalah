package com.backbase.kalah.controller;

import com.backbase.kalah.exception.KalahException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

class KalahExceptionHandlerTest {

    @Test
    void handle_withKalahException_shouldReturnBadRequestWithMessage() {
        //arrange
        var handler = new KalahExceptionHandler();
        var message = "some message";
        var exception = new KalahException(message);

        //act
        ResponseEntity<String> response = handler.handle(exception);

        //assert
        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertEquals(message, response.getBody());
    }
}
