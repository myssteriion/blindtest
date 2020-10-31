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

public class LuckyTest {
    
    private Lucky lucky = new Lucky(2, 20, 10);
    
    
    
    @Test
    public void prepareAndGetNbPlayers() {
        
        try {
            lucky.prepare(null);
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'game' est obligatoire."), e);
        }
        
        Player p1 = new Player().setProfile( new ProfileEntity().setName("name1") ).setScore(0);
        Player p2 = new Player().setProfile( new ProfileEntity().setName("name2") ).setScore(0);
        
        Game game = new Game().setDuration(Duration.NORMAL).setPlayers(Arrays.asList(p1, p2));
        
        Assert.assertEquals( 0, lucky.getNbPlayers() );
        lucky.prepare(game);
        Assert.assertEquals( 1, lucky.getNbPlayers() );
        
        Player p3 = new Player().setProfile( new ProfileEntity().setName("name1") ).setScore(0);
        Player p4 = new Player().setProfile( new ProfileEntity().setName("name2") ).setScore(0);
        Player p5 = new Player().setProfile( new ProfileEntity().setName("name1") ).setScore(0);
        Player p6 = new Player().setProfile( new ProfileEntity().setName("name2") ).setScore(0);
        Player p7 = new Player().setProfile( new ProfileEntity().setName("name2") ).setScore(0);
        
        
        game = new Game().setDuration(Duration.NORMAL).setPlayers( Arrays.asList(p1, p2, p3, p4, p5, p6, p7) );
        
        lucky.prepare(game);
        Assert.assertEquals( 2, lucky.getNbPlayers() );
    }
    
    @Test
    public void apply() {
        
        try {
            lucky.apply(null, null);
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'game' est obligatoire."), e);
        }
        
        Player p1 = new Player().setProfile( new ProfileEntity().setName("name1") ).setScore(0);
        Player p2 = new Player().setProfile( new ProfileEntity().setName("name2") ).setScore(0);
        Player p3 = new Player().setProfile( new ProfileEntity().setName("name3") ).setScore(0);
        
        Game game = new Game().setDuration(Duration.NORMAL).setPlayers( Arrays.asList(p1, p2, p3) );
        
        try {
            lucky.apply(game, null);
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'musicResult' est obligatoire."), e);
        }
        
        
        lucky.prepare(game);
        
        MusicResult musicResult = new MusicResult();
        musicResult.setTitleWinners( Collections.singletonList("name1") );
        musicResult.setAuthorWinners( Collections.singletonList("name1") );
        
        lucky.apply(game, musicResult);
        Assert.assertTrue(  p1.getScore() >= 2 * lucky.getNbPointWon() && p1.getTeamNumber() <= 2 * lucky.getNbPointWon() + lucky.getNbPointBonus() );
        Assert.assertTrue(  p2.getScore() >= 0 && p2.getTeamNumber() <= lucky.getNbPointBonus() );
        Assert.assertTrue(  p3.getScore() >= 0 && p3.getTeamNumber() <= lucky.getNbPointBonus() );
    }
    
    @Test
    public void testToString() {
        Assert.assertEquals("roundName=LUCKY, nbMusics=2, nbPointWon=20, nbPointBonus=10, nbPlayers=0", lucky.toString() );
    }
    
}