package com.myssteriion.blindtest.controller.param;

import com.myssteriion.blindtest.model.entity.param.SpotifyParamEntity;
import com.myssteriion.blindtest.service.param.SpotifyParamService;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.NotFoundException;
import com.myssteriion.utils.rest.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for SpotifyParam.
 */
@RestController
@RequestMapping(path = "params/spotify-param")
public class SpotifyParamController {
    
    private SpotifyParamService spotifyParamService;
    
    
    
    /**
     * Instantiates a new SpotifyParam controller.
     *
     * @param spotifyParamService the SpotifyParam service
     */
    @Autowired
    public SpotifyParamController(SpotifyParamService spotifyParamService) {
        this.spotifyParamService = spotifyParamService;
    }
    
    
    
    /**
     * Update the only SpotifyParam.
     *
     * @param spotifyParam the 'new' SpotifyParam
     * @return the spotifyParam modified
     * @throws NotFoundException the not found exception
     * @throws ConflictException the conflict exception
     */
    @PutMapping
    public ResponseEntity<SpotifyParamEntity> update(@RequestBody SpotifyParamEntity spotifyParam) throws NotFoundException, ConflictException {
        return RestUtils.create200( spotifyParamService.update(spotifyParam) );
    }
    
    /**
     * Get the only SpotifyParam.
     *
     * @return the SpotifyParam modified
     */
    @GetMapping
    public ResponseEntity<SpotifyParamEntity> find() {
        return RestUtils.create200( spotifyParamService.find() );
    }
    
}
