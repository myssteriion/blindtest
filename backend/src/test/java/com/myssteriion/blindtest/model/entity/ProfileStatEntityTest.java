package com.myssteriion.blindtest.model.entity;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.GoodAnswer;
import com.myssteriion.blindtest.model.common.Theme;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProfileStatEntityTest extends AbstractTest {
    
    @Test
    void incrementPlayedGames() {
        
        ProfileStatEntity profileStat = new ProfileStatEntity();
    
        Assertions.assertNull( profileStat.getPlayedGames() );
        
        profileStat.incrementPlayedGames(Duration.NORMAL);
        Assertions.assertEquals( Integer.valueOf(1), profileStat.getPlayedGames().get(Duration.NORMAL) );
    
        profileStat.incrementPlayedGames(Duration.NORMAL);
        Assertions.assertEquals( Integer.valueOf(2), profileStat.getPlayedGames().get(Duration.NORMAL) );
    
        profileStat.incrementPlayedGames(Duration.LONG);
        Assertions.assertEquals( Integer.valueOf(2), profileStat.getPlayedGames().get(Duration.NORMAL) );
        Assertions.assertEquals( Integer.valueOf(1), profileStat.getPlayedGames().get(Duration.LONG) );
    }
    
    @Test
    void addBestScoreIfBetter() {
    
        ProfileStatEntity profileStat = new ProfileStatEntity();
    
        Assertions.assertNull( profileStat.getBestScores() );
        
        profileStat.addBestScoreIfBetter(Duration.NORMAL, 1000);
        Assertions.assertEquals( Integer.valueOf(1000), profileStat.getBestScores().get(Duration.NORMAL) );
    
        profileStat.addBestScoreIfBetter(Duration.NORMAL, 500);
        Assertions.assertEquals( Integer.valueOf(1000), profileStat.getBestScores().get(Duration.NORMAL) );
    
        profileStat.addBestScoreIfBetter(Duration.NORMAL, 1500);
        Assertions.assertEquals( Integer.valueOf(1500), profileStat.getBestScores().get(Duration.NORMAL) );
    
        profileStat.addBestScoreIfBetter(Duration.LONG, 200);
        Assertions.assertEquals( Integer.valueOf(1500), profileStat.getBestScores().get(Duration.NORMAL) );
        Assertions.assertEquals( Integer.valueOf(200), profileStat.getBestScores().get(Duration.LONG) );
    
        profileStat.addBestScoreIfBetter(Duration.LONG, 400);
        Assertions.assertEquals( Integer.valueOf(1500), profileStat.getBestScores().get(Duration.NORMAL) );
        Assertions.assertEquals( Integer.valueOf(400), profileStat.getBestScores().get(Duration.LONG) );
    }
    
    @Test
    void incrementWonGames() {
    
        ProfileStatEntity profileStat = new ProfileStatEntity();
    
        Assertions.assertNull( profileStat.getWonGames() );
    
        profileStat.incrementWonGames(1);
        Assertions.assertEquals( Integer.valueOf(1), profileStat.getWonGames().get("1") );
    
        profileStat.incrementWonGames(1);
        Assertions.assertEquals( Integer.valueOf(2), profileStat.getWonGames().get("1") );
    
        profileStat.incrementWonGames(2);
        Assertions.assertEquals( Integer.valueOf(2), profileStat.getWonGames().get("1") );
        Assertions.assertEquals( Integer.valueOf(1), profileStat.getWonGames().get("2") );
    }
    
    @Test
    void incrementListenedMusics() {
    
        ProfileStatEntity profileStat = new ProfileStatEntity();
    
        Assertions.assertNull( profileStat.getListenedMusics() );
    
        profileStat.incrementListenedMusics(Theme.ANNEES_60);
        Assertions.assertEquals( Integer.valueOf(1), profileStat.getListenedMusics().get(Theme.ANNEES_60) );
    
        profileStat.incrementListenedMusics(Theme.ANNEES_60);
        Assertions.assertEquals( Integer.valueOf(2), profileStat.getListenedMusics().get(Theme.ANNEES_60) );
    
        profileStat.incrementListenedMusics(Theme.ANNEES_70);
        Assertions.assertEquals( Integer.valueOf(2), profileStat.getListenedMusics().get(Theme.ANNEES_60) );
        Assertions.assertEquals( Integer.valueOf(1), profileStat.getListenedMusics().get(Theme.ANNEES_70) );
    }
    
    @Test
    void incrementFoundMusics() {
    
        ProfileStatEntity profileStat = new ProfileStatEntity();
    
        Assertions.assertNull( profileStat.getFoundMusics() );
    
        profileStat.incrementFoundMusics(Theme.ANNEES_60, GoodAnswer.TITLE);
        Assertions.assertEquals( Integer.valueOf(1), profileStat.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.TITLE) );
    
        profileStat.incrementFoundMusics(Theme.ANNEES_60, GoodAnswer.TITLE);
        Assertions.assertEquals( Integer.valueOf(2), profileStat.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.TITLE) );
    
        profileStat.incrementFoundMusics(Theme.ANNEES_70, GoodAnswer.AUTHOR);
        Assertions.assertEquals( Integer.valueOf(2), profileStat.getFoundMusics().get(Theme.ANNEES_60).get(GoodAnswer.TITLE) );
        Assertions.assertEquals( Integer.valueOf(1), profileStat.getFoundMusics().get(Theme.ANNEES_70).get(GoodAnswer.AUTHOR) );
    }
    
}