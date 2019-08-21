package com.myssteriion.blindtest.model.common;

import com.myssteriion.blindtest.model.common.roundcontent.AbstractRoundContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ChoiceContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ClassicContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ThiefContent;
import com.myssteriion.blindtest.model.dto.game.GameDTO;
import com.myssteriion.blindtest.properties.RoundContentProperties;
import com.myssteriion.blindtest.tools.BeanFactory;
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

        RoundContentProperties prop = BeanFactory.getBean(RoundContentProperties.class);

        switch (this) {
            case CLASSIC:
                int nbMusics = prop.getClassicNbMusics();
                int nbPointWon = prop.getClassicNbPointWon();
                return new ClassicContent((int) (nbMusics * durationRatio), nbPointWon);

            case CHOICE:
                nbMusics = prop.getChoiceNbMusics();
                nbPointWon = prop.getChoiceNbPointWon();
                int nbPointBonusWon = prop.getChoiceNbPointBonusWon();
                int nbPointMalusLoose = prop.getChoicenNPointMalusLoose();
                return new ChoiceContent((int) ((nbMusics * nbPlayer) * durationRatio), nbPointWon, nbPointBonusWon, nbPointMalusLoose);


            case THIEF:
                nbMusics = prop.getThiefNbMusics();
                nbPointWon = prop.getThiefNbPointWon();
                int nbPointLoose = prop.getThiefNbPointLoose();
                return new ThiefContent( (int) (nbMusics * durationRatio), nbPointWon, nbPointLoose);


            default:        throw new IllegalArgumentException("Il manque un case ('" + this + "').");
        }
    }

    public static Round getFirst() {
        return Arrays.stream(Round.values()).filter(round -> round.roundNumber == 0).findFirst().get();
    }

}
