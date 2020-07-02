package com.backbase.kalah.service;

import com.backbase.kalah.exception.KalahException;
import com.backbase.kalah.model.Game;
import com.backbase.kalah.model.Pit;
import com.backbase.kalah.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import com.backbase.kalah.presentation.GamePresentation;
import com.backbase.kalah.presentation.NewGamePresentation;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final GamePresentationMapper mapper;
    private final GameFlow gameFlow;

    public NewGamePresentation initGame() {
        var game = Game.create();
        var savedGame = gameRepository.save(game);
        return mapper.getNewGamePresentation(savedGame);
    }

    public GamePresentation makeMove(UUID gameId, Integer pitId) {
        var game = findGame(gameId);
        synchronized (game) {
            var pit = getPit(pitId, game);
            gameFlow.makeMove(game, pit);
            return mapper.getGamePresentation(game);
        }
    }

    public GamePresentation getGame(UUID gameId) {
        return mapper.getGamePresentation(findGame(gameId));
    }

    private Pit getPit(Integer pitId, Game game) {
        var optionalPit = game.getPitById(pitId);
        if (optionalPit.isEmpty()) {
            throw new KalahException("Pit not found");
        }

        return optionalPit.get();
    }

    private Game findGame(UUID gameId) {
        var optionalGame = gameRepository.findById(gameId);
        if (optionalGame.isEmpty()) {
            throw new KalahException("Game not found");
        }

        return optionalGame.get();
    }
}
