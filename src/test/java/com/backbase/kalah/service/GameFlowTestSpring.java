package com.backbase.kalah.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@RunWith(SpringRunner.class)
class GameFlowTestSpring {

    @Autowired
    private GameFlow gameFlow;

    @Test
    void makeMove_shouldContainRulesInRightOrder() {
        List<GameFlowRule> rules = (List<GameFlowRule>) ReflectionTestUtils.getField(gameFlow, "rules");
        assertEquals("ValidMoveRule", rules.get(0).getClass().getSimpleName());
        assertEquals("SowSeedsRule", rules.get(1).getClass().getSimpleName());
        assertEquals("LastWasEmptyRule", rules.get(2).getClass().getSimpleName());
        assertEquals("FlipTurnRule", rules.get(3).getClass().getSimpleName());
        assertEquals("EndGameRule", rules.get(4).getClass().getSimpleName());
    }
}