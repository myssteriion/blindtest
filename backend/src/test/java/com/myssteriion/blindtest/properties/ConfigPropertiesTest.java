package com.myssteriion.blindtest.properties;

import com.myssteriion.blindtest.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ConfigPropertiesTest extends AbstractTest {
    
    @Autowired
    private ConfigProperties props;
    
    
    
    @Test
    void getAvatarsFolder() {
        Assertions.assertEquals( "avatars", props.getAvatarsFolderPath() );
    }
    
    @Test
    void getMusicsFolder() {
        Assertions.assertEquals( "musics", props.getMusicsFolderPath() );
    }
    
}