package com.myssteriion.blindtest.model.common;

import com.myssteriion.blindtest.model.common.roundcontent.AbstractRoundContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ChoiceContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ClassicContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ThiefContent;

import java.util.Arrays;
import java.util.List;

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

    public AbstractRoundContent createRoundContent(List<String> playersNames) {

        int nbPlayer = playersNames.size();

        switch (this) {
            case CLASSIC:   return new ClassicContent(12, 100);
            case CHOICE:    return new ChoiceContent(4 * nbPlayer, 100, 50, -50);
            case THIEF:     return new ThiefContent(12, 100, 100);
            default:        throw new IllegalArgumentException("Il manque un case ('" + this + "').");
        }
    }

    public static Round getFirst() {
        return Arrays.stream(Round.values()).filter(round -> round.roundNumber == 0).findFirst().get();
    }

}
