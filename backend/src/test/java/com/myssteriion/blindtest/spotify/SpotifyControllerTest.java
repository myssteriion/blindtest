package com.myssteriion.blindtest.spotify;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.dto.param.SpotifyParamDTO;
import com.myssteriion.utils.model.Empty;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class SpotifyControllerTest extends AbstractTest {
    
    @Mock
    private SpotifyService spotifyService;
    
    @InjectMocks
    private SpotifyController spotifyController;
    
    
    
    @Test
    public void connectionTest() throws SpotifyException {
        
        SpotifyParamDTO spotifyParam = new SpotifyParamDTO("id", "pwd", new HashMap<>());
        
        Mockito.doNothing().when(spotifyService).testSpotifyParamConnection(Mockito.any(SpotifyParamDTO.class));
        
        ResponseEntity<Empty> re = spotifyController.connectionTest(spotifyParam);
        Assert.assertEquals( HttpStatus.NO_CONTENT, re.getStatusCode() );
    }
    
}