package com.myssteriion.blindtest.model.common;

import com.myssteriion.blindtest.model.common.roundcontent.AbstractRoundContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ChoiceContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ClassicContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ThiefContent;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.properties.RoundContentProperties;
import com.myssteriion.blindtest.tools.BeanFactory;
import com.myssteriion.blindtest.tools.Tool;

import java.util.Arrays;

/**
 * The Round enum.
 */
public enum Round {

    CLASSIC(0),
    CHOICE(1),
    THIEF(2 );



    private int roundNumber;

    Round(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    /**
     * Gets round number.
     *
     * @return the round number
     */
    public int getRoundNumber() {
        return roundNumber;
    }

    /**
     * Gets next round.
     *
     * @return the next round, NULL if there isn't next
     */
    public Round nextRound() {
        return Arrays.stream(Round.values()).filter(round -> round.roundNumber == this.roundNumber+1).findFirst().orElse(null);
    }

    /**
     * Create round content from game.
     *
     * @param game the game
     * @return the round content
     */
    public AbstractRoundContent createRoundContent(Game game) {

        Tool.verifyValue("game", game);

        int nbPlayer = game.getPlayers().size();
        double durationRatio = game.getDuration().getRatio();

        RoundContentProperties prop = BeanFactory.getBean(RoundContentProperties.class);

        switch (this) {
            case CLASSIC:
                int nbMusics = prop.getClassicNbMusics();
                int nbPointWon = prop.getClassicNbPointWon();
                return new ClassicContent((int) (nbMusics * durationRatio), nbPointWon);

            case CHOICE:
                nbMusics = prop.getChoiceNbMusics();
                nbPointWon = prop.getChoiceNbPointWon();
                int nbPointBonusWon = prop.getChoiceNbPointBonus();
                int nbPointMalusLoose = prop.getChoiceNbPointMalus();

                game.getPlayers().get(0).setTurnToChoose(true);

                return new ChoiceContent((int) ((nbMusics * nbPlayer) * durationRatio), nbPointWon, nbPointBonusWon, nbPointMalusLoose);


            case THIEF:
                nbMusics = prop.getThiefNbMusics();
                nbPointWon = prop.getThiefNbPointWon();
                int nbPointLoose = prop.getThiefNbPointLoose();
                return new ThiefContent( (int) (nbMusics * durationRatio), nbPointWon, nbPointLoose);


            default:        throw new IllegalArgumentException("Il manque un case ('" + this + "').");
        }
    }

    /**
     * Test if it's the last round.
     *
     * @return TRUE if it's the last round, FALSE otherwise
     */
    public boolean isLast() {
        return Arrays.stream(Round.values()).noneMatch(round -> round.roundNumber == this.roundNumber+1);
    }

    /**
     * Gets first round.
     *
     * @return the first round
     */
    public static Round getFirst() {
        return Arrays.stream(Round.values()).filter(round -> round.roundNumber == 0)
                .findFirst()
                .orElseThrow( () -> new IllegalArgumentException("Round n°0 not found.") );
    }

}
