package com.myssteriion.blindtest.model.round.impl;

import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.entity.ProfileEntity;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.blindtest.model.game.Player;
import com.myssteriion.utils.test.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

class FriendshipTest {
    
    private Friendship friendship = new Friendship(2, 20);
    
    
    
    @Test
    void prepareAndGetNbTeams() {
        
        TestUtils.assertThrowMandatoryField("game", () -> friendship.prepare(null) );
        
        Player p1 = new Player().setProfile( new ProfileEntity().setName("name1") ).setScore(0).setTeamNumber(-1);
        Player p2 = new Player().setProfile( new ProfileEntity().setName("name2") ).setScore(0).setTeamNumber(-1);
        
        Game game = new Game().setDuration(Duration.NORMAL).setPlayers( Arrays.asList(p1, p2) );
        
        Assertions.assertEquals( 0, friendship.getNbTeams() );
        Assertions.assertEquals( -1, p1.getTeamNumber() );
        Assertions.assertEquals( -1, p2.getTeamNumber() );
        
        friendship.prepare(game);
        Assertions.assertEquals( 2, friendship.getNbTeams() );
        Assertions.assertEquals( 0, p1.getTeamNumber() );
        Assertions.assertEquals( 1, p2.getTeamNumber() );
        
        
        Player p3 = new Player().setProfile( new ProfileEntity().setName("name3") ).setScore(0).setRank(1);
        Player p4 = new Player().setProfile( new ProfileEntity().setName("name4") ).setScore(0).setRank(1);
        Player p5 = new Player().setProfile( new ProfileEntity().setName("name5") ).setScore(0).setRank(1);
        
        game = new Game().setDuration(Duration.NORMAL).setPlayers(Arrays.asList(p1, p2, p3, p4, p5));
        
        friendship.prepare(game);
        Assertions.assertEquals( 3, friendship.getNbTeams() );
        Assertions.assertTrue(p1.getTeamNumber() != -1);
        Assertions.assertTrue(p2.getTeamNumber() != -1);
        Assertions.assertTrue(p3.getTeamNumber() != -1);
        Assertions.assertTrue(p4.getTeamNumber() != -1);
        Assertions.assertTrue(p5.getTeamNumber() != -1);
    }
    
    @Test
    void apply() {
        
        TestUtils.assertThrowMandatoryField("game", () -> friendship.apply(null, null) );
        
        Player p1 = new Player().setProfile( new ProfileEntity().setName("name1") ).setScore(0);
        Player p2 = new Player().setProfile( new ProfileEntity().setName("name2") ).setScore(0);
        Player p3 = new Player().setProfile( new ProfileEntity().setName("name3") ).setScore(0);
        Player p4 = new Player().setProfile( new ProfileEntity().setName("name4") ).setScore(0);
        
        Game game = new Game().setDuration(Duration.NORMAL).setPlayers( Arrays.asList(p1, p2, p3, p4) );
        TestUtils.assertThrowMandatoryField("musicResult", () -> friendship.apply(game, null) );
        
        friendship.prepare(game);
        
        
        MusicResult musicResult = new MusicResult();
        musicResult.setTitleWinners( Collections.singletonList("name1") );
        musicResult.setAuthorWinners( Collections.singletonList("name1") );
        
        friendship.apply(game, musicResult);
        int winTeam = p1.getTeamNumber();
        game.getPlayers().stream().filter(player -> player.getTeamNumber() == winTeam).forEach(player -> {
            Assertions.assertEquals( friendship.getNbPointWon()*2, player.getScore() );
        });
        game.getPlayers().stream().filter( player -> !(player.getTeamNumber() == winTeam) ).forEach(player -> {
            Assertions.assertEquals( 0, player.getScore() );
        });
    }
    
    @Test
    void testToString()  {
        Assertions.assertEquals("roundName=FRIENDSHIP, nbMusics=2, nbPointWon=20, nbTeams=0", friendship.toString() );
    }
}