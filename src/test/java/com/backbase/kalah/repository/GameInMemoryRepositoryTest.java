package com.backbase.kalah.repository;

import com.backbase.kalah.model.Game;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

class GameInMemoryRepositoryTest {

    @Test
    void save_shouldPutGameIntoRepo() {
        //arrange
        var repo = new GameInMemoryRepository();
        var game = Game.create();

        //act
        var savedGame = repo.save(game);

        //assert
        assertSame(game, savedGame);
    }

    @Test
    void findById_withNotExistingGame_shouldReturnEmpty() {
        //arrange
        var repo = new GameInMemoryRepository();

        //act && assert
        assertTrue(repo.findById(UUID.randomUUID()).isEmpty());
    }

    @Test
    void findById_withExistingGame_shouldReturnEmpty() {
        //arrange
        var repo = new GameInMemoryRepository();
        var game = Game.create();
        repo.save(game);

        //act && assert
        var savedGame = repo.findById(game.getId()).get();
        assertSame(game, savedGame);
    }
}