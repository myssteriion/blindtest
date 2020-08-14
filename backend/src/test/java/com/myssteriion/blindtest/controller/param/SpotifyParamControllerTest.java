package com.myssteriion.blindtest.controller.param;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.dto.param.SpotifyParamDTO;
import com.myssteriion.blindtest.service.param.SpotifyParamService;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class SpotifyParamControllerTest extends AbstractTest {
    
    @Mock
    private SpotifyParamService spotifyParamService;
    
    @InjectMocks
    private SpotifyParamController spotifyParamController;
    
    
    
    @Test
    public void update() throws NotFoundException, ConflictException {
        
        SpotifyParamDTO spotifyParam = new SpotifyParamDTO("id", "pwd", new HashMap<>());
        Mockito.when(spotifyParamService.update(Mockito.any(SpotifyParamDTO.class))).thenReturn(spotifyParam);
    
        ResponseEntity<SpotifyParamDTO> actual = spotifyParamController.update(spotifyParam);
        Assert.assertEquals( HttpStatus.OK, actual.getStatusCode() );
        Assert.assertEquals( "id", actual.getBody().getClientId() );
        Assert.assertEquals( "pwd", actual.getBody().getClientSecret() );
    }
    
    @Test
    public void find() {
    
        SpotifyParamDTO spotifyParam = new SpotifyParamDTO("id", "pwd", new HashMap<>());
        Mockito.when(spotifyParamService.find()).thenReturn(spotifyParam);
    
        ResponseEntity<SpotifyParamDTO> actual = spotifyParamController.find();
        Assert.assertEquals( HttpStatus.OK, actual.getStatusCode() );
        Assert.assertEquals( "id", actual.getBody().getClientId() );
        Assert.assertEquals( "pwd", actual.getBody().getClientSecret() );
    }
    
}