package com.backbase.kalah.service.rule;

import com.backbase.kalah.model.Game;
import com.backbase.kalah.model.Pit;
import com.backbase.kalah.service.GameFlowRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Order(value = 5)
class EndGameRule implements GameFlowRule {

    @Override
    public Pit apply(Game game, Pit pit) {
        if (game.isPlayer1EmptyPits() || game.isPlayer2EmptyPits()) {
            log.info("Game ended");
            var player1TotalSeeds = game.countPlayer1Seeds();
            var player2TotalSeeds = game.countPlayer2Seeds();
            if (player1TotalSeeds > player2TotalSeeds) {
                game.setPlayer1Winner();
            } else if (player1TotalSeeds < player2TotalSeeds) {
                game.setPlayer2Winner();
            }

            game.finish();
        }

        return pit;
    }
}
