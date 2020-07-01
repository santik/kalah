package com.backbase.kalah.service.rule;

import com.backbase.kalah.exception.KalahException;
import com.backbase.kalah.model.Game;
import com.backbase.kalah.model.Pit;
import org.springframework.stereotype.Component;

@Component
public class ValidMoveRule {

    public Pit apply(Game game, Integer pitIndex) throws KalahException {
        if (game.isFinished()) {
            throw new KalahException("Game is finished");
        }

        if (pitIndex < 1 || pitIndex >= game.getBoard().size()) {
            throw new KalahException("Pit not found");
        }
        var pit = game.getBoard().get(pitIndex);

        if (game.isKalah(pit)) {
            throw new KalahException("Can not use kalah pit!");
        }

        if (!game.isCurrentPlayerPit(pit)) {
            throw new KalahException("Not your turn!");
        }

        if (!pit.hasSeeds()) {
            throw new KalahException("Empty pit!");
        }

        return pit;
    }
}
