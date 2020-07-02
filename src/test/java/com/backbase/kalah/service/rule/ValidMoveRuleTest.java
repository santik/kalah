package com.backbase.kalah.service.rule;

import com.backbase.kalah.exception.KalahException;
import com.backbase.kalah.model.Game;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidMoveRuleTest {

    @Test
    void apply_withFinishedGame_shouldThrowException() {
        //arrange
        var rule = new ValidMoveRule();
        var game = Game.create();
        game.finish();
        var pit = game.getPitById(1).get();

        //act && assert
        assertThrows(KalahException.class, () -> rule.apply(game, pit));
    }

    @Test
    void apply_withKalahPit_shouldThrowException() {
        //arrange
        var rule = new ValidMoveRule();
        var game = Game.create();
        game.finish();
        var pit = game.getPitById(7).get();

        //act && assert
        assertThrows(KalahException.class, () -> rule.apply(game, pit));
    }

    @Test
    void apply_withNotCurrentPlayerPit_shouldThrowException() {
        //arrange
        var rule = new ValidMoveRule();
        var game = Game.create();
        game.finish();
        var pit = game.getPitById(8).get();

        //act && assert
        assertThrows(KalahException.class, () -> rule.apply(game, pit));
    }

    @Test
    void apply_withEmptyPit_shouldThrowException() {
        //arrange
        var rule = new ValidMoveRule();
        var game = Game.create();
        game.finish();
        var pit = game.getPitById(1).get();
        pit.setSeedsCount(0);

        //act && assert
        assertThrows(KalahException.class, () -> rule.apply(game, pit));
    }

    @Test
    void apply_AllOk_shouldReturnSamePit() {
        //arrange
        var rule = new ValidMoveRule();
        var game = Game.create();
        var pit = game.getPitById(1).get();

        //act && assert
        assertSame(pit, rule.apply(game, pit));
    }
}