package com.myssteriion.blindtest.model.common.roundcontent.impl;

import com.myssteriion.blindtest.model.common.roundcontent.AbstractRoundContent;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;

/**
 * The Recovery round content.
 */
public class RecoveryContent extends AbstractRoundContent {

    /**
     * Instantiates a new Recovery content.
     *
     * @param nbMusics     the nb musics
     * @param nbPointWon   the nb point won
     */
    public RecoveryContent(int nbMusics, int nbPointWon) {
        super(nbMusics, nbPointWon);
    }



    @Override
    public Game apply(Game game, MusicResult musicResult) {

        game = super.apply(game, musicResult);

        // -1 car un premier apply est fait dans le super
        game.getPlayers().stream()
                .filter( player -> musicResult.isAuthorWinner(player.getProfile().getName()) )
                .forEach( player -> player.addScore( nbPointWon * (player.getRank().getNum() - 1) ) );

        game.getPlayers().stream()
                .filter( player -> musicResult.isTitleWinner(player.getProfile().getName()) )
                .forEach( player -> player.addScore( nbPointWon * (player.getRank().getNum() -1) ) );

        return game;
    }

}
