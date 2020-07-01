package com.backbase.kalah.controller;

import com.backbase.kalah.exception.KalahException;
import com.backbase.kalah.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.backbase.kalah.presentation.NewGamePresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    @PostMapping
    public ResponseEntity<NewGamePresentation> createGame(){
        var newGamePresentation = gameService.initGame();
        return ResponseEntity.status(HttpStatus.CREATED).body(newGamePresentation);
    }

    @PutMapping("/{gameId}/pits/{pitIndex}")
    public ResponseEntity move(@PathVariable UUID gameId, @PathVariable Integer pitIndex) {
        try {
            return ResponseEntity.ok().body(gameService.makeMove(gameId, pitIndex));
        } catch (KalahException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{gameId}")
    public ResponseEntity getGame(@PathVariable UUID gameId) {
        try {
            return ResponseEntity.ok().body(gameService.getGame(gameId));
        } catch (KalahException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
