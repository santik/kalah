package com.backbase.kalah.service;

import com.backbase.kalah.exception.KalahException;
import com.backbase.kalah.model.Game;
import com.backbase.kalah.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import com.backbase.kalah.presentation.GamePresentation;
import com.backbase.kalah.presentation.NewGamePresentation;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final GamePresentationMapper mapper;
    private final GameFlow gameFlow;

    public NewGamePresentation initGame() {
        final var game = Game.createGame();
        Game savedGame = gameRepository.save(game);
        return mapper.getNewGamePresentation(savedGame);
    }

    public GamePresentation makeMove(UUID gameId, Integer pitIndex) throws KalahException {
        var game = findGame(gameId);
        synchronized (game) {
            gameFlow.makeMove(game, pitIndex);
            return mapper.getGamePresentation(game);
        }
    }

    public GamePresentation getGame(UUID gameId) throws KalahException {
        return mapper.getGamePresentation(findGame(gameId));
    }

    private Game findGame(UUID gameId) throws KalahException {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        if (optionalGame.isEmpty()) {
            throw new KalahException("Game not found.");
        }

        return optionalGame.get();
    }
}
