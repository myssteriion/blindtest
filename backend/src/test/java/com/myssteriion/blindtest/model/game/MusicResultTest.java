package com.myssteriion.blindtest.model.game;

import com.myssteriion.blindtest.AbstractTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class MusicResultTest extends AbstractTest {
    
    @Test
    public void isAuthorWinner() {
        
        MusicResult musicResult = new MusicResult();
        String name = "name";
        String name2 = "name2";
        
        Assert.assertNull( musicResult.getAuthorWinners() );
        Assert.assertFalse( musicResult.isAuthorWinner(name) );
        
        musicResult.setAuthorWinners( Collections.singletonList(name) );
        Assert.assertTrue( musicResult.isAuthorWinner(name) );
        Assert.assertFalse( musicResult.isAuthorWinner(name2) );
        
        musicResult.setAuthorWinners( Arrays.asList(name, name2) );
        Assert.assertTrue( musicResult.isAuthorWinner(name) );
        Assert.assertTrue( musicResult.isAuthorWinner(name2) );
    }
    
    @Test
    public void isTitleWinner() {
        
        MusicResult musicResult = new MusicResult();
        String name = "name";
        String name2 = "name2";
        
        Assert.assertNull( musicResult.getTitleWinners() );
        Assert.assertFalse( musicResult.isTitleWinner(name) );
        
        musicResult.setTitleWinners( Collections.singletonList(name) );
        Assert.assertTrue( musicResult.isTitleWinner(name) );
        Assert.assertFalse( musicResult.isTitleWinner(name2) );
        
        musicResult.setTitleWinners( Arrays.asList(name, name2) );
        Assert.assertTrue( musicResult.isTitleWinner(name) );
        Assert.assertTrue( musicResult.isTitleWinner(name2) );
    }
    
    @Test
    public void isAuthorAndTitleWinner() {
        
        MusicResult musicResult = new MusicResult();
        String name = "name";
        String name2 = "name2";
        
        Assert.assertNull( musicResult.getAuthorWinners() );
        Assert.assertFalse( musicResult.isAuthorWinner(name) );
        Assert.assertNull( musicResult.getTitleWinners() );
        Assert.assertFalse( musicResult.isTitleWinner(name) );
        Assert.assertFalse( musicResult.isAuthorAndTitleWinner(name) );
        
        musicResult.setAuthorWinners( Collections.singletonList(name) );
        Assert.assertTrue( musicResult.isAuthorWinner(name) );
        Assert.assertFalse( musicResult.isAuthorWinner(name2) );
        musicResult.setTitleWinners( Collections.singletonList(name) );
        Assert.assertTrue( musicResult.isTitleWinner(name) );
        Assert.assertFalse( musicResult.isTitleWinner(name2) );
        Assert.assertTrue( musicResult.isAuthorAndTitleWinner(name) );
        Assert.assertFalse( musicResult.isAuthorAndTitleWinner(name2) );
        
        musicResult.setAuthorWinners( Arrays.asList(name, name2) );
        Assert.assertTrue( musicResult.isAuthorWinner(name) );
        Assert.assertTrue( musicResult.isAuthorWinner(name2) );
        musicResult.setTitleWinners( Arrays.asList(name, name2) );
        Assert.assertTrue( musicResult.isTitleWinner(name) );
        Assert.assertTrue( musicResult.isTitleWinner(name2) );
        Assert.assertTrue( musicResult.isAuthorAndTitleWinner(name) );
        Assert.assertTrue( musicResult.isAuthorAndTitleWinner(name2) );
    }
    
    @Test
    public void isLoser() {
        
        MusicResult musicResult = new MusicResult();
        String name = "name";
        String name2 = "name2";
        
        Assert.assertNull( musicResult.getLosers() );
        Assert.assertFalse( musicResult.isLoser(name) );
        
        musicResult.setLosers( Collections.singletonList(name) );
        Assert.assertTrue( musicResult.isLoser(name) );
        Assert.assertFalse( musicResult.isLoser(name2) );
        
        musicResult.setLosers( Arrays.asList(name, name2) );
        Assert.assertTrue( musicResult.isLoser(name) );
        Assert.assertTrue( musicResult.isLoser(name2) );
    }
    
    @Test
    public void hadPenalty() {
        
        MusicResult musicResult = new MusicResult();
        String name = "name";
        String name2 = "name2";
        
        Assert.assertNull( musicResult.getPenalties() );
        Assert.assertFalse( musicResult.hadPenalty(name) );
        
        musicResult.setPenalties( Collections.singletonList(name) );
        Assert.assertTrue( musicResult.hadPenalty(name) );
        Assert.assertFalse( musicResult.hadPenalty(name2) );
        
        musicResult.setPenalties( Arrays.asList(name, name2) );
        Assert.assertTrue( musicResult.hadPenalty(name) );
        Assert.assertTrue( musicResult.hadPenalty(name2) );
    }
    
}