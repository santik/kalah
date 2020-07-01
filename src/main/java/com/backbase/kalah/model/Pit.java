package com.backbase.kalah.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class Pit {
    @Setter
    private Integer seedsCount;
    private Integer playerIndex; //instead of checking inside the range of indexes
    private Integer index; //instead of doing indexOf calls

    public Boolean hasSeeds() {
        return seedsCount != 0;
    }
}
