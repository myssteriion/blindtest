package com.myssteriion.blindtest.model.round;

import com.myssteriion.blindtest.model.round.impl.Choice;
import com.myssteriion.blindtest.model.round.impl.Classic;
import com.myssteriion.blindtest.model.round.impl.Friendship;
import com.myssteriion.blindtest.model.round.impl.Lucky;
import com.myssteriion.blindtest.model.round.impl.Recovery;
import com.myssteriion.blindtest.model.round.impl.Thief;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.properties.RoundProperties;
import com.myssteriion.utils.CommonUtils;

import java.util.Arrays;

/**
 * The Round enum.
 */
public enum Round {
    
    CLASSIC(0),
    CHOICE(1),
    LUCKY(2),
    FRIENDSHIP(3),
    THIEF(4),
    RECOVERY(5);
    
    
    
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
        return Arrays.stream( Round.values() )
                .filter(round -> round.roundNumber == this.roundNumber+1)
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Create round from game.
     *
     * @param game the game
     * @return the round
     */
    // TODO refactor en supprimant car BeanFactory n'existe plus pour la class ROUND
    public AbstractRound createRound(Game game, RoundProperties prop) {
        
        CommonUtils.verifyValue("game", game);
        
        double durationRatio = game.getDuration().getRatio();
        
        
        AbstractRound round;
        
        switch (this) {
            case CLASSIC:
                int nbMusics = prop.getClassicNbMusics();
                int nbPointWon = prop.getClassicNbPointWon();
                round = new Classic((int) (nbMusics * durationRatio), nbPointWon);
                break;
            
            case CHOICE:
                nbMusics = prop.getChoiceNbMusics();
                nbPointWon = prop.getChoiceNbPointWon();
                int nbPointBonusWon = prop.getChoiceNbPointBonus();
                int nbPointMalusLoose = prop.getChoiceNbPointMalus();
                round = new Choice((int) (nbMusics * durationRatio), nbPointWon, nbPointBonusWon, nbPointMalusLoose);
                break;
            
            case LUCKY:
                nbMusics = prop.getLuckyNbMusics();
                nbPointWon = prop.getLuckyNbPointWon();
                nbPointBonusWon = prop.getLuckyNbPointBonus();
                round = new Lucky((int) (nbMusics * durationRatio), nbPointWon, nbPointBonusWon);
                break;
            
            case FRIENDSHIP:
                nbMusics = prop.getFriendshipNbMusics();
                nbPointWon = prop.getFriendshipNbPointWon();
                round = new Friendship((int) (nbMusics * durationRatio), nbPointWon);
                break;
            
            case THIEF:
                nbMusics = prop.getThiefNbMusics();
                nbPointWon = prop.getThiefNbPointWon();
                int nbPointLoose = prop.getThiefNbPointLoose();
                round = new Thief( (int) (nbMusics * durationRatio), nbPointWon, nbPointLoose);
                break;
            
            case RECOVERY:
                nbMusics = prop.getRecoveryNbMusics();
                nbPointWon = prop.getRecoveryNbPointWon();
                round = new Recovery((int) (nbMusics * durationRatio), nbPointWon);
                break;
            
            
            default: throw new IllegalArgumentException("Il manque un case ('" + this + "').");
        }
        
        round.prepare(game);
        return round;
    }
    
    /**
     * Test if it's the last round.
     *
     * @return TRUE if it's the last round, FALSE otherwise
     */
    public boolean isLast() {
        return CommonUtils.isNullOrEmpty( nextRound() );
    }
    
    /**
     * Gets first round.
     *
     * @return the first round
     */
    public static Round getFirst() {
        return Arrays.stream( Round.values() )
                .filter(round -> round.roundNumber == 0)
                .findFirst()
                .orElseThrow( () -> new IllegalArgumentException("Round n°0 not found.") );
    }
    
}
