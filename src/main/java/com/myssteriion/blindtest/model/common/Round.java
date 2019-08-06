package com.myssteriion.blindtest.model.common;

import java.util.Arrays;

public enum Round {

    CLASSIC(0, 20, 100, 0);



    private int roundNumber;

    private int nbMusics;

    private int nbPointWon;

    private int nbPointLost;



    Round(int roundNumber, int nbMusics, int nbPointWon, int nbPointLost) {
        this.roundNumber = roundNumber;
        this.nbMusics = nbMusics;
        this.nbPointWon = nbPointWon;
        this.nbPointLost = nbPointLost;
    }



    public int getRoundNumber() {
        return roundNumber;
    }

    public int getNbMusics() {
        return nbMusics;
    }

    public int getNbPointWon() {
        return nbPointWon;
    }

    public int getNbPointLost() {
        return nbPointLost;
    }


    public Round next() {
        return Arrays.stream(Round.values()).filter(round -> round.roundNumber == this.roundNumber+1).findFirst().orElse(null);
    }

    public static Round getFirst() {
        return Arrays.stream(Round.values()).filter(round -> round.roundNumber == 0).findFirst().get();
    }

}
