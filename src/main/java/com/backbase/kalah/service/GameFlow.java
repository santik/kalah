package com.backbase.kalah.service;

import com.backbase.kalah.exception.KalahException;
import com.backbase.kalah.model.Game;
import com.backbase.kalah.model.Pit;
import com.backbase.kalah.service.rule.ValidMoveRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameFlow {

    private final List<GameFlowRule> rules;
    private final ValidMoveRule validMoveRule;

    public void makeMove(Game game, Integer pitIndex) throws KalahException {
        Stream<Pit> pitStream = Stream.of(validMoveRule.apply(game, pitIndex));
        for (GameFlowRule rule: rules) {
            log.info("Applying {}", rule.getClass());
            pitStream = pitStream.map(pit -> rule.apply(game, pit));
        }

        pitStream.findAny();
    }
}

