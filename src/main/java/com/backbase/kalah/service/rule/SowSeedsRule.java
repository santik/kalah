package com.backbase.kalah.service.rule;

import com.backbase.kalah.model.Game;
import com.backbase.kalah.model.Pit;
import com.backbase.kalah.service.GameFlowRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Order(value=1)
class SowSeedsRule implements GameFlowRule {

    @Override
    public Pit apply(Game game, Pit pit) {
        Integer seedsCount = pit.getSeedsCount();
        pit.setSeedsCount(0);

        while (seedsCount > 0) {
            pit = game.getNextTo(pit);
            if (!game.isOpponentKalah(pit)) {
                pit.setSeedsCount(pit.getSeedsCount() + 1);
                seedsCount = seedsCount - 1;
            }
        }

        return pit;
    }
}
