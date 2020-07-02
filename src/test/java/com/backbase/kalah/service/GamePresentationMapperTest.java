package com.backbase.kalah.service;

import com.backbase.kalah.model.Game;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

class GamePresentationMapperTest {

    @Test
    void getNewGamePresentation_shouldReturnCorrectPresentation() {
        //arrange
        var mapper = new GamePresentationMapper();
        var game = Game.create();

        //act
        var newGamePresentation = mapper.getNewGamePresentation(game);

        //assert
        assertEquals(game.getId().toString(), newGamePresentation.getId());
        assertNotNull(newGamePresentation.getUri());
    }

    @Test
    void getGamePresentation_shouldReturnCorrectPresentation() {
        //arrange
        var mapper = new GamePresentationMapper();
        var game = Game.create();

        //act
        var gamePresentation = mapper.getGamePresentation(game);

        //assert
        assertEquals(game.getId().toString(), gamePresentation.getId());
        assertNotNull(gamePresentation.getUrl());
        assertEquals(game.getBoard().size() - 1, gamePresentation.getStatus().size());
    }
}