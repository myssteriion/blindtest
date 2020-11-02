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

class LuckyTest {
    
    private Lucky lucky = new Lucky(2, 20, 10);
    
    
    
    @Test
    void prepareAndGetNbPlayers() {
        
        TestUtils.assertThrowMandatoryField("game", () -> lucky.prepare(null) );
        
        Player p1 = new Player().setProfile( new ProfileEntity().setName("name1") ).setScore(0);
        Player p2 = new Player().setProfile( new ProfileEntity().setName("name2") ).setScore(0);
        
        Game game = new Game().setDuration(Duration.NORMAL).setPlayers(Arrays.asList(p1, p2));
        
        Assertions.assertEquals( 0, lucky.getNbPlayers() );
        lucky.prepare(game);
        Assertions.assertEquals( 1, lucky.getNbPlayers() );
        
        Player p3 = new Player().setProfile( new ProfileEntity().setName("name1") ).setScore(0);
        Player p4 = new Player().setProfile( new ProfileEntity().setName("name2") ).setScore(0);
        Player p5 = new Player().setProfile( new ProfileEntity().setName("name1") ).setScore(0);
        Player p6 = new Player().setProfile( new ProfileEntity().setName("name2") ).setScore(0);
        Player p7 = new Player().setProfile( new ProfileEntity().setName("name2") ).setScore(0);
        
        
        game = new Game().setDuration(Duration.NORMAL).setPlayers( Arrays.asList(p1, p2, p3, p4, p5, p6, p7) );
        
        lucky.prepare(game);
        Assertions.assertEquals( 2, lucky.getNbPlayers() );
    }
    
    @Test
    void apply() {
        
        TestUtils.assertThrowMandatoryField("game", () -> lucky.apply(null, null) );
        
        Player p1 = new Player().setProfile( new ProfileEntity().setName("name1") ).setScore(0);
        Player p2 = new Player().setProfile( new ProfileEntity().setName("name2") ).setScore(0);
        Player p3 = new Player().setProfile( new ProfileEntity().setName("name3") ).setScore(0);
        
        Game game = new Game().setDuration(Duration.NORMAL).setPlayers( Arrays.asList(p1, p2, p3) );
        TestUtils.assertThrowMandatoryField("musicResult", () -> lucky.apply(game, null) );
        
        
        lucky.prepare(game);
        
        MusicResult musicResult = new MusicResult();
        musicResult.setTitleWinners( Collections.singletonList("name1") );
        musicResult.setAuthorWinners( Collections.singletonList("name1") );
        
        lucky.apply(game, musicResult);
        Assertions.assertTrue(  p1.getScore() >= 2 * lucky.getNbPointWon() && p1.getTeamNumber() <= 2 * lucky.getNbPointWon() + lucky.getNbPointBonus() );
        Assertions.assertTrue(  p2.getScore() >= 0 && p2.getTeamNumber() <= lucky.getNbPointBonus() );
        Assertions.assertTrue(  p3.getScore() >= 0 && p3.getTeamNumber() <= lucky.getNbPointBonus() );
    }
    
    @Test
    void testToString() {
        Assertions.assertEquals("roundName=LUCKY, nbMusics=2, nbPointWon=20, nbPointBonus=10, nbPlayers=0", lucky.toString() );
    }
    
}