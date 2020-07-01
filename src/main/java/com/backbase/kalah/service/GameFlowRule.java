package com.backbase.kalah.service;

import com.backbase.kalah.model.Game;
import com.backbase.kalah.model.Pit;

public interface GameFlowRule {
   Pit apply(Game game, Pit pit);
}
