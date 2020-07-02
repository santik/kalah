package com.backbase.kalah.service;

import com.backbase.kalah.exception.KalahException;
import com.backbase.kalah.model.Game;
import com.backbase.kalah.presentation.GamePresentation;
import com.backbase.kalah.presentation.NewGamePresentation;
import com.backbase.kalah.repository.GameRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GameServiceTest {

    @Test
    void initGame_shouldCallRepoAndReturnCorrectPresentation() {
        //arrange
        var gameRepository = mock(GameRepository.class);
        var mapper = mock(GamePresentationMapper.class);
        var gameFlow = mock(GameFlow.class);
        var service = new GameService(gameRepository, mapper, gameFlow);

        var game = Game.create();
        when(gameRepository.save(any(Game.class))).thenReturn(game);
        var presentation = NewGamePresentation.builder().build();
        when(mapper.getNewGamePresentation(game)).thenReturn(presentation);

        //act
        var newGamePresentation = service.initGame();

        //assert
        assertSame(presentation, newGamePresentation);
    }

    @Test
    void makeMove_withExistingGameAndPit_shouldCallFlowAndReturnCorrectPresentation() {
        //arrange
        var gameRepository = mock(GameRepository.class);
        var mapper = mock(GamePresentationMapper.class);
        var gameFlow = mock(GameFlow.class);
        var service = new GameService(gameRepository, mapper, gameFlow);

        var game = Game.create();
        UUID id = UUID.randomUUID();
        when(gameRepository.findById(id)).thenReturn(Optional.of(game));
        var presentation = GamePresentation.builder().build();
        when(mapper.getGamePresentation(game)).thenReturn(presentation);

        //act
        var newGamePresentation = service.makeMove(id, 1);

        //assert
        verify(gameFlow).makeMove(game, game.getPitByIndex(1).get());
        assertSame(presentation, newGamePresentation);
    }

    @Test
    void makeMove_withNotExistingGame_shouldThrowException() {
        //arrange
        var gameRepository = mock(GameRepository.class);
        var mapper = mock(GamePresentationMapper.class);
        var gameFlow = mock(GameFlow.class);
        var service = new GameService(gameRepository, mapper, gameFlow);

        UUID id = UUID.randomUUID();
        when(gameRepository.findById(id)).thenReturn(Optional.empty());

        //act && assert
        assertThrows(KalahException.class, () -> service.makeMove(id, 1));
    }

    @Test
    void makeMove_withExistingGameAndNotExistingPit_shouldThrowException() {
        //arrange
        var gameRepository = mock(GameRepository.class);
        var mapper = mock(GamePresentationMapper.class);
        var gameFlow = mock(GameFlow.class);
        var service = new GameService(gameRepository, mapper, gameFlow);

        var game = Game.create();
        UUID id = UUID.randomUUID();
        when(gameRepository.findById(id)).thenReturn(Optional.of(game));
        var presentation = GamePresentation.builder().build();
        when(mapper.getGamePresentation(game)).thenReturn(presentation);

        //act && assert
        assertThrows(KalahException.class, () -> service.makeMove(id, 111));
    }

    @Test
    void getGame_withExistingGame_shouldReturnCorrectPresentation() {
        //arrange
        var gameRepository = mock(GameRepository.class);
        var mapper = mock(GamePresentationMapper.class);
        var gameFlow = mock(GameFlow.class);
        var service = new GameService(gameRepository, mapper, gameFlow);

        var game = Game.create();
        UUID id = UUID.randomUUID();
        when(gameRepository.findById(id)).thenReturn(Optional.of(game));
        var presentation = GamePresentation.builder().build();
        when(mapper.getGamePresentation(game)).thenReturn(presentation);

        //act
        var newGamePresentation = service.getGame(id);

        //assert
        assertSame(presentation, newGamePresentation);
    }

    @Test
    void getGame_withNotExistingGame_shouldThrowException() {
        //arrange
        var gameRepository = mock(GameRepository.class);
        var mapper = mock(GamePresentationMapper.class);
        var gameFlow = mock(GameFlow.class);
        var service = new GameService(gameRepository, mapper, gameFlow);

        UUID id = UUID.randomUUID();
        when(gameRepository.findById(id)).thenReturn(Optional.empty());

        //act && assert
        assertThrows(KalahException.class, () -> service.getGame(id));
    }
}