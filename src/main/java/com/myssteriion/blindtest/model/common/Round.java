package com.myssteriion.blindtest.model.common;

import com.myssteriion.blindtest.model.common.roundcontent.AbstractRoundContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ChoiceContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ClassicContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ThiefContent;
import com.myssteriion.blindtest.model.dto.game.GameDTO;
import com.myssteriion.blindtest.tools.Tool;

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

    public AbstractRoundContent createRoundContent(GameDTO gameDto) {

        Tool.verifyValue("gameDto", gameDto);

        int nbPlayer = gameDto.getPlayers().size();
        double durationRatio = gameDto.getDuration().getRatio();

        switch (this) {
            case CLASSIC:   return new ClassicContent( (int) (12 * durationRatio), 100 );
            case CHOICE:    return new ChoiceContent( (int) ((4 * nbPlayer) * durationRatio), 100, 50, -50);
            case THIEF:     return new ThiefContent( (int) (12 * durationRatio), 100, -100);
            default:        throw new IllegalArgumentException("Il manque un case ('" + this + "').");
        }
    }

    public static Round getFirst() {
        return Arrays.stream(Round.values()).filter(round -> round.roundNumber == 0).findFirst().get();
    }

}
