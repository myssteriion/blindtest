package com.myssteriion.blindtest.controller.param;

import com.myssteriion.blindtest.model.dto.param.SpotifyParamDTO;
import com.myssteriion.blindtest.service.param.SpotifyParamService;
import com.myssteriion.utils.rest.RestUtils;
import com.myssteriion.utils.rest.exception.ConflictException;
import com.myssteriion.utils.rest.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for SpotifyParamDTO.
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
     * Update the only SpotifyParamDTO.
     *
     * @param spotifyParamDTO the 'new' SpotifyParamDTO
     * @return the SpotifyParamDTO modified
     * @throws NotFoundException the not found exception
     * @throws ConflictException the conflict exception
     */
    @PutMapping
    public ResponseEntity<SpotifyParamDTO> update(@RequestBody SpotifyParamDTO spotifyParamDTO) throws NotFoundException, ConflictException {
        return RestUtils.create200( spotifyParamService.update(spotifyParamDTO) );
    }

    /**
     * Get the only SpotifyParamDTO.
     *
     * @return the SpotifyParamDTO modified
     */
    @GetMapping
    public ResponseEntity<SpotifyParamDTO> find() {
        return RestUtils.create200( spotifyParamService.find() );
    }

}
