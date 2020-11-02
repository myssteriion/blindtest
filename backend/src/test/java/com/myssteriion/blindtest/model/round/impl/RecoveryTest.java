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

class RecoveryTest {
    
    private Recovery recovery = new Recovery(2, 20);
    
    
    
    @Test
    void apply() {
        
        TestUtils.assertThrowMandatoryField("game", () -> recovery.apply(null, null) );
        
        Player p1 = new Player().setProfile(new ProfileEntity().setName("name1")).setScore(0).setRank(1);
        Player p2 = new Player().setProfile(new ProfileEntity().setName("name2")).setScore(0).setRank(2);
        Player p3 = new Player().setProfile(new ProfileEntity().setName("name3")).setScore(0).setRank(3);
        
        Game game = new Game().setDuration(Duration.NORMAL).setPlayers(Arrays.asList(p1, p2, p3));
        TestUtils.assertThrowMandatoryField("musicResult", () -> recovery.apply(game, null) );
        
        
        recovery.prepare(game);
        
        MusicResult musicResult = new MusicResult();
        musicResult.setTitleWinners( Arrays.asList("name1", "name2", "name3") );
        musicResult.setAuthorWinners(Collections.singletonList("name1"));
        
        recovery.apply(game, musicResult);
        Assertions.assertEquals( 2 * recovery.getNbPointWon(), p1.getScore() );
        Assertions.assertEquals( 2 * recovery.getNbPointWon(), p2.getScore() );
        Assertions.assertEquals( 3 * recovery.getNbPointWon(), p3.getScore() );
    }
    
}