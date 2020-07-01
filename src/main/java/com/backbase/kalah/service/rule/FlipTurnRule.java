package com.backbase.kalah.service.rule;

import com.backbase.kalah.model.Game;
import com.backbase.kalah.model.Pit;
import com.backbase.kalah.service.GameFlowRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Order(value = 4)
class FlipTurnRule implements GameFlowRule {

    @Override
    public Pit apply(Game game, Pit pit) {
        if (!game.isCurrentPlayerPit(pit)) {
            log.info("Flipping turn");
            game.flipTurn();
        }

        return pit;
    }
}
