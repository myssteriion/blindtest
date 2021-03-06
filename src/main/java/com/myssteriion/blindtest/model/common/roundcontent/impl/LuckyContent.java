package com.myssteriion.blindtest.model.common.roundcontent.impl;

import com.myssteriion.blindtest.model.common.roundcontent.AbstractRoundContent;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.utils.CommonUtils;

/**
 * The Lucky round content.
 */
public class LuckyContent extends AbstractRoundContent {
    
    /**
     * The number of bonus points.
     */
    private int nbPointBonus;
    
    /**
     * The number of players.
     */
    private int nbPlayers;
    
    
    
    /**
     * Instantiates a new Lucky content.
     *
     * @param nbMusics     the nb musics
     * @param nbPointWon   the nb point won
     * @param nbPointBonus the nb bonus point
     */
    public LuckyContent(int nbMusics, int nbPointWon, int nbPointBonus) {
        super(nbMusics, nbPointWon);
        this.nbPointBonus = Math.max(nbPointBonus, 0);
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
     * Gets nbPlayers.
     *
     * @return The nbPlayers.
     */
    public int getNbPlayers() {
        return nbPlayers;
    }
    
    
    @Override
    public void prepare(Game game) {
        
        super.prepare(game);
        nbPlayers = (game.getPlayers().size() <= 6) ? 1 : 2;
    }
    
    @Override
    public Game apply(Game game, MusicResult musicResult) {
        
        game = super.apply(game, musicResult);
        
        for (int i = 0; i < nbPlayers; i++)
            game.getPlayers().get( CommonUtils.RANDOM.nextInt(game.getPlayers().size() ) ).addScore(nbPointBonus);
        
        return game;
    }
    
    
    @Override
    public String toString() {
        return super.toString() +
                ", nbPointBonus=" + nbPointBonus +
                ", nbPlayers=" + nbPlayers;
    }
    
}
