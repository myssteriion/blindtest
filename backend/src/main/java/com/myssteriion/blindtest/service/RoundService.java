package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.model.common.RoundName;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.round.AbstractRound;
import com.myssteriion.blindtest.model.round.impl.Choice;
import com.myssteriion.blindtest.model.round.impl.Classic;
import com.myssteriion.blindtest.model.round.impl.Friendship;
import com.myssteriion.blindtest.model.round.impl.Lucky;
import com.myssteriion.blindtest.model.round.impl.Recovery;
import com.myssteriion.blindtest.model.round.impl.Thief;
import com.myssteriion.blindtest.properties.RoundProperties;
import com.myssteriion.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Service for Round.
 */
@Service
public class RoundService {
    
    private RoundProperties roundProperties;
    
    /**
     * The first round.
     */
    private RoundName first;
    
    /**
     * The round order.
     */
    private Map<RoundName, RoundName> order;
    
    
    
    @Autowired
    public RoundService(RoundProperties roundProperties) {
        
        this.roundProperties = roundProperties;
        this.first = RoundName.CLASSIC;
        
        this.order = new HashMap<>();
        this.order.put(first, RoundName.CHOICE);
        this.order.put(RoundName.CHOICE,  RoundName.LUCKY);
        this.order.put(RoundName.LUCKY, RoundName.FRIENDSHIP);
        this.order.put(RoundName.FRIENDSHIP, RoundName.THIEF);
        this.order.put(RoundName.THIEF, RoundName.RECOVERY);
        this.order.put(RoundName.RECOVERY, null);
    }
    
    
    
    /**
     * Gets first round.
     *
     * @param game the game
     * @return the first round
     */
    public AbstractRound createFirstRound(Game game) {
        return createRound(this.first, game);
    }
    
    /**
     * Gets next round.
     *
     * @return the next round, NULL if there isn't next
     */
    public AbstractRound createNextRound(Game game) {
        
        CommonUtils.verifyValue("game", game);
        
        AbstractRound currentRound = game.getRound();
        if (currentRound == null)
            return null;
        
        RoundName nextRoundName = order.get( currentRound.getRoundName() );
        if (nextRoundName == null)
            return null;
        
        return createRound(nextRoundName, game);
    }
    
    /**
     * Test if the round is the last round.
     *
     * @param roundName the round name
     * @return TRUE if the round is the last round, FALSE otherwise
     */
    public boolean isLastRound(RoundName roundName) {
        return roundName != null && order.get(roundName) == null;
    }
    
    
    /**
     * Create round.
     *
     * @param roundName the roundName
     * @param game      the game
     * @return the round
     */
    private AbstractRound createRound(RoundName roundName, Game game) {
        
        CommonUtils.verifyValue("roundName", roundName);
        CommonUtils.verifyValue("game", game);
        
        double durationRatio = game.getDuration().getRatio();
        
        
        AbstractRound round;
        
        switch (roundName) {
            
            case CLASSIC:
                int nbMusics = roundProperties.getClassicNbMusics();
                int nbPointWon = roundProperties.getClassicNbPointWon();
                round = new Classic((int) (nbMusics * durationRatio), nbPointWon);
                break;
            
            case CHOICE:
                nbMusics = roundProperties.getChoiceNbMusics();
                nbPointWon = roundProperties.getChoiceNbPointWon();
                int nbPointBonusWon = roundProperties.getChoiceNbPointBonus();
                int nbPointMalusLoose = roundProperties.getChoiceNbPointMalus();
                round = new Choice((int) (nbMusics * durationRatio), nbPointWon, nbPointBonusWon, nbPointMalusLoose);
                break;
            
            case LUCKY:
                nbMusics = roundProperties.getLuckyNbMusics();
                nbPointWon = roundProperties.getLuckyNbPointWon();
                nbPointBonusWon = roundProperties.getLuckyNbPointBonus();
                round = new Lucky((int) (nbMusics * durationRatio), nbPointWon, nbPointBonusWon);
                break;
            
            case FRIENDSHIP:
                nbMusics = roundProperties.getFriendshipNbMusics();
                nbPointWon = roundProperties.getFriendshipNbPointWon();
                round = new Friendship((int) (nbMusics * durationRatio), nbPointWon);
                break;
            
            case THIEF:
                nbMusics = roundProperties.getThiefNbMusics();
                nbPointWon = roundProperties.getThiefNbPointWon();
                int nbPointLoose = roundProperties.getThiefNbPointLoose();
                round = new Thief( (int) (nbMusics * durationRatio), nbPointWon, nbPointLoose);
                break;
            
            case RECOVERY:
                nbMusics = roundProperties.getRecoveryNbMusics();
                nbPointWon = roundProperties.getRecoveryNbPointWon();
                round = new Recovery((int) (nbMusics * durationRatio), nbPointWon);
                break;
            
            default:
                throw new IllegalArgumentException("miss case ('" + roundName + "')");
        }
        
        round.prepare(game);
        return round;
    }
    
}
