package com.backbase.kalah.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void createGame_shouldReturn_CorrectGame() {
        //act
        Game game = Game.create();

        //assert
        assertNotNull(game);
        assertNotNull(game.getStatus());
        assertNotNull(game.getId());
        assertNotNull(game.getBoard());
        assertNull(game.getWinner());

        assertEquals(15, game.getBoard().size());
        game.getBoard().forEach(pit -> {
            if (pit != null && (pit.getIndex() == 7 || pit.getIndex() == 14)) {
                assertEquals(0, pit.getSeedsCount());
            }
            if (pit != null && pit.getIndex() != 7 && pit.getIndex() != 14) {
                assertEquals(6, pit.getSeedsCount());
            }
        });
    }

    @Test
    void isFinished_gameNotFinished_shouldReturnFalse() {
        //arrange
        Game game = Game.create();

        //act && assert
        assertFalse(game.isFinished());
    }

    @Test
    void isFinished_gameFinished_shouldReturnTrue() {
        //arrange
        Game game = Game.create();

        //act
        game.finish();

        // assert
        assertTrue(game.isFinished());
    }

    @Test
    void flipTurn() {
        //arrange
        Game game = Game.create();

        //act && assert
        assertEquals(Status.PLAYER1_TURN, game.getStatus());
        game.flipTurn();
        assertEquals(Status.PLAYER2_TURN, game.getStatus());
    }

    @Test
    void getNextTo_withMiddleIndex_shouldReturnNext() {
        //arrange
        Game game = Game.create();
        int pitIndex = 1;
        var initialPit = game.getPitByIndex(pitIndex).get();

        //act
        Pit nextTo = game.getNextTo(initialPit);

        //assert
        assertEquals(pitIndex, initialPit.getIndex());
        assertEquals(pitIndex + 1, nextTo.getIndex());
    }

    @Test
    void getNextTo_withEndIndex_shouldReturnNext() {
        //arrange
        Game game = Game.create();
        int pitIndex = 14;
        var initialPit = game.getPitByIndex(pitIndex).get();

        //act
        Pit nextTo = game.getNextTo(initialPit);

        //assert
        assertEquals(pitIndex, initialPit.getIndex());
        assertEquals(1, nextTo.getIndex());
    }

    @Test
    void getPitByIndex_withExistingIndex_shouldReturnPit() {
        //arrange
        Game game = Game.create();
        Integer index = 5;

        //act
        var optionalPit = game.getPitByIndex(index);

        //assert
        assertTrue(optionalPit.isPresent());
    }

    @Test
    void getPitByIndex_withZeroIndex_shouldReturnPit() {
        //arrange
        Game game = Game.create();
        Integer index = 0;

        //act
        var optionalPit = game.getPitByIndex(index);

        //assert
        assertTrue(optionalPit.isEmpty());
    }

    @Test
    void getPitByIndex_withNotExistingIndex_shouldReturnPit() {
        //arrange
        Game game = Game.create();
        Integer index = 15;

        //act
        var optionalPit = game.getPitByIndex(index);

        //assert
        assertTrue(optionalPit.isEmpty());
    }

    @Test
    void getOppositeTo() {
        //arrange
        Game game = Game.create();
        Integer index = 4;
        var pit = game.getPitByIndex(index).get();

        //act
        var oppositeTo = game.getOppositeTo(pit);

        //assert
        assertEquals(10, oppositeTo.getIndex());
    }

    @Test
    void isOpponentKalah_shouldReturnCorrectAnswer() {
        //arrange
        Game game = Game.create();

        //act && assert
        assertEquals(Status.PLAYER1_TURN, game.getStatus());
        assertTrue(game.isOpponentKalah(game.getPitByIndex(14).get()));
        assertFalse(game.isOpponentKalah(game.getPitByIndex(7).get()));
    }

    @Test
    void isKalah() {
        //arrange
        Game game = Game.create();

        //act && assert
        assertTrue(game.isKalah(game.getPitByIndex(7).get()));
        assertTrue(game.isKalah(game.getPitByIndex(14).get()));
    }

    @Test
    void isPlayer1EmptyPits_withNotEmptyPits_shouldReturnTrue() {
        //arrange
        Game game = Game.create();

        //act && assert
        assertFalse(game.isPlayer1EmptyPits());
    }

    @Test
    void isPlayer1EmptyPits_withEmptyPits_shouldReturnTrue() {
        //arrange
        Game game = Game.create();

        //act
        game.getBoard().forEach(pit -> {
            if (pit != null) {
                pit.setSeedsCount(0);
            }
        });


        //assert
        assertTrue(game.isPlayer1EmptyPits());
    }

    @Test
    void isPlayer2EmptyPits_withNotEmptyPits_shouldReturnTrue() {
        //arrange
        Game game = Game.create();

        //act && assert
        assertFalse(game.isPlayer2EmptyPits());
    }

    @Test
    void isPlayer2EmptyPits_withEmptyPits_shouldReturnTrue() {
        //arrange
        Game game = Game.create();

        //act
        game.getBoard().forEach(pit -> {
            if (pit != null) {
                pit.setSeedsCount(0);
            }
        });


        //assert
        assertTrue(game.isPlayer2EmptyPits());
    }

    @Test
    void countPlayerSeeds() {
        //arrange
        Game game = Game.create();

        //act && assert
        assertEquals(36, game.countPlayer1Seeds());
        assertEquals(36, game.countPlayer2Seeds());
    }

    @Test
    void isCurrentPlayerPit() {
        //arrange
        Game game = Game.create();

        //act && assert
        assertEquals(Status.PLAYER1_TURN, game.getStatus());
        assertTrue(game.isCurrentPlayerPit(game.getPitByIndex(1).get()));
    }

    @Test
    void getCurrentPlayerKalah() {
        //arrange
        Game game = Game.create();

        //act && assert
        assertEquals(Status.PLAYER1_TURN, game.getStatus());
        assertTrue(game.isCurrentPlayerPit(game.getPitByIndex(7).get()));
    }

    @Test
    void setWinner() {
        //arrange
        Game game = Game.create();

        //act && assert
        assertNull(game.getWinner());
        game.setPlayer1Winner();
        assertEquals(1, game.getWinner());
        game.setPlayer2Winner();
        assertEquals(2, game.getWinner());
    }
}