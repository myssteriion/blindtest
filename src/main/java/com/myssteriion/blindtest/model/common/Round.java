package com.myssteriion.blindtest.model.common;

import com.myssteriion.blindtest.model.common.roundcontent.AbstractRoundContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ChoiceContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ClassicContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.FriendshipContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.LuckyContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.RecoveryContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ThiefContent;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.properties.RoundContentProperties;
import com.myssteriion.utils.BeanFactory;
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
     * Create round content from game.
     *
     * @param game the game
     * @return the round content
     */
    public AbstractRoundContent createRoundContent(Game game) {
        
        CommonUtils.verifyValue("game", game);
        
        double durationRatio = game.getDuration().getRatio();
        
        RoundContentProperties prop = BeanFactory.getBean(RoundContentProperties.class);
        
        AbstractRoundContent roundContent;
        
        switch (this) {
            case CLASSIC:
                int nbMusics = prop.getClassicNbMusics();
                int nbPointWon = prop.getClassicNbPointWon();
                roundContent = new ClassicContent((int) (nbMusics * durationRatio), nbPointWon);
                break;
            
            case CHOICE:
                nbMusics = prop.getChoiceNbMusics();
                nbPointWon = prop.getChoiceNbPointWon();
                int nbPointBonusWon = prop.getChoiceNbPointBonus();
                int nbPointMalusLoose = prop.getChoiceNbPointMalus();
                roundContent = new ChoiceContent((int) (nbMusics * durationRatio), nbPointWon, nbPointBonusWon, nbPointMalusLoose);
                break;
            
            case LUCKY:
                nbMusics = prop.getLuckyNbMusics();
                nbPointWon = prop.getLuckyNbPointWon();
                nbPointBonusWon = prop.getLuckyNbPointBonus();
                roundContent = new LuckyContent((int) (nbMusics * durationRatio), nbPointWon, nbPointBonusWon);
                break;
            
            case FRIENDSHIP:
                nbMusics = prop.getFriendshipNbMusics();
                nbPointWon = prop.getFriendshipNbPointWon();
                roundContent = new FriendshipContent((int) (nbMusics * durationRatio), nbPointWon);
                break;
            
            case THIEF:
                nbMusics = prop.getThiefNbMusics();
                nbPointWon = prop.getThiefNbPointWon();
                int nbPointLoose = prop.getThiefNbPointLoose();
                roundContent = new ThiefContent( (int) (nbMusics * durationRatio), nbPointWon, nbPointLoose);
                break;
            
            case RECOVERY:
                nbMusics = prop.getRecoveryNbMusics();
                nbPointWon = prop.getRecoveryNbPointWon();
                roundContent = new RecoveryContent((int) (nbMusics * durationRatio), nbPointWon);
                break;
            
            
            default: throw new IllegalArgumentException("Il manque un case ('" + this + "').");
        }
        
        roundContent.prepare(game);
        return roundContent;
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
