package com.backbase.kalah.service;

import com.backbase.kalah.controller.GameController;
import com.backbase.kalah.model.Game;
import com.backbase.kalah.presentation.GamePresentation;
import com.backbase.kalah.presentation.NewGamePresentation;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class GamePresentationMapper {

    public NewGamePresentation getNewGamePresentation(Game game) {
        return NewGamePresentation.builder()
                .id(game.getId().toString())
                .uri(getGameLink(game).getHref())
                .build();
    }

    public GamePresentation getGamePresentation(Game game) {
        return GamePresentation.builder()
                .id(game.getId().toString())
                .url(getGameLink(game).getHref())
                .status(getStatus(game))
                .build();
    }

    private Map<String, String> getStatus(Game game) {
        var status = new LinkedHashMap<String, String>(); //to ensure order
        IntStream.range(1, game.getBoard().size()).forEach(
                i -> status.put(String.valueOf(i), game.getBoard().get(i).getSeedsCount().toString())
        );
        return status;
    }

    private Link getGameLink(Game game) {
        return linkTo(methodOn(GameController.class).getGame(game.getId())).withSelfRel();
    }
}
