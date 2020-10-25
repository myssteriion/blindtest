package com.myssteriion.blindtest.model.round;

import com.myssteriion.blindtest.model.common.RoundName;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.utils.CommonUtils;

/**
 * Abstract class for all round.
 */
public abstract class AbstractRound {
    
    /**
     * The round name.
     */
    private RoundName roundName;
    
    /**
     * The musics number.
     */
    protected int nbMusics;
    
    /**
     * The number of win points.
     */
    protected int nbPointWon;
    
    
    
    /**
     * Instantiates a new AbstractRound.
     *
     * @param roundName  the roundName
     * @param nbMusics   the nb musics
     * @param nbPointWon the nb point won
     */
    public AbstractRound(RoundName roundName, int nbMusics, int nbPointWon) {
        this.roundName = roundName;
        this.nbMusics = Math.max(nbMusics, 0);
        this.nbPointWon = Math.max(nbPointWon, 0);
    }
    
    
    
    /**
     * Gets roundName.
     *
     * @return The roundName.
     */
    public RoundName getRoundName() {
        return roundName;
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
     * Prepare round.
     *
     * @param game the game
     */
    public void prepare(Game game) {
        
        CommonUtils.verifyValue("game", game);
        
        game.getPlayers().forEach( player -> {
            player.setTurnToChoose(false);
            player.setTeamNumber(-1);
        });
    }
    
    /**
     * Update the game from music result and this round.
     *
     * @param game        the game
     * @param musicResult the music result
     * @return the game after update
     */
    public Game apply(Game game, MusicResult musicResult) {
        
        CommonUtils.verifyValue("game", game);
        CommonUtils.verifyValue("musicResult", musicResult);
        
        game.getPlayers().stream()
                .filter( player -> musicResult.isAuthorWinner(player.getProfile().getName()) )
                .forEach( player -> player.addScore(nbPointWon) );
        
        game.getPlayers().stream()
                .filter( player -> musicResult.isTitleWinner(player.getProfile().getName()) )
                .forEach( player -> player.addScore(nbPointWon) );
        
        game.getPlayers().stream()
                .filter( player -> musicResult.hadPenalty(player.getProfile().getName()) )
                .forEach( player -> player.addScore(nbPointWon * -2) );
        
        return game;
    }
    
    
    /**
     * Test if the round is last step.
     *
     * @param game the game
     * @return TRUE if the round is last step, FALSE otherwise
     */
    public boolean isLastStep(Game game) {
        
        CommonUtils.verifyValue("game", game);
        
        return game.getNbMusicsPlayedInRound() == nbMusics - 1;
    }
    
    /**
     * Test if the round is finished.
     *
     * @param game the game
     * @return TRUE if the round is finished, FALSE otherwise
     */
    public boolean isFinished(Game game) {
        
        CommonUtils.verifyValue("game", game);
        
        return game.getNbMusicsPlayedInRound() == nbMusics;
    }
    
    
    @Override
    public String toString() {
        return "roundName=" + roundName +
                ", nbMusics=" + nbMusics +
                ", nbPointWon=" + nbPointWon;
    }
    
}
