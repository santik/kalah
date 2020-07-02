package com.backbase.kalah.service.rule;

import com.backbase.kalah.exception.KalahException;
import com.backbase.kalah.model.Game;
import com.backbase.kalah.model.Pit;
import com.backbase.kalah.service.GameFlowRule;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
class ValidMoveRule implements GameFlowRule {

    @Override
    public Pit apply(Game game, Pit pit) {
        if (game.isFinished()) {
            throw new KalahException("Game is finished");
        }

        if (game.isKalah(pit)) {
            throw new KalahException("Can not use kalah pit");
        }

        if (!game.isCurrentPlayerPit(pit)) {
            throw new KalahException("Not your turn");
        }

        if (!pit.hasSeeds()) {
            throw new KalahException("Empty pit");
        }

        return pit;
    }
}
