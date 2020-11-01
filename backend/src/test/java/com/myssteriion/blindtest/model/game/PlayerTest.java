package com.myssteriion.blindtest.model.game;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.GoodAnswer;
import com.myssteriion.blindtest.model.common.Theme;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PlayerTest extends AbstractTest {
    
    @Test
    void addScore() {
        
        Player player = new Player();
        
        Assertions.assertEquals( 0, player.getScore() );
        
        player.addScore(100);
        Assertions.assertEquals( 100, player.getScore() );
        
        player.addScore(75);
        Assertions.assertEquals( 175, player.getScore() );
    }
    
    @Test
    void incrementFoundMusics() {
        
        Player player = new Player();
        
        Assertions.assertNull( player.getFoundMusics() );
        
        player.incrementFoundMusics(Theme.ANNEES_60, GoodAnswer.BOTH);
        Assertions.assertEquals( Integer.valueOf(1), player.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.BOTH) );
        
        player.incrementFoundMusics(Theme.ANNEES_60, GoodAnswer.BOTH);
        Assertions.assertEquals( Integer.valueOf(2), player.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.BOTH) );
        
        player.incrementFoundMusics(Theme.ANNEES_60, GoodAnswer.TITLE);
        Assertions.assertEquals( Integer.valueOf(2), player.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.BOTH) );
        Assertions.assertEquals( Integer.valueOf(1), player.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.TITLE) );
        
        player.incrementFoundMusics(Theme.ANNEES_70, GoodAnswer.AUTHOR);
        player.incrementFoundMusics(Theme.ANNEES_70, GoodAnswer.AUTHOR);
        Assertions.assertEquals( Integer.valueOf(2), player.getFoundMusics().get(Theme.ANNEES_70).get(GoodAnswer.AUTHOR) );
    }
    
}