package com.myssteriion.blindtest.model.round.impl;

import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.entity.ProfileEntity;
import com.myssteriion.blindtest.model.game.Game;
import com.myssteriion.blindtest.model.game.MusicResult;
import com.myssteriion.blindtest.model.game.Player;
import com.myssteriion.utils.test.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

class ChoiceTest {
    
    private Choice choice = new Choice(2, 20, 10, -10);
    
    
    
    @Test
    void prepareAndGetOrder() {
        
        TestUtils.assertThrowMandatoryField("game", () -> choice.prepare(null) );
        
        Player p1 = new Player().setProfile( new ProfileEntity().setName("name1") ).setScore(0);
        Player p2 = new Player().setProfile( new ProfileEntity().setName("name2") ).setScore(0);
        
        Game game = new Game().setDuration(Duration.NORMAL).setPlayers( Arrays.asList(p1, p2) );
        
        Assertions.assertEquals( new ArrayList<>(), choice.getOrder() );
        choice.prepare(game);
        Assertions.assertEquals( choice.getNbMusics() - 1, choice.getOrder().size() );
        
        Assertions.assertTrue( p1.isTurnToChoose() ^ p2.isTurnToChoose() );
    }
    
    @Test
    void apply() {
        
        TestUtils.assertThrowMandatoryField("game", () -> choice.apply(null, null) );
        
        Player p1 = new Player().setProfile( new ProfileEntity().setName("name1") ).setScore(0);
        Player p2 = new Player().setProfile( new ProfileEntity().setName("name2") ).setScore(0);
        Player p3 = new Player().setProfile( new ProfileEntity().setName("name3") ).setScore(0);
        
        Game game = new Game().setDuration(Duration.NORMAL).setPlayers( Arrays.asList(p1, p2, p3) );
        TestUtils.assertThrowMandatoryField("musicResult", () -> choice.apply(game, null) );
        
        choice.prepare(game);
        
        
        p1.setTurnToChoose(true);
        p2.setTurnToChoose(true);
        p3.setTurnToChoose(true);
        
        MusicResult musicResult = new MusicResult();
        musicResult.setTitleWinners( Arrays.asList("name1", "name2") );
        musicResult.setAuthorWinners( Arrays.asList("name1", "name3") );
        
        choice.apply(game, musicResult);
        Assertions.assertEquals(2 * (choice.getNbPointWon() + choice.getNbPointBonus()), p1.getScore() );
        Assertions.assertEquals( choice.getNbPointWon() + choice.getNbPointBonus(), p2.getScore() );
        Assertions.assertEquals( choice.getNbPointWon() + choice.getNbPointBonus(), p3.getScore() );
        Assertions.assertTrue( p1.isTurnToChoose() ^ p2.isTurnToChoose() ^ p3.isTurnToChoose() );
        
        game.setNbMusicsPlayed( choice.getNbMusics() - 1 );
        game.setNbMusicsPlayedInRound( choice.getNbMusics() - 1 );
        
        choice.apply(game, musicResult);
        Assertions.assertFalse( p1.isTurnToChoose() );
        Assertions.assertFalse( p2.isTurnToChoose() );
        Assertions.assertFalse( p3.isTurnToChoose() );
    }
    
    @Test
    void testToString() {
        Assertions.assertEquals("roundName=CHOICE, nbMusics=2, nbPointWon=20, nbPointBonus=10, nbPointMalus=-10, order=[]", choice.toString() );
    }
    
}