package com.backbase.kalah.service.rule;

import com.backbase.kalah.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class LastWasEmptyRuleTest {

    private LastWasEmptyRule rule = new LastWasEmptyRule();
    private Game game;

    @BeforeEach
    public void setUp() {
        game = Game.create();
    }

    @Test
    void apply_withEmptyPitAndHasOpposite_shouldPutSeedsInKalah() {
        //arrange
        var pit = game.getPitById(1).get();
        pit.setSeedsCount(1);

        //act
        rule.apply(game, pit);

        //assert
        assertEquals(0, game.getOppositeTo(pit).getSeedsCount().intValue());
        assertEquals(7, game.getCurrentPlayerKalah().getSeedsCount().intValue());
    }

    @Test
    void apply_withNotEmptyPit_shouldNotPutSeedsInKalah() {
        //arrange
        var pit = game.getPitById(1).get();
        pit.setSeedsCount(2);

        //act
        rule.apply(game, pit);

        //assert
        assertEquals(6, game.getOppositeTo(pit).getSeedsCount().intValue());
        assertEquals(0, game.getCurrentPlayerKalah().getSeedsCount().intValue());
    }

    @Test
    void apply_withEmptyPitAndHasNoOpposite_shouldNotPutSeedsInKalah() {
        //arrange
        var pit = game.getPitById(1).get();
        pit.setSeedsCount(1);
        game.getOppositeTo(pit).setSeedsCount(0);

        //act
        rule.apply(game, pit);

        //assert
        assertEquals(0, game.getOppositeTo(pit).getSeedsCount().intValue());
        assertEquals(0, game.getCurrentPlayerKalah().getSeedsCount().intValue());
        assertEquals(1, pit.getSeedsCount().intValue());
    }
}