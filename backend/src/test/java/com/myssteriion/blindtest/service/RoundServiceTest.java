package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.RoundName;
import com.myssteriion.blindtest.model.entity.ProfileEntity;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.Player;
import com.myssteriion.blindtest.model.round.AbstractRound;
import com.myssteriion.blindtest.model.round.impl.Choice;
import com.myssteriion.blindtest.model.round.impl.Classic;
import com.myssteriion.blindtest.model.round.impl.Friendship;
import com.myssteriion.blindtest.model.round.impl.Lucky;
import com.myssteriion.blindtest.model.round.impl.Recovery;
import com.myssteriion.blindtest.model.round.impl.Thief;
import com.myssteriion.utils.test.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class RoundServiceTest extends AbstractTest {
    
    private RoundService roundService;
    
    
    @Before
    public void before() {
        roundService = new RoundService(roundProperties);
    }
    
    
    
    @Test
    public void createFirstRound() {
        
        try {
            roundService.createFirstRound(null);
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'game' est obligatoire."), e);
        }
        
        Game game = new Game();
        try {
            roundService.createFirstRound(game);
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'game -> duration' est obligatoire."), e);
        }
        
        game.setDuration(Duration.NORMAL)
                .setPlayers(Arrays.asList(
                        new Player().setProfile(new ProfileEntity().setName("name1")),
                        new Player().setProfile(new ProfileEntity().setName("name2"))
                ));
        
        AbstractRound round = roundService.createFirstRound(game);
        Assert.assertTrue(round instanceof Classic);
    }
    
    @Test
    public void createNextRound() {
        
        try {
            roundService.createNextRound(null);
        } catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'game' est obligatoire."), e);
        }
        
        Game game = new Game();
        try {
            roundService.createFirstRound(game);
        } catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'game -> duration' est obligatoire."), e);
        }
        
        game.setDuration(Duration.NORMAL)
                .setPlayers(Arrays.asList(
                        new Player().setProfile(new ProfileEntity().setName("name1")),
                        new Player().setProfile(new ProfileEntity().setName("name2"))
                ));
        
        game.setRound( roundService.createFirstRound(game) );
        Assert.assertTrue(game.getRound() instanceof Classic);
        
        game.setRound( roundService.createNextRound(game) );
        Assert.assertTrue(game.getRound() instanceof Choice);
        
        game.setRound( roundService.createNextRound(game) );
        Assert.assertTrue(game.getRound() instanceof Lucky);
        
        game.setRound( roundService.createNextRound(game) );
        Assert.assertTrue(game.getRound() instanceof Friendship);
        
        game.setRound( roundService.createNextRound(game) );
        Assert.assertTrue(game.getRound() instanceof Thief);
        
        game.setRound( roundService.createNextRound(game) );
        Assert.assertTrue(game.getRound() instanceof Recovery);
    }
    
    @Test
    public void isLastRound() {
        
        Assert.assertFalse( roundService.isLastRound(RoundName.CLASSIC));
        Assert.assertFalse( roundService.isLastRound(RoundName.CHOICE));
        Assert.assertFalse( roundService.isLastRound(RoundName.LUCKY));
        Assert.assertFalse( roundService.isLastRound(RoundName.FRIENDSHIP));
        Assert.assertFalse( roundService.isLastRound(RoundName.THIEF));
        Assert.assertTrue( roundService.isLastRound(RoundName.RECOVERY));
    }
    
}