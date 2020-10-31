package com.myssteriion.blindtest.model.round.impl;

import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.entity.ProfileEntity;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.blindtest.model.game.Player;
import com.myssteriion.utils.test.TestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class FriendshipTest {
    
    private Friendship friendship = new Friendship(2, 20);
    
    
    
    @Test
    public void prepareAndGetNbTeams() {
        
        try {
            friendship.prepare(null);
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'game' est obligatoire."), e);
        }
        
        Player p1 = new Player().setProfile( new ProfileEntity().setName("name1") ).setScore(0).setTeamNumber(-1);
        Player p2 = new Player().setProfile( new ProfileEntity().setName("name2") ).setScore(0).setTeamNumber(-1);
        
        Game game = new Game().setDuration(Duration.NORMAL).setPlayers( Arrays.asList(p1, p2) );
        
        Assert.assertEquals( 0, friendship.getNbTeams() );
        Assert.assertEquals( -1, p1.getTeamNumber() );
        Assert.assertEquals( -1, p2.getTeamNumber() );
        
        friendship.prepare(game);
        Assert.assertEquals( 2, friendship.getNbTeams() );
        Assert.assertEquals( 0, p1.getTeamNumber() );
        Assert.assertEquals( 1, p2.getTeamNumber() );
        
        
        Player p3 = new Player().setProfile( new ProfileEntity().setName("name3") ).setScore(0).setRank(1);
        Player p4 = new Player().setProfile( new ProfileEntity().setName("name4") ).setScore(0).setRank(1);
        Player p5 = new Player().setProfile( new ProfileEntity().setName("name5") ).setScore(0).setRank(1);
        
        game = new Game().setDuration(Duration.NORMAL).setPlayers(Arrays.asList(p1, p2, p3, p4, p5));
        
        friendship.prepare(game);
        Assert.assertEquals( 3, friendship.getNbTeams() );
        Assert.assertTrue(p1.getTeamNumber() != -1);
        Assert.assertTrue(p2.getTeamNumber() != -1);
        Assert.assertTrue(p3.getTeamNumber() != -1);
        Assert.assertTrue(p4.getTeamNumber() != -1);
        Assert.assertTrue(p5.getTeamNumber() != -1);
    }
    
    @Test
    public void apply() {
        
        try {
            friendship.apply(null, null);
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'game' est obligatoire."), e);
        }
        
        Player p1 = new Player().setProfile( new ProfileEntity().setName("name1") ).setScore(0);
        Player p2 = new Player().setProfile( new ProfileEntity().setName("name2") ).setScore(0);
        Player p3 = new Player().setProfile( new ProfileEntity().setName("name3") ).setScore(0);
        Player p4 = new Player().setProfile( new ProfileEntity().setName("name4") ).setScore(0);
        
        Game game = new Game().setDuration(Duration.NORMAL).setPlayers( Arrays.asList(p1, p2, p3, p4) );
        
        try {
            friendship.apply(game, null);
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'musicResult' est obligatoire."), e);
        }
        
        friendship.prepare(game);
        
        
        MusicResult musicResult = new MusicResult();
        musicResult.setTitleWinners( Collections.singletonList("name1") );
        musicResult.setAuthorWinners( Collections.singletonList("name1") );
        
        friendship.apply(game, musicResult);
        int winTeam = p1.getTeamNumber();
        game.getPlayers().stream().filter(player -> player.getTeamNumber() == winTeam).forEach(player -> {
            Assert.assertEquals( friendship.getNbPointWon()*2, player.getScore() );
        });
        game.getPlayers().stream().filter( player -> !(player.getTeamNumber() == winTeam) ).forEach(player -> {
            Assert.assertEquals( 0, player.getScore() );
        });
    }
    
    @Test
    public void testToString()  {
        Assert.assertEquals("roundName=FRIENDSHIP, nbMusics=2, nbPointWon=20, nbTeams=0", friendship.toString() );
    }
}