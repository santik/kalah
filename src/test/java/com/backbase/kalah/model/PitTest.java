package com.backbase.kalah.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PitTest {

    @Test
    void hasSeeds() {
        //arrange
        var pit = new Pit(1,1,1);

        //act & assert
        assertTrue(pit.hasSeeds());
        pit.setSeedsCount(0);
        assertFalse(pit.hasSeeds());
    }
}
