package com.myssteriion.blindtest.model.entity;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.GoodAnswer;
import com.myssteriion.blindtest.model.common.Theme;
import org.junit.Assert;
import org.junit.Test;

public class ProfileStatEntityTest extends AbstractTest {
    
    @Test
    public void incrementPlayedGames() {
        
        ProfileStatEntity profileStat = new ProfileStatEntity();
    
        Assert.assertNull( profileStat.getPlayedGames() );
        
        profileStat.incrementPlayedGames(Duration.NORMAL);
        Assert.assertEquals( Integer.valueOf(1), profileStat.getPlayedGames().get(Duration.NORMAL) );
    
        profileStat.incrementPlayedGames(Duration.NORMAL);
        Assert.assertEquals( Integer.valueOf(2), profileStat.getPlayedGames().get(Duration.NORMAL) );
    
        profileStat.incrementPlayedGames(Duration.LONG);
        Assert.assertEquals( Integer.valueOf(2), profileStat.getPlayedGames().get(Duration.NORMAL) );
        Assert.assertEquals( Integer.valueOf(1), profileStat.getPlayedGames().get(Duration.LONG) );
    }
    
    @Test
    public void addBestScoreIfBetter() {
    
        ProfileStatEntity profileStat = new ProfileStatEntity();
    
        Assert.assertNull( profileStat.getBestScores() );
        
        profileStat.addBestScoreIfBetter(Duration.NORMAL, 1000);
        Assert.assertEquals( Integer.valueOf(1000), profileStat.getBestScores().get(Duration.NORMAL) );
    
        profileStat.addBestScoreIfBetter(Duration.NORMAL, 500);
        Assert.assertEquals( Integer.valueOf(1000), profileStat.getBestScores().get(Duration.NORMAL) );
    
        profileStat.addBestScoreIfBetter(Duration.NORMAL, 1500);
        Assert.assertEquals( Integer.valueOf(1500), profileStat.getBestScores().get(Duration.NORMAL) );
    
        profileStat.addBestScoreIfBetter(Duration.LONG, 200);
        Assert.assertEquals( Integer.valueOf(1500), profileStat.getBestScores().get(Duration.NORMAL) );
        Assert.assertEquals( Integer.valueOf(200), profileStat.getBestScores().get(Duration.LONG) );
    
        profileStat.addBestScoreIfBetter(Duration.LONG, 400);
        Assert.assertEquals( Integer.valueOf(1500), profileStat.getBestScores().get(Duration.NORMAL) );
        Assert.assertEquals( Integer.valueOf(400), profileStat.getBestScores().get(Duration.LONG) );
    }
    
    @Test
    public void incrementWonGames() {
    
        ProfileStatEntity profileStat = new ProfileStatEntity();
    
        Assert.assertNull( profileStat.getWonGames() );
    
        profileStat.incrementWonGames(1);
        Assert.assertEquals( Integer.valueOf(1), profileStat.getWonGames().get("1") );
    
        profileStat.incrementWonGames(1);
        Assert.assertEquals( Integer.valueOf(2), profileStat.getWonGames().get("1") );
    
        profileStat.incrementWonGames(2);
        Assert.assertEquals( Integer.valueOf(2), profileStat.getWonGames().get("1") );
        Assert.assertEquals( Integer.valueOf(1), profileStat.getWonGames().get("2") );
    }
    
    @Test
    public void incrementListenedMusics() {
    
        ProfileStatEntity profileStat = new ProfileStatEntity();
    
        Assert.assertNull( profileStat.getListenedMusics() );
    
        profileStat.incrementListenedMusics(Theme.ANNEES_60);
        Assert.assertEquals( Integer.valueOf(1), profileStat.getListenedMusics().get(Theme.ANNEES_60) );
    
        profileStat.incrementListenedMusics(Theme.ANNEES_60);
        Assert.assertEquals( Integer.valueOf(2), profileStat.getListenedMusics().get(Theme.ANNEES_60) );
    
        profileStat.incrementListenedMusics(Theme.ANNEES_70);
        Assert.assertEquals( Integer.valueOf(2), profileStat.getListenedMusics().get(Theme.ANNEES_60) );
        Assert.assertEquals( Integer.valueOf(1), profileStat.getListenedMusics().get(Theme.ANNEES_70) );
    }
    
    @Test
    public void incrementFoundMusics() {
    
        ProfileStatEntity profileStat = new ProfileStatEntity();
    
        Assert.assertNull( profileStat.getFoundMusics() );
    
        profileStat.incrementFoundMusics(Theme.ANNEES_60, GoodAnswer.TITLE);
        Assert.assertEquals( Integer.valueOf(1), profileStat.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.TITLE) );
    
        profileStat.incrementFoundMusics(Theme.ANNEES_60, GoodAnswer.TITLE);
        Assert.assertEquals( Integer.valueOf(2), profileStat.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.TITLE) );
    
        profileStat.incrementFoundMusics(Theme.ANNEES_70, GoodAnswer.AUTHOR);
        Assert.assertEquals( Integer.valueOf(2), profileStat.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.TITLE) );
        Assert.assertEquals( Integer.valueOf(1), profileStat.getFoundMusics().get(Theme.ANNEES_70).get(GoodAnswer.AUTHOR) );
    }
    
}