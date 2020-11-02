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

class ThiefTest {
    
    private Thief thief = new Thief(2, 20, -20);
    
    
    
    @Test
    void apply() {
        
        TestUtils.assertThrowMandatoryField("game", () -> thief.apply(null, null) );
        
        Player p1 = new Player().setProfile( new ProfileEntity().setName("name1") ).setScore(0);
        Player p2 = new Player().setProfile( new ProfileEntity().setName("name2") ).setScore(0);
        Player p3 = new Player().setProfile( new ProfileEntity().setName("name3") ).setScore(0);
        
        Game game = new Game().setDuration(Duration.NORMAL).setPlayers( Arrays.asList(p1, p2, p3) );
        TestUtils.assertThrowMandatoryField("musicResult", () -> thief.apply(game, null) );
        
        
        thief.prepare(game);
        
        MusicResult musicResult = new MusicResult();
        musicResult.setTitleWinners( Collections.singletonList("name1") );
        musicResult.setAuthorWinners( Collections.singletonList("name1") );
        musicResult.setLosers( Arrays.asList("name2", "name2") );
        
        thief.apply(game, musicResult);
        Assertions.assertEquals( 2 * thief.getNbPointWon(), p1.getScore() );
        Assertions.assertEquals( 0 - (2 * thief.getNbPointWon()), p2.getScore() );
        Assertions.assertEquals( 0, p3.getScore() );
    }
    
    @Test
    void testToString() {
        Assertions.assertEquals("roundName=THIEF, nbMusics=2, nbPointWon=20, nbPointLoose=-20", thief.toString() );
    }
    
}