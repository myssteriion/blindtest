package com.myssteriion.blindtest.model.game;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.GoodAnswer;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.utils.test.TestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class PlayerTest extends AbstractTest {
    
    @Test
    public void constructor() {
        
        String name = "name";
        ProfileDTO profile = new ProfileDTO(name);
        
        
        try {
            new Player(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'profile' est obligatoire."), e);
        }
        
        
        Player player = new Player(profile);
        Assert.assertEquals( profile, player.getProfile() );
        Assert.assertEquals( 0, player.getScore() );
        Assert.assertEquals( 1, player.getRank() );
        Assert.assertFalse( player.isLast() );
    }
    
    @Test
    public void getterSetter() {
        
        String name = "name";
        ProfileDTO profile = new ProfileDTO(name);
        
        Player player = new Player(profile);
        Assert.assertEquals( profile, player.getProfile() );
        Assert.assertEquals( 0, player.getScore() );
        Assert.assertEquals( 1, player.getRank() );
        Assert.assertFalse( player.isLast() );
        
        player.setRank(7);
        player.setLast(true);
        Assert.assertEquals( 7, player.getRank() );
        Assert.assertTrue( player.isLast() );
    }
    
    @Test
    public void toStringAndEquals() {
        
        String name = "name";
        ProfileDTO profile = new ProfileDTO(name);
        Player playerUn = new Player(profile);
        
        Assert.assertEquals( "profile={" + profile + "}, score=0, rank=1, last=false, turnToChoose=false, teamNumber=-1, foundMusics={}", playerUn.toString() );
        
        Player playerUnIso = new Player(profile);
        Player playerDeux = new Player(new ProfileDTO(name + "1"));
        
        
        Assert.assertNotEquals(playerUn, null);
        Assert.assertNotEquals(playerUn, "bad class");
        Assert.assertEquals(playerUn, playerUn);
        Assert.assertEquals(playerUn, playerUnIso);
        Assert.assertNotEquals(playerUn, playerDeux);
        
        
        Assert.assertEquals(playerUn.hashCode(), playerUn.hashCode());
        Assert.assertEquals(playerUn.hashCode(), playerUnIso.hashCode());
        Assert.assertNotEquals(playerUn.hashCode(), playerDeux.hashCode());
    }
    
    @Test
    public void incrementFoundMusics() {
        
        String name = "name";
        ProfileDTO profile = new ProfileDTO(name);
        Player player = new Player(profile);
        
        player.incrementFoundMusics(Theme.ANNEES_60, GoodAnswer.TITLE);
        Map<Theme, Map<GoodAnswer, Integer>> foundMusics = player.getFoundMusics();
        Assert.assertEquals( new Integer(1), foundMusics.get(Theme.ANNEES_60).get(GoodAnswer.TITLE) );
        
        player.incrementFoundMusics(Theme.ANNEES_60, GoodAnswer.TITLE);
        foundMusics = player.getFoundMusics();
        Assert.assertEquals( new Integer(2), foundMusics.get(Theme.ANNEES_60).get(GoodAnswer.TITLE) );
    }
    
}