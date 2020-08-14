package com.myssteriion.blindtest.spotify;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.dto.param.SpotifyParamDTO;
import com.myssteriion.blindtest.service.param.SpotifyParamService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashMap;

public class SpotifyServiceTest extends AbstractTest {
    
    @Mock
    private SpotifyParamService spotifyParamService;
    
    @InjectMocks
    private SpotifyService spotifyService;
    
    
    
    @Before
    public void before() {
        
        SpotifyParamDTO sParam = new SpotifyParamDTO()
                .setClientId("id")
                .setClientSecret("secret")
                .setPlaylistIds( new HashMap<>() );
        
        Mockito.when( spotifyParamService.find() ).thenReturn(sParam);
    }
    
    
    
    @Test
    public void isConnected() {
        Assert.assertFalse( spotifyService.isConnected() );
    }
    
    @Test
    public void testConnection() {
        
        try {
            spotifyService.testConnection();
            Assert.fail("Doit lever SpotifyException une car le mock throw.");
        }
        catch (SpotifyException e) {
            Assert.assertEquals( "Can't create spotify connection.", e.getMessage() );
        }
    }
    
}