package com.backbase.kalah.service;

import com.backbase.kalah.model.Game;
import com.backbase.kalah.model.Pit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
class GameFlow {

    private final List<GameFlowRule> rules;

    public void makeMove(Game game, Pit pit) {
        var pitStream = Stream.of(pit);
        for (var rule: rules) {
            pitStream = pitStream.map(currentPit -> rule.apply(game, currentPit));
        }

        pitStream.findAny();
    }
}

