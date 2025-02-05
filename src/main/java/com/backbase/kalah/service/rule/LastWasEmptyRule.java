package com.backbase.kalah.service.rule;

import com.backbase.kalah.model.Game;
import com.backbase.kalah.model.Pit;
import com.backbase.kalah.service.GameFlowRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Order(value = 3)
class LastWasEmptyRule implements GameFlowRule {

    @Override
    public Pit apply(Game game, Pit pit) {
        if (isPitWasEmptyAndOfCurrentUser(game, pit)) {
            log.info("Applying last was empty rule");
            var oppositePit = game.getOppositeTo(pit);
            if (oppositePit.getSeedsCount() == 0) {
                return pit;
            }

            var kalah = game.getCurrentPlayerKalah();
            var kalahSeeds = kalah.getSeedsCount() + oppositePit.getSeedsCount() + pit.getSeedsCount();
            kalah.setSeedsCount(kalahSeeds);
            oppositePit.setSeedsCount(0);
            pit.setSeedsCount(0);
        }

        return pit;
    }

    private boolean isPitWasEmptyAndOfCurrentUser(Game game, Pit pit) {
        return !game.isKalah(pit) && game.isCurrentPlayerPit(pit) && pit.getSeedsCount().equals(1);
    }
}
