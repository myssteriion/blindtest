package com.myssteriion.blindtest.model.common.roundcontent;

import com.myssteriion.blindtest.model.dto.game.Game;
import com.myssteriion.blindtest.model.dto.game.MusicResult;
import com.myssteriion.blindtest.tools.Tool;

/**
 * Abstract class for all round content.
 */
public abstract class AbstractRoundContent {

    /**
     * The musics number.
     */
    protected int nbMusics;

    /**
     * The number of win points.
     */
    protected int nbPointWon;



    /**
     * Instantiates a new Abstract round content.
     *
     * @param nbMusics   the nb musics
     * @param nbPointWon the nb point won
     */
    public AbstractRoundContent(int nbMusics, int nbPointWon) {
        this.nbMusics = Math.max(nbMusics, 0);
        this.nbPointWon = Math.max(nbPointWon, 0);
    }



    /**
     * Gets nb musics.
     *
     * @return the nb musics
     */
    public int getNbMusics() {
        return nbMusics;
    }

    /**
     * Gets nb point won.
     *
     * @return the nb point won
     */
    public int getNbPointWon() {
        return nbPointWon;
    }


    /**
     * Update the game from music result and this round content.
     *
     * @param game        the game
     * @param musicResult the music result
     * @return the game after update
     */
    public Game apply(Game game, MusicResult musicResult) {

        Tool.verifyValue("gameDto", game);
        Tool.verifyValue("musicResultDto", musicResult);

        game.getPlayers().stream()
                            .filter( playerDto -> musicResult.isWinner(playerDto.getName()) )
                            .forEach( playerDto -> playerDto.addScore(nbPointWon) );

        return game;
    }

    /**
     * Test if the round content is finished.
     *
     * @param game the game
     * @return TRUE if the round content is finished, FALSE otherwise
     */
    public boolean isFinished(Game game) {

        Tool.verifyValue("gameDto", game);

        return game.getNbMusicsPlayedInRound() == nbMusics;
    }

    /**
     * Test if the round content is last step.
     *
     * @param game the game
     * @return TRUE if the round content is last step, FALSE otherwise
     */
    public boolean isLastMusic(Game game) {

        Tool.verifyValue("gameDto", game);

        return game.getNbMusicsPlayedInRound() == nbMusics - 1;
    }


    @Override
    public String toString() {
        return "nbMusics=" + nbMusics +
                ", nbPointWon=" + nbPointWon;
    }

}
