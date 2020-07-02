package com.backbase.kalah.service;

import com.backbase.kalah.exception.KalahException;
import com.backbase.kalah.model.Game;
import com.backbase.kalah.model.Pit;
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
        var savedGame = gameRepository.save(game);
        return mapper.getNewGamePresentation(savedGame);
    }

    public GamePresentation makeMove(UUID gameId, Integer pitIndex) {
        var game = findGame(gameId);
        synchronized (game) {
            var pit = getPit(pitIndex, game);
            gameFlow.makeMove(game, pit);
            return mapper.getGamePresentation(game);
        }
    }

    public GamePresentation getGame(UUID gameId) {
        return mapper.getGamePresentation(findGame(gameId));
    }

    private Pit getPit(Integer pitIndex, Game game) {
        var optionalPit = game.getPitByIndex(pitIndex);
        if (optionalPit.isEmpty()) {
            throw new KalahException("Pit not found");
        }

        return optionalPit.get();
    }

    private Game findGame(UUID gameId) {
        var optionalGame = gameRepository.findById(gameId);
        if (optionalGame.isEmpty()) {
            throw new KalahException("Game not found.");
        }

        return optionalGame.get();
    }
}
