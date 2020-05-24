package com.myssteriion.blindtest.properties;

import com.myssteriion.blindtest.AbstractTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ConfigPropertiesTest extends AbstractTest {
    
    @Autowired
    private ConfigProperties props;
    
    
    
    @Test
    public void getMaxPlayers() {
        Assert.assertEquals( new Integer(4), props.getMaxPlayers() );
    }
    
    @Test
    public void getMinPlayers() {
        Assert.assertEquals( new Integer(2), props.getMinPlayers() );
    }
    
    @Test
    public void getAvatarsFolder() {
        Assert.assertEquals( "/avatarsFolder", props.getAvatarsFolderPath() );
    }
    
    @Test
    public void getMusicsFolder() {
        Assert.assertEquals( "/musicsFolder", props.getMusicsFolderPath() );
    }
    
}