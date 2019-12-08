package com.myssteriion.blindtest.model.common.roundcontent.impl;

import com.myssteriion.blindtest.model.common.roundcontent.AbstractRoundContent;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;

/**
 * The Thief round content.
 */
public class ThiefContent extends AbstractRoundContent {

    /**
     * The number of lose points.
     */
    private int nbPointLoose;



    /**
     * Instantiates a new Thief content.
     *
     * @param nbMusics     the nb musics
     * @param nbPointWon   the nb point won
     * @param nbPointLoose the nb point loose
     */
    public ThiefContent(int nbMusics, int nbPointWon, int nbPointLoose) {

        super(nbMusics, nbPointWon);
        this.nbPointLoose = Math.min(nbPointLoose, 0);
    }



    /**
     * Gets nb point loose.
     *
     * @return the nb point loose
     */
    public int getNbPointLoose() {
        return nbPointLoose;
    }


    @Override
    public Game apply(Game game, MusicResult musicResult) {

        game = super.apply(game, musicResult);

        game.getPlayers().stream()
                .filter( playerDto -> musicResult.isLoser(playerDto.getProfile().getName()) )
                .forEach( playerDto -> {

                    Long nbLose = musicResult.getLosers().stream()
                                    .filter(name -> name.equals(playerDto.getProfile().getName()))
                                    .count();

                    playerDto.addScore(nbLose.intValue() * nbPointLoose);
                });

        return game;
    }


    @Override
    public String toString() {
        return super.toString() +
                ", nbPointLoose=" + nbPointLoose;
    }

}
