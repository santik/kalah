package com.backbase.kalah.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Game {

    private static final Integer PLAYER1_INDEX = 1;
    private static final Integer PLAYER2_INDEX = 2;
    private static final Integer SEEDS_PER_PIT = 4;
    private static final Integer PLAYER1_KALAH_INDEX = 7;
    private static final Integer PLAYER2_KALAH_INDEX = 14;

    private UUID id;
    private Status status = Status.PLAYER1_TURN;
    private Integer winner;
    private List<Pit> board;

    public static Game createGame() {
        var game = new Game();
        game.setUpBoard();
        game.id = UUID.randomUUID();
        return game;
    }

    private void setUpBoard() {
        board = new ArrayList<>();
        board.add(null); //just to fill 0 index

        IntStream.range(1, PLAYER1_KALAH_INDEX)
                .forEach(i -> board.add(new Pit(SEEDS_PER_PIT, PLAYER1_INDEX, i)));
        board.add(new Pit(0, PLAYER1_INDEX, PLAYER1_KALAH_INDEX));

        IntStream.range(PLAYER1_KALAH_INDEX + 1, PLAYER2_KALAH_INDEX)
                .forEach(i -> board.add(new Pit(SEEDS_PER_PIT, PLAYER2_INDEX, i)));
        board.add(new Pit(0, PLAYER2_INDEX, PLAYER2_KALAH_INDEX));
    }

    public boolean isFinished() {
        return status.equals(Status.FINISHED);
    }

    public void flipTurn() {
        status = status.equals(Status.PLAYER1_TURN) ? Status.PLAYER2_TURN : Status.PLAYER1_TURN;
    }

    public void finish() {
        status = Status.FINISHED;
    }

    public Pit getNextTo(Pit pit) {
        var index = pit.getIndex();
        if (index == board.size() - 1) {
            index = 0;
        }
        return board.get(index + 1);
    }

    public Pit getOppositeTo(Pit pit) {
        var index = board.size() - pit.getIndex() - 1;
        return board.get(index);
    }

    public boolean isOpponentKalah(Pit pit) {
        return (isPlayer2Kalah(pit) && getStatus().equals(Status.PLAYER1_TURN)) ||
                (isPlayer1Kalah(pit) && getStatus().equals(Status.PLAYER2_TURN));
    }

    public boolean isKalah(Pit pit) {
        return isPlayer1Kalah(pit) || isPlayer2Kalah(pit);
    }

    public boolean isPlayer1EmptyPits() {
        var hasSeeds = IntStream.range(1, PLAYER1_KALAH_INDEX).anyMatch(
                i -> board.get(i).getSeedsCount() != 0
        );
        return !hasSeeds;
    }

    public boolean isPlayer2EmptyPits() {
        var hasSeeds = IntStream.range(PLAYER1_KALAH_INDEX + 1, PLAYER2_KALAH_INDEX).anyMatch(
                i -> board.get(i).getSeedsCount() != 0
        );
        return !hasSeeds;
    }

    public Integer countPlayer1Seeds() {
        return IntStream.range(1, PLAYER1_KALAH_INDEX + 1)
                .map(i -> board.get(i).getSeedsCount()).sum();
    }

    public Integer countPlayer2Seeds() {
        return IntStream.range(PLAYER1_KALAH_INDEX + 1, PLAYER2_KALAH_INDEX + 1)
                .map(i -> board.get(i).getSeedsCount()).sum();
    }

    public Boolean isCurrentPlayerPit(Pit pit) {
        return (pit.getPlayerIndex().equals(PLAYER1_INDEX) && getStatus().equals(Status.PLAYER1_TURN)) ||
                (pit.getPlayerIndex().equals(PLAYER2_INDEX) && getStatus().equals(Status.PLAYER2_TURN));
    }

    public Pit getCurrentPlayerKalah() {
        if (getStatus().equals(Status.PLAYER1_TURN)) {
            return board.get(PLAYER1_KALAH_INDEX);
        }
        return board.get(PLAYER2_KALAH_INDEX);
    }

    private boolean isPlayer1Kalah(Pit pit) {
        return pit.getIndex().equals(PLAYER1_KALAH_INDEX);
    }

    private boolean isPlayer2Kalah(Pit pit) {
        return pit.getIndex().equals(PLAYER2_KALAH_INDEX);
    }

    public void setPlayer1Winner() {
        winner = PLAYER1_INDEX;
    }

    public void setPlayer2Winner() {
        winner = PLAYER2_INDEX;
    }
}
