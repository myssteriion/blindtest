package com.myssteriion.blindtest.spotify;

import com.myssteriion.blindtest.model.dto.param.SpotifyParamDTO;
import com.myssteriion.utils.model.Empty;
import com.myssteriion.utils.rest.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for Spotify.
 */
@RestController
@RequestMapping(path = "spotify")
public class SpotifyController {
    
    private SpotifyService spotifyService;
    
    
    
    /**
     * Instantiates a new Spotify controller.
     *
     * @param spotifyService the Spotify service
     */
    @Autowired
    public SpotifyController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }
    
    
    
    /**
     * Test the Spotify connection.
     *
     * @param spotifyParamDTO the 'new' SpotifyParamDTO
     * @return the pageable of avatars
     * @throws SpotifyException the spotify exception
     */
    @PostMapping(path = "connection-test")
    public ResponseEntity<Empty> connectionTest(@RequestBody SpotifyParamDTO spotifyParamDTO) throws SpotifyException {
        
        spotifyService.testSpotifyParamConnection(spotifyParamDTO);
        return RestUtils.create204();
    }
    
}
