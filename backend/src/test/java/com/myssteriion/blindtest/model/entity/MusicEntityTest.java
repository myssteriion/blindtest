package com.myssteriion.blindtest.model.entity;

import com.myssteriion.blindtest.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MusicEntityTest extends AbstractTest {
    
    @Test
    void incrementPlayed() {
        
        MusicEntity music = new MusicEntity();
    
        Assertions.assertEquals( 0,  music.getPlayed() );
        
        music.incrementPlayed();
        Assertions.assertEquals( 1, music.getPlayed() );
    
        music.incrementPlayed();
        Assertions.assertEquals( 2, music.getPlayed() );
    }
    
}