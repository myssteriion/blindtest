package com.myssteriion.blindtest;

import com.myssteriion.blindtest.properties.ConfigProperties;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public abstract class AbstractTest {
    
    protected ConfigProperties configProperties;
    
    
    
    public AbstractTest() {
        configProperties = Mockito.mock(ConfigProperties.class);
        Mockito.doReturn("avatars").when(configProperties).getAvatarsFolderPath();
        Mockito.doReturn("musics").when(configProperties).getMusicsFolderPath();
    }
    
}
