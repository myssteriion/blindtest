package com.myssteriion.blindtest.model.common;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ChoiceContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ClassicContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.LuckyContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.RecoveryContent;
import com.myssteriion.blindtest.model.common.roundcontent.impl.ThiefContent;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
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
    public void createRoundContent(){
        
        List<Player> players = Arrays.asList(
                new Player(new ProfileDTO().setName("name")),
                new Player(new ProfileDTO().setName("name2")));
        Duration duration = Duration.NORMAL;
        
        Game game = new Game(players, duration, null, null, ConnectionMode.OFFLINE, roundContentProperties);
        
        
        Assert.assertTrue( Round.CLASSIC.createRoundContent(game, roundContentProperties) instanceof ClassicContent );
        Assert.assertFalse( game.getPlayers().get(0).isTurnToChoose() );
        Assert.assertFalse( game.getPlayers().get(1).isTurnToChoose() );
        
        Assert.assertTrue( Round.CHOICE.createRoundContent(game, roundContentProperties) instanceof ChoiceContent);
        Assert.assertTrue( game.getPlayers().get(0).isTurnToChoose() ^ game.getPlayers().get(1).isTurnToChoose() );
        
        Assert.assertTrue( Round.LUCKY.createRoundContent(game, roundContentProperties) instanceof LuckyContent);
        Assert.assertFalse( game.getPlayers().get(0).isTurnToChoose() );
        Assert.assertFalse( game.getPlayers().get(1).isTurnToChoose() );
        
        Assert.assertTrue( Round.THIEF.createRoundContent(game, roundContentProperties) instanceof ThiefContent);
        Assert.assertFalse( game.getPlayers().get(0).isTurnToChoose() );
        Assert.assertFalse( game.getPlayers().get(1).isTurnToChoose() );
        
        Assert.assertTrue( Round.RECOVERY.createRoundContent(game, roundContentProperties) instanceof RecoveryContent);
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