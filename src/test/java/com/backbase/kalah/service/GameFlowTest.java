package com.backbase.kalah.service;

import com.backbase.kalah.model.Game;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GameFlowTest {

    @Test
    void makeMove_shouldCallEveryRule() {
        //arrange
        var game = Game.create();
        var pit = game.getPitById(1).get();

        var rule1 = mock(GameFlowRule.class);
        when(rule1.apply(game, pit)).thenReturn(pit);

        var rule2 = mock(GameFlowRule.class);
        when(rule2.apply(game, pit)).thenReturn(pit);

        var list = List.of(rule1, rule2);
        var gameFlow = new GameFlow(list);

        //act
        gameFlow.makeMove(game, pit);

        //assert
        verify(rule1).apply(game, pit);
        verify(rule2).apply(game, pit);

    }
}