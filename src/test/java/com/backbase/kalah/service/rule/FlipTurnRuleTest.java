package com.backbase.kalah.service.rule;

import com.backbase.kalah.model.Game;
import com.backbase.kalah.model.Pit;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FlipTurnRuleTest {

    @Test
    void apply_withCurrentPlayerPit_shouldNotFlip() {
        //arrange
        var rule = new FlipTurnRule();
        var game = mock(Game.class);
        var pit = new Pit(1,1,1);
        when(game.isCurrentPlayerPit(pit)).thenReturn(true);

        //act
        rule.apply(game, pit);

        //assert
        verify(game, never()).flipTurn();
    }

    @Test
    void apply_withNotCurrentPlayerPit_shouldFlip() {
        //arrange
        var rule = new FlipTurnRule();
        var game = mock(Game.class);
        var pit = new Pit(1,1,1);
        when(game.isCurrentPlayerPit(pit)).thenReturn(false);

        //act
        rule.apply(game, pit);

        //assert
        verify(game).flipTurn();
    }
}