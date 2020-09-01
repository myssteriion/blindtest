package com.myssteriion.blindtest.model.round.impl;

import com.myssteriion.blindtest.model.round.AbstractRound;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;

/**
 * The Thief round.
 */
public class Thief extends AbstractRound {
    
    /**
     * The number of lose points.
     */
    private int nbPointLoose;
    
    
    
    /**
     * Instantiates a new Thief.
     *
     * @param nbMusics     the nb musics
     * @param nbPointWon   the nb point won
     * @param nbPointLoose the nb point loose
     */
    public Thief(int nbMusics, int nbPointWon, int nbPointLoose) {
        
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
                .filter( player -> musicResult.isLoser(player.getProfile().getName()) )
                .forEach( player -> {
                    
                    Long nbLose = musicResult.getLosers().stream()
                            .filter(name -> name.equals(player.getProfile().getName()))
                            .count();
                    
                    player.addScore(nbLose.intValue() * nbPointLoose);
                });
        
        return game;
    }
    
    
    @Override
    public String toString() {
        return super.toString() +
                ", nbPointLoose=" + nbPointLoose;
    }
    
}
