package com.myssteriion.blindtest.model.game;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.GoodAnswer;
import com.myssteriion.blindtest.model.common.Theme;
import org.junit.Assert;
import org.junit.Test;

public class PlayerTest extends AbstractTest {
    
    @Test
    public void addScore() {
        
        Player player = new Player();
        
        Assert.assertEquals( 0, player.getScore() );
        
        player.addScore(100);
        Assert.assertEquals( 100, player.getScore() );
        
        player.addScore(75);
        Assert.assertEquals( 175, player.getScore() );
    }
    
    @Test
    public void incrementFoundMusics() {
        
        Player player = new Player();
        
        Assert.assertNull( player.getFoundMusics() );
        
        player.incrementFoundMusics(Theme.ANNEES_60, GoodAnswer.BOTH);
        Assert.assertEquals( Integer.valueOf(1), player.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.BOTH) );
        
        player.incrementFoundMusics(Theme.ANNEES_60, GoodAnswer.BOTH);
        Assert.assertEquals( Integer.valueOf(2), player.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.BOTH) );
        
        player.incrementFoundMusics(Theme.ANNEES_60, GoodAnswer.TITLE);
        Assert.assertEquals( Integer.valueOf(2), player.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.BOTH) );
        Assert.assertEquals( Integer.valueOf(1), player.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.TITLE) );
        
        player.incrementFoundMusics(Theme.ANNEES_70, GoodAnswer.AUTHOR);
        player.incrementFoundMusics(Theme.ANNEES_70, GoodAnswer.AUTHOR);
        Assert.assertEquals( Integer.valueOf(2), player.getFoundMusics().get(Theme.ANNEES_70).get(GoodAnswer.AUTHOR) );
    }
    
}