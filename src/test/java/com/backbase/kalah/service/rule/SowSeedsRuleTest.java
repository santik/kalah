package com.backbase.kalah.service.rule;

import com.backbase.kalah.model.Game;
import com.backbase.kalah.model.Pit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SowSeedsRuleTest {

    @Test
    void apply_shouldPut1SeedInEachNextPit() {
        //arrange
        var rule = new SowSeedsRule();
        var game = Game.create();
        var pit = game.getPitByIndex(1).get();

        //act
        rule.apply(game, pit);

        //assert
        assertFalse(pit.hasSeeds());
        assertEquals(7, game.getPitByIndex(2).get().getSeedsCount());
        assertEquals(7, game.getPitByIndex(3).get().getSeedsCount());
        assertEquals(7, game.getPitByIndex(4).get().getSeedsCount());
        assertEquals(7, game.getPitByIndex(5).get().getSeedsCount());
    }

    @Test
    void apply_shouldPut1SeedInEachNextPitIncludingSelfKalah() {
        //arrange
        var rule = new SowSeedsRule();
        var game = Game.create();
        var pit = game.getPitByIndex(5).get();

        //act
        rule.apply(game, pit);

        //assert
        assertFalse(pit.hasSeeds());
        assertEquals(7, game.getPitByIndex(6).get().getSeedsCount());
        assertEquals(1, game.getPitByIndex(7).get().getSeedsCount());
        assertEquals(7, game.getPitByIndex(8).get().getSeedsCount());
        assertEquals(7, game.getPitByIndex(9).get().getSeedsCount());
    }

    @Test
    void apply_shouldPut1SeedInEachNextPitExcludingOpponentKalah() {
        //arrange
        var rule = new SowSeedsRule();
        var game = Game.create();
        var pit = game.getPitByIndex(5).get();
        pit.setSeedsCount(12);

        //act
        rule.apply(game, pit);

        //assert
        assertFalse(pit.hasSeeds());
        assertEquals(7, game.getPitByIndex(6).get().getSeedsCount());
        assertEquals(1, game.getPitByIndex(7).get().getSeedsCount());
        assertEquals(7, game.getPitByIndex(8).get().getSeedsCount());
        assertEquals(7, game.getPitByIndex(9).get().getSeedsCount());
        assertEquals(7, game.getPitByIndex(10).get().getSeedsCount());
        assertEquals(7, game.getPitByIndex(11).get().getSeedsCount());
        assertEquals(7, game.getPitByIndex(12).get().getSeedsCount());
        assertEquals(7, game.getPitByIndex(13).get().getSeedsCount());
        assertEquals(0, game.getPitByIndex(14).get().getSeedsCount());
    }

    @Test
    void apply_shouldReturnLastPit() {
        //arrange
        var rule = new SowSeedsRule();
        var game = Game.create();
        var pit = game.getPitByIndex(1).get();
        pit.setSeedsCount(2);

        //act
        var lastPit = rule.apply(game, pit);

        //assert
        Pit expectedLastPit = game.getPitByIndex(3).get();
        assertFalse(pit.hasSeeds());
        assertEquals(7, game.getPitByIndex(2).get().getSeedsCount());
        assertEquals(7, expectedLastPit.getSeedsCount());
        assertSame(expectedLastPit, lastPit);
    }
}