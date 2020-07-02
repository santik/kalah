package com.backbase.kalah.controller;

import com.backbase.kalah.exception.KalahException;
import com.backbase.kalah.presentation.GamePresentation;
import com.backbase.kalah.presentation.NewGamePresentation;
import com.backbase.kalah.service.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@RunWith(SpringRunner.class)
public class GameControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GameService gameService;


    @Test
    public void create_shouldReturnCreated() {
        //arrange
        var expectedPresentation = NewGamePresentation.builder().build();
        when(gameService.initGame()).thenReturn(expectedPresentation);

        //act
        ResponseEntity<NewGamePresentation> response = restTemplate.postForEntity(getEndpointPath(), null, NewGamePresentation.class);
        var actualPresentation = response.getBody();

        //assert
        assertEquals(CREATED, response.getStatusCode());
        assertEquals(expectedPresentation, actualPresentation);
    }

    @Test
    public void move_withValidMove_shouldReturnStatus() throws JsonProcessingException {
        //arrange
        var expectedPresentation = GamePresentation.builder().build();
        when(gameService.makeMove(any(UUID.class), anyInt())).thenReturn(expectedPresentation);

        //act
        var id = UUID.randomUUID().toString();
        var pit = 1;
        ResponseEntity<String> response = restTemplate.exchange(getEndpointPath() + "/" + id + "/pits/" + pit, HttpMethod.PUT, null, String.class);
        var actualPresentation = objectMapper.readValue(response.getBody(), GamePresentation.class);
        //assert
        assertEquals(OK, response.getStatusCode());
        assertEquals(expectedPresentation, actualPresentation);
    }

    @Test
    public void create_withInvalidMove_shouldReturnBadRequest() {
        //arrange
        when(gameService.makeMove(any(UUID.class), anyInt())).thenThrow(KalahException.class);

        //act
        var id = UUID.randomUUID().toString();
        var pit = 1;
        ResponseEntity<String> response = restTemplate.exchange(getEndpointPath() + "/" + id + "/pits/" + pit, HttpMethod.PUT, null, String.class);

        //assert
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void get_withExistingGame_shouldReturnStatus() {
        //arrange
        var expectedPresentation = GamePresentation.builder().build();
        when(gameService.getGame(any(UUID.class))).thenReturn(expectedPresentation);

        //act
        var id = UUID.randomUUID().toString();
        ResponseEntity<GamePresentation> response = restTemplate.getForEntity(getEndpointPath() + "/" + id, GamePresentation.class);

        //assert
        assertEquals(OK, response.getStatusCode());
        assertEquals(expectedPresentation, response.getBody());
    }

    @Test
    public void get_withNotExistingGame_shouldReturnBadRequest() {
        //arrange
        when(gameService.getGame(any(UUID.class))).thenThrow(KalahException.class);

        //act
        var id = UUID.randomUUID().toString();
        ResponseEntity<GamePresentation> response = restTemplate.getForEntity(getEndpointPath() + "/" + id, GamePresentation.class);

        //assert
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    private String getEndpointPath() {
        RequestMapping requestMapping = GameController.class.getAnnotation(RequestMapping.class);
        return requestMapping.value()[0];
    }
}
