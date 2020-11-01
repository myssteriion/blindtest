package com.myssteriion.blindtest.model.game;

import com.myssteriion.blindtest.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

class MusicResultTest extends AbstractTest {
    
    @Test
    void isAuthorWinner() {
        
        MusicResult musicResult = new MusicResult();
        String name = "name";
        String name2 = "name2";
        
        Assertions.assertNull( musicResult.getAuthorWinners() );
        Assertions.assertFalse( musicResult.isAuthorWinner(name) );
        
        musicResult.setAuthorWinners( Collections.singletonList(name) );
        Assertions.assertTrue( musicResult.isAuthorWinner(name) );
        Assertions.assertFalse( musicResult.isAuthorWinner(name2) );
        
        musicResult.setAuthorWinners( Arrays.asList(name, name2) );
        Assertions.assertTrue( musicResult.isAuthorWinner(name) );
        Assertions.assertTrue( musicResult.isAuthorWinner(name2) );
    }
    
    @Test
    void isTitleWinner() {
        
        MusicResult musicResult = new MusicResult();
        String name = "name";
        String name2 = "name2";
        
        Assertions.assertNull( musicResult.getTitleWinners() );
        Assertions.assertFalse( musicResult.isTitleWinner(name) );
        
        musicResult.setTitleWinners( Collections.singletonList(name) );
        Assertions.assertTrue( musicResult.isTitleWinner(name) );
        Assertions.assertFalse( musicResult.isTitleWinner(name2) );
        
        musicResult.setTitleWinners( Arrays.asList(name, name2) );
        Assertions.assertTrue( musicResult.isTitleWinner(name) );
        Assertions.assertTrue( musicResult.isTitleWinner(name2) );
    }
    
    @Test
    void isAuthorAndTitleWinner() {
        
        MusicResult musicResult = new MusicResult();
        String name = "name";
        String name2 = "name2";
        
        Assertions.assertNull( musicResult.getAuthorWinners() );
        Assertions.assertFalse( musicResult.isAuthorWinner(name) );
        Assertions.assertNull( musicResult.getTitleWinners() );
        Assertions.assertFalse( musicResult.isTitleWinner(name) );
        Assertions.assertFalse( musicResult.isAuthorAndTitleWinner(name) );
        
        musicResult.setAuthorWinners( Collections.singletonList(name) );
        Assertions.assertTrue( musicResult.isAuthorWinner(name) );
        Assertions.assertFalse( musicResult.isAuthorWinner(name2) );
        musicResult.setTitleWinners( Collections.singletonList(name) );
        Assertions.assertTrue( musicResult.isTitleWinner(name) );
        Assertions.assertFalse( musicResult.isTitleWinner(name2) );
        Assertions.assertTrue( musicResult.isAuthorAndTitleWinner(name) );
        Assertions.assertFalse( musicResult.isAuthorAndTitleWinner(name2) );
        
        musicResult.setAuthorWinners( Arrays.asList(name, name2) );
        Assertions.assertTrue( musicResult.isAuthorWinner(name) );
        Assertions.assertTrue( musicResult.isAuthorWinner(name2) );
        musicResult.setTitleWinners( Arrays.asList(name, name2) );
        Assertions.assertTrue( musicResult.isTitleWinner(name) );
        Assertions.assertTrue( musicResult.isTitleWinner(name2) );
        Assertions.assertTrue( musicResult.isAuthorAndTitleWinner(name) );
        Assertions.assertTrue( musicResult.isAuthorAndTitleWinner(name2) );
    }
    
    @Test
    void isLoser() {
        
        MusicResult musicResult = new MusicResult();
        String name = "name";
        String name2 = "name2";
        
        Assertions.assertNull( musicResult.getLosers() );
        Assertions.assertFalse( musicResult.isLoser(name) );
        
        musicResult.setLosers( Collections.singletonList(name) );
        Assertions.assertTrue( musicResult.isLoser(name) );
        Assertions.assertFalse( musicResult.isLoser(name2) );
        
        musicResult.setLosers( Arrays.asList(name, name2) );
        Assertions.assertTrue( musicResult.isLoser(name) );
        Assertions.assertTrue( musicResult.isLoser(name2) );
    }
    
    @Test
    void hadPenalty() {
        
        MusicResult musicResult = new MusicResult();
        String name = "name";
        String name2 = "name2";
        
        Assertions.assertNull( musicResult.getPenalties() );
        Assertions.assertFalse( musicResult.hadPenalty(name) );
        
        musicResult.setPenalties( Collections.singletonList(name) );
        Assertions.assertTrue( musicResult.hadPenalty(name) );
        Assertions.assertFalse( musicResult.hadPenalty(name2) );
        
        musicResult.setPenalties( Arrays.asList(name, name2) );
        Assertions.assertTrue( musicResult.hadPenalty(name) );
        Assertions.assertTrue( musicResult.hadPenalty(name2) );
    }
    
}