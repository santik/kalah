package com.backbase.kalah.service;

import com.backbase.kalah.model.Game;
import com.backbase.kalah.model.Pit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
class GameFlow {

    private final List<GameFlowRule> rules;

    public void makeMove(Game game, Pit pit) {
        for (var rule: rules) {
            pit = rule.apply(game, pit);
        }
    }
}
