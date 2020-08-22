package com.myssteriion.blindtest.model.entity;

import com.myssteriion.blindtest.AbstractTest;
import org.junit.Assert;
import org.junit.Test;

public class MusicEntityTest extends AbstractTest {
    
    @Test
    public void incrementPlayed() {
        
        MusicEntity music = new MusicEntity();
    
        Assert.assertEquals( 0,  music.getPlayed() );
        
        music.incrementPlayed();
        Assert.assertEquals( 1, music.getPlayed() );
    
        music.incrementPlayed();
        Assert.assertEquals( 2, music.getPlayed() );
    }
    
}