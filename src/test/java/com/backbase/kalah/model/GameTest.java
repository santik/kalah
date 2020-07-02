package com.backbase.kalah.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Integer initSeedsPerPit = (Integer) ReflectionTestUtils.getField(Game.class, "INIT_SEEDS_PER_PIT");
    private Game game;

    @BeforeEach
    public void setUp() {
        game = Game.create();
    }


    @Test
    void createGame_shouldReturn_CorrectGame() {
        //assert
        assertNotNull(game);
        assertNotNull(game.getStatus());
        assertNotNull(game.getId());
        assertNotNull(game.getBoard());
        assertNull(game.getWinner());

        assertEquals(15, game.getBoard().size());
        game.getBoard().forEach(pit -> {
            if (pit != null && (pit.getId() == 7 || pit.getId() == 14)) {
                assertEquals(0, pit.getSeedsCount());
            }
            if (pit != null && pit.getId() != 7 && pit.getId() != 14) {
                assertEquals(initSeedsPerPit, pit.getSeedsCount());
            }
        });
    }

    @Test
    void isFinished_gameNotFinished_shouldReturnFalse() {
        //act && assert
        assertFalse(game.isFinished());
    }

    @Test
    void isFinished_gameFinished_shouldReturnTrue() {
        //act
        game.finish();

        // assert
        assertTrue(game.isFinished());
    }

    @Test
    void flipTurn() {
        //act && assert
        assertEquals(Status.PLAYER1_TURN, game.getStatus());
        game.flipTurn();
        assertEquals(Status.PLAYER2_TURN, game.getStatus());
    }

    @Test
    void getNextTo_withMiddleId_shouldReturnNext() {
        //arrange
        int pitId = 1;
        var initialPit = game.getPitById(pitId).get();

        //act
        Pit nextTo = game.getNextTo(initialPit);

        //assert
        assertEquals(pitId, initialPit.getId());
        assertEquals(pitId + 1, nextTo.getId());
    }

    @Test
    void getNextTo_withEndId_shouldReturnNext() {
        //arrange
        int pitId = 14;
        var initialPit = game.getPitById(pitId).get();

        //act
        Pit nextTo = game.getNextTo(initialPit);

        //assert
        assertEquals(pitId, initialPit.getId());
        assertEquals(1, nextTo.getId());
    }

    @Test
    void getPitById_withExistingId_shouldReturnPit() {
        //arrange
        Integer id = 5;

        //act
        var optionalPit = game.getPitById(id);

        //assert
        assertTrue(optionalPit.isPresent());
    }

    @Test
    void getPitById_withZeroId_shouldReturnPit() {
        //arrange
        Integer id = 0;

        //act
        var optionalPit = game.getPitById(id);

        //assert
        assertTrue(optionalPit.isEmpty());
    }

    @Test
    void getPitById_withNotExistingId_shouldReturnPit() {
        //arrange
        Integer id = 15;

        //act
        var optionalPit = game.getPitById(id);

        //assert
        assertTrue(optionalPit.isEmpty());
    }

    @Test
    void getOppositeTo() {
        //arrange
        Integer id = 4;
        var pit = game.getPitById(id).get();

        //act
        var oppositeTo = game.getOppositeTo(pit);

        //assert
        assertEquals(10, oppositeTo.getId());
    }

    @Test
    void isOpponentKalah_shouldReturnCorrectAnswer() {
        //act && assert
        assertEquals(Status.PLAYER1_TURN, game.getStatus());
        assertTrue(game.isOpponentKalah(game.getPitById(14).get()));
        assertFalse(game.isOpponentKalah(game.getPitById(7).get()));
    }

    @Test
    void isKalah() {
        //act && assert
        assertTrue(game.isKalah(game.getPitById(7).get()));
        assertTrue(game.isKalah(game.getPitById(14).get()));
    }

    @Test
    void isPlayer1EmptyPits_withNotEmptyPits_shouldReturnTrue() {
        //act && assert
        assertFalse(game.isPlayer1EmptyPits());
    }

    @Test
    void isPlayer1EmptyPits_withEmptyPits_shouldReturnTrue() {
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
        //act && assert
        assertFalse(game.isPlayer2EmptyPits());
    }

    @Test
    void isPlayer2EmptyPits_withEmptyPits_shouldReturnTrue() {
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
        //act && assert
        assertEquals(initSeedsPerPit * 6, game.countPlayer1Seeds());
        assertEquals(initSeedsPerPit * 6, game.countPlayer2Seeds());
    }

    @Test
    void isCurrentPlayerPit() {
        //act && assert
        assertEquals(Status.PLAYER1_TURN, game.getStatus());
        assertTrue(game.isCurrentPlayerPit(game.getPitById(1).get()));
    }

    @Test
    void getCurrentPlayerKalah() {
        //act && assert
        assertEquals(Status.PLAYER1_TURN, game.getStatus());
        assertTrue(game.isCurrentPlayerPit(game.getPitById(7).get()));
    }

    @Test
    void setWinner() {
        //act && assert
        assertNull(game.getWinner());
        game.setPlayer1Winner();
        assertEquals(1, game.getWinner());
        game.setPlayer2Winner();
        assertEquals(2, game.getWinner());
    }
}
