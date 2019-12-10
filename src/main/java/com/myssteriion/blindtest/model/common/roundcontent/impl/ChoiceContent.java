package com.myssteriion.blindtest.model.common.roundcontent.impl;

import com.myssteriion.blindtest.model.common.roundcontent.AbstractRoundContent;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.blindtest.model.game.Player;

/**
 * The Choice round content.
 */
public class ChoiceContent extends AbstractRoundContent {

    /**
     * The number of bonus points.
     */
    private int nbPointBonus;

    /**
     * The number of malus points.
     */
    private int nbPointMalus;



    /**
     * Instantiates a new Choice content.
     *
     * @param nbMusics     the nb musics
     * @param nbPointWon   the nb point won
     * @param nbPointBonus the nb point bonus
     * @param nbPointMalus the nb point malus
     */
    public ChoiceContent(int nbMusics, int nbPointWon, int nbPointBonus, int nbPointMalus) {

        super(nbMusics, nbPointWon);
        this.nbPointBonus = Math.max(nbPointBonus, 0);
        this.nbPointMalus = Math.min(nbPointMalus, 0);
    }



    /**
     * Gets nb point bonus.
     *
     * @return the nb point bonus
     */
    public int getNbPointBonus() {
        return nbPointBonus;
    }

    /**
     * Gets nb point malus.
     *
     * @return the nb point malus
     */
    public int getNbPointMalus() {
        return nbPointMalus;
    }


    @Override
    public void prepare(Game game) {

        super.prepare(game);
        game.getPlayers().get(0).setTurnToChoose(true);
    }

    @Override
    public Game apply(Game game, MusicResult musicResult) {

        game = super.apply(game, musicResult);

        game.getPlayers().stream()
                .filter(Player::isTurnToChoose)
                .forEach( player -> {

                    String profileName = player.getProfile().getName();

                    int point = nbPointMalus;
                    if ( musicResult.isAuthorAndTitleWinner(profileName) )
                        point = nbPointBonus*2;
                    else if ( musicResult.isAuthorWinner(profileName) || musicResult.isTitleWinner(profileName) )
                        point = nbPointBonus;

                    player.addScore(point);
                    player.setTurnToChoose(false);
                });

        if ( !isLastMusic(game) ) {

            // +1 car le gameDTO n'a pas encore subit le next
            int numPlayer = (1 + game.getNbMusicsPlayedInRound()) % game.getPlayers().size();
            game.getPlayers().get(numPlayer).setTurnToChoose(true);
        }

        return game;
    }


    @Override
    public String toString() {
        return super.toString() +
                ", nbPointBonus=" + nbPointBonus +
                ", nbPointMalus=" + nbPointMalus;
    }

}
