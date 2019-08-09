package com.myssteriion.blindtest.model.common;

import java.util.Arrays;

public enum Round {

    CLASSIC(0, 8, 100, 0, 0, 0),
    CHOICE(1, 8, 100, 50, 0, -50),
    THIEF(2, 8, 100, 0, 100, 0);



    private int roundNumber;

    private int nbMusics;

    private int nbPointWon;

    private int nbPointBonusWon;

    private int nbPointLost;

    private int nbPointMalusLost;



    Round(int roundNumber, int nbMusics, int nbPointWon, int nbPointBonusWon, int nbPointLost, int nbPointMalusLost) {
        this.roundNumber = roundNumber;
        this.nbMusics = nbMusics;
        this.nbPointWon = nbPointWon;
        this.nbPointBonusWon = nbPointBonusWon;
        this.nbPointLost = nbPointLost;
        this.nbPointMalusLost = nbPointMalusLost;
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

    public int getNbPointBonusWon() {
        return nbPointBonusWon;
    }

    public int getNbPointLost() {
        return nbPointLost;
    }

    public int getNbPointMalusLost() {
        return nbPointMalusLost;
    }


    public Round next() {
        return Arrays.stream(Round.values()).filter(round -> round.roundNumber == this.roundNumber+1).findFirst().orElse(null);
    }

    public static Round getFirst() {
        return Arrays.stream(Round.values()).filter(round -> round.roundNumber == 0).findFirst().get();
    }

}
