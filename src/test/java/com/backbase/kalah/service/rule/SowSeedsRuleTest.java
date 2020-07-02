package com.backbase.kalah.service.rule;

import com.backbase.kalah.model.Game;
import com.backbase.kalah.model.Pit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class SowSeedsRuleTest {

    private Integer initSeedsPerPit = (Integer) ReflectionTestUtils.getField(Game.class, "INIT_SEEDS_PER_PIT");
    private SowSeedsRule rule = new SowSeedsRule();
    private Game game;

    @BeforeEach
    public void setUp() {
        game = Game.create();
    }

    @Test
    void apply_shouldPut1SeedInEachNextPit() {
        //arrange
        var pit = game.getPitById(1).get();

        //act
        rule.apply(game, pit);

        //assert
        assertFalse(pit.hasSeeds());
        assertEquals(initSeedsPerPit + 1, game.getPitById(2).get().getSeedsCount());
        assertEquals(initSeedsPerPit + 1, game.getPitById(3).get().getSeedsCount());
        assertEquals(initSeedsPerPit + 1, game.getPitById(4).get().getSeedsCount());
        assertEquals(initSeedsPerPit + 1, game.getPitById(5).get().getSeedsCount());
        assertEquals(initSeedsPerPit + 1, game.getPitById(6).get().getSeedsCount());
        assertEquals(1, game.getPitById(7).get().getSeedsCount());
    }

    @Test
    void apply_shouldPut1SeedInEachNextPitExcludingOpponentKalah() {
        //arrange
        var pit = game.getPitById(5).get();
        pit.setSeedsCount(12);

        //act
        rule.apply(game, pit);

        //assert
        assertFalse(pit.hasSeeds());
        assertEquals(initSeedsPerPit + 1, game.getPitById(6).get().getSeedsCount());
        assertEquals(1, game.getPitById(7).get().getSeedsCount());
        assertEquals(initSeedsPerPit + 1, game.getPitById(8).get().getSeedsCount());
        assertEquals(initSeedsPerPit + 1, game.getPitById(9).get().getSeedsCount());
        assertEquals(initSeedsPerPit + 1, game.getPitById(10).get().getSeedsCount());
        assertEquals(initSeedsPerPit + 1, game.getPitById(11).get().getSeedsCount());
        assertEquals(initSeedsPerPit + 1, game.getPitById(12).get().getSeedsCount());
        assertEquals(initSeedsPerPit + 1, game.getPitById(13).get().getSeedsCount());
        assertEquals(0, game.getPitById(14).get().getSeedsCount());
    }

    @Test
    void apply_shouldReturnLastPit() {
        //arrange
        var pit = game.getPitById(1).get();
        pit.setSeedsCount(2);

        //act
        var lastPit = rule.apply(game, pit);

        //assert
        Pit expectedLastPit = game.getPitById(3).get();
        assertFalse(pit.hasSeeds());
        assertEquals(initSeedsPerPit + 1, game.getPitById(2).get().getSeedsCount());
        assertEquals(initSeedsPerPit + 1, expectedLastPit.getSeedsCount());
        assertSame(expectedLastPit, lastPit);
    }
}