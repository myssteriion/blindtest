package com.myssteriion.blindtest.model.common;

import com.myssteriion.blindtest.model.common.roundcontent.AbstractRoundContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ClassicContent;

import java.util.Arrays;

public enum Round {

    CLASSIC(0),
    CHOICE(1),
    THIEF(2 );



    private int roundNumber;



    Round(int roundNumber) {
        this.roundNumber = roundNumber;
    }



    public int getRoundNumber() {
        return roundNumber;
    }


    public Round nextRound() {
        return Arrays.stream(Round.values()).filter(round -> round.roundNumber == this.roundNumber+1).findFirst().orElse(null);
    }

    public AbstractRoundContent createRoundContent() {
        switch (this) {
            case CLASSIC:   return new ClassicContent(8, 100);
            default:        throw new IllegalArgumentException("Il manque un case ('" + this + "').");
        }
    }

    public static Round getFirst() {
        return Arrays.stream(Round.values()).filter(round -> round.roundNumber == 0).findFirst().get();
    }

}
