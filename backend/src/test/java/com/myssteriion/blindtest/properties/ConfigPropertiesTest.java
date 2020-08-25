package com.myssteriion.blindtest.properties;

import com.myssteriion.blindtest.AbstractTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ConfigPropertiesTest extends AbstractTest {
    
    @Autowired
    private ConfigProperties props;
    
    
    
    @Test
    public void getAvatarsFolder() {
        Assert.assertEquals( "avatars", props.getAvatarsFolderPath() );
    }
    
    @Test
    public void getMusicsFolder() {
        Assert.assertEquals( "musics", props.getMusicsFolderPath() );
    }
    
}