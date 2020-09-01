package com.myssteriion.blindtest.model.round.impl;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.round.Round;
import com.myssteriion.blindtest.model.entity.ProfileEntity;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class RoundTest extends AbstractTest {
    
    @Test
    public void getter() {
        Assert.assertEquals( 0, Round.getFirst().getRoundNumber() );
    }
    
    @Test
    public void nextRound() {
        Assert.assertEquals( Round.CHOICE, Round.CLASSIC.nextRound() );
        Assert.assertEquals( Round.LUCKY, Round.CHOICE.nextRound() );
        Assert.assertEquals( Round.FRIENDSHIP, Round.LUCKY.nextRound() );
        Assert.assertEquals( Round.THIEF, Round.FRIENDSHIP.nextRound() );
        Assert.assertEquals( Round.RECOVERY, Round.THIEF.nextRound() );
        Assert.assertNull( Round.RECOVERY.nextRound() );
    }
    
    @Test
    public void createRound(){
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileEntity().setName("name")),
                new Player(new ProfileEntity().setName("name2")));
        Duration duration = Duration.NORMAL;
        
        Game game = new Game(players, duration, null, null, roundProperties);
        
        
        Assert.assertTrue( Round.CLASSIC.createRound(game, roundProperties) instanceof Classic);
        Assert.assertFalse( game.getPlayers().get(0).isTurnToChoose() );
        Assert.assertFalse( game.getPlayers().get(1).isTurnToChoose() );
        
        Assert.assertTrue( Round.CHOICE.createRound(game, roundProperties) instanceof Choice);
        Assert.assertTrue( game.getPlayers().get(0).isTurnToChoose() ^ game.getPlayers().get(1).isTurnToChoose() );
        
        Assert.assertTrue( Round.LUCKY.createRound(game, roundProperties) instanceof Lucky);
        Assert.assertFalse( game.getPlayers().get(0).isTurnToChoose() );
        Assert.assertFalse( game.getPlayers().get(1).isTurnToChoose() );
        
        Assert.assertTrue( Round.THIEF.createRound(game, roundProperties) instanceof Thief);
        Assert.assertFalse( game.getPlayers().get(0).isTurnToChoose() );
        Assert.assertFalse( game.getPlayers().get(1).isTurnToChoose() );
        
        Assert.assertTrue( Round.RECOVERY.createRound(game, roundProperties) instanceof Recovery);
        Assert.assertFalse( game.getPlayers().get(0).isTurnToChoose() );
        Assert.assertFalse( game.getPlayers().get(1).isTurnToChoose() );
    }
    
    @Test
    public void isLast() {
        
        Assert.assertFalse( Round.CLASSIC.isLast() );
        Assert.assertFalse( Round.CHOICE.isLast() );
        Assert.assertFalse( Round.LUCKY.isLast() );
        Assert.assertFalse( Round.THIEF.isLast() );
        Assert.assertTrue( Round.RECOVERY.isLast() );
    }
    
    @Test
    public void getFirst() {
        Assert.assertEquals( Round.CLASSIC, Round.getFirst() );
    }
    
}