package com.backbase.kalah.repository;

import com.backbase.kalah.model.Game;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
public class GameInMemoryRepository implements GameRepository {

    private static final Map<UUID, Game> games = new ConcurrentHashMap<>();

    @Override
    public Game save(Game game) {
        games.put(game.getId(), game);
        return game;
    }

    @Override
    public Optional<Game> findById(UUID gameId) {
        return Optional.ofNullable(games.get(gameId));
    }
}
