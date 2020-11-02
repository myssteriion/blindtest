package com.myssteriion.blindtest.model.round.impl;

import com.myssteriion.blindtest.model.common.RoundName;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.blindtest.model.round.AbstractRound;

/**
 * The Recovery round.
 */
public class Recovery extends AbstractRound {
    
    /**
     * Instantiates a new Recovery.
     *
     * @param nbMusics     the nb musics
     * @param nbPointWon   the nb point won
     */
    public Recovery(int nbMusics, int nbPointWon) {
        super(RoundName.RECOVERY, nbMusics, nbPointWon);
    }
    
    
    
    @Override
    public Game apply(Game game, MusicResult musicResult) {
        
        game = super.apply(game, musicResult);
        
        // -1 car un premier apply est fait dans le super
        game.getPlayers().stream()
                .filter( player -> musicResult.isAuthorWinner(player.getProfile().getName()) )
                .forEach( player -> player.addScore( nbPointWon * (player.getRank() - 1) ) );
        
        game.getPlayers().stream()
                .filter( player -> musicResult.isTitleWinner(player.getProfile().getName()) )
                .forEach( player -> player.addScore( nbPointWon * (player.getRank() -1) ) );
        
        return game;
    }
    
}
