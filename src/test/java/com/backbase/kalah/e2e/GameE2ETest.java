package com.backbase.kalah.e2e;

import com.backbase.kalah.controller.GameController;
import lombok.extern.slf4j.Slf4j;
import com.backbase.kalah.presentation.NewGamePresentation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@RunWith(SpringRunner.class)
public class GameE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGameFlow() {
        //create game
        ResponseEntity<NewGamePresentation> newGameResponse = restTemplate.postForEntity(getEndpointPath(), null, NewGamePresentation.class);
        var game = newGameResponse.getBody();
        var id = game.getId();

        //trying all pits one by one including wrong ones to make sure it doesn't break on validation
        var pit = 1;
        var totalMoves = 0;
        while (true) {
            log.info("Trying pit {}", pit);
            ResponseEntity<String> response = restTemplate.exchange(getEndpointPath() + "/" + id + "/pits/" + pit, HttpMethod.PUT, null, String.class);
            pit = getNextPit(pit);
            String bodyString = response.getBody();
            log.info("Response {}", response);
            if (response.getStatusCode().is4xxClientError()) {
                if (bodyString.equals("Game is finished")) {
                    break;
                }
                continue;
            }
            totalMoves++;
        }

        assertEquals(19, totalMoves);
    }

    private int getNextPit(int pit) {
        var newPit = pit + 1;
        if (newPit > 14) {
            newPit = 1;
        }
        return newPit;
    }

    private String getEndpointPath() {
        RequestMapping requestMapping = GameController.class.getAnnotation(RequestMapping.class);
        return requestMapping.value()[0];
    }
}