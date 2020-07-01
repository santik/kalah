package com.backbase.kalah.repository;

import com.backbase.kalah.model.Game;

import java.util.Optional;
import java.util.UUID;

public interface GameRepository {
    Game save(Game game);
    Optional<Game> findById(UUID gameId);
}
