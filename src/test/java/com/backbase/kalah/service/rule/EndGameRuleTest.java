package com.backbase.kalah.service.rule;

import com.backbase.kalah.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

class EndGameRuleTest {

    private EndGameRule rule = new EndGameRule();
    private Game game;

    @BeforeEach
    public void setUp() {
        game = Game.create();
    }

    @Test
    void apply_withNotEmptyPits_shouldKeepGameRunning() {
        //arrange
        var pit = game.getBoard().get(1);

        //act
        rule.apply(game, pit);

        //assert
        assertFalse(game.isFinished());
    }

    @Test
    void apply_withEmptyPits_shouldFinishGame() {
        //arrange
        game.getBoard().forEach(pit -> {
            if (pit != null) {
                pit.setSeedsCount(0);
            }
        });
        var pit = game.getBoard().get(1);

        //act
        rule.apply(game, pit);

        //assert
        assertTrue(game.isFinished());
    }

    @Test
    void apply_withEmptyPitsAndEqual_shouldNotSetWinner() {
        //arrange
        game.getBoard().forEach(pit -> {
            if (pit != null) {
                pit.setSeedsCount(0);
            }
        });
        var pit = game.getBoard().get(1);

        //act
        rule.apply(game, pit);

        //assert
        assertNull(game.getWinner());
    }

    @Test
    void apply_withEmptyPitsAndNotEqual_shouldSetWinner() {
        //arrange
        game.getBoard().forEach(pit -> {
            if (pit != null) {
                pit.setSeedsCount(0);
            }
        });
        var pit = game.getBoard().get(1);
        pit.setSeedsCount(1);

        //act
        rule.apply(game, pit);

        //assert
        assertEquals(1, game.getWinner().intValue());
    }
}