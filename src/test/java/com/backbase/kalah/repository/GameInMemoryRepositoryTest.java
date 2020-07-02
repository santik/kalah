package com.backbase.kalah.repository;

import com.backbase.kalah.model.Game;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

class GameInMemoryRepositoryTest {

    private GameInMemoryRepository repo = new GameInMemoryRepository();

    @Test
    void save_shouldPutGameIntoRepo() {
        //arrange
        var game = Game.create();

        //act
        var savedGame = repo.save(game);

        //assert
        assertSame(game, savedGame);
    }

    @Test
    void findById_withNotExistingGame_shouldReturnEmpty() {
        //act && assert
        assertTrue(repo.findById(UUID.randomUUID()).isEmpty());
    }

    @Test
    void findById_withExistingGame_shouldReturnEmpty() {
        //arrange
        var game = Game.create();
        repo.save(game);

        //act && assert
        var savedGame = repo.findById(game.getId()).get();
        assertSame(game, savedGame);
    }
}