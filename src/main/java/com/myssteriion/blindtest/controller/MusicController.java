package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.music.ThemeInfo;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.service.MusicService;
import com.myssteriion.blindtest.spotify.SpotifyException;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.utils.rest.RestUtils;
import com.myssteriion.utils.rest.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Controller for MusicDTO.
 */
@CrossOrigin( origins = {"http://localhost:8085"} )
@RestController
@RequestMapping(path = "musics")
public class MusicController {

    private MusicService musicService;



    /**
     * Instantiates a new Music controller.
     *
     * @param musicService the music service
     */
    @Autowired
    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }



    /**
     * Get nb musics by themes by connection mode.
     *
     * @return the themesInfo
     */
    @GetMapping(path = "/compute-themes-info")
    public ResponseEntity< Page<ThemeInfo> > computeThemesInfo() {

        musicService.refresh();

        List<ThemeInfo> themesInfo = musicService.computeThemesInfo();
        return RestUtils.create200( new PageImpl<>(themesInfo) );
    }

    /**
     * Randomly choose a music.
     *
     * @param sameProbability if themes probability are same
     * @param themes     	  the themes filter (optional)
     * @param effects     	  the effects filter (optional)
     * @param connectionMode  the online mode
     * @return a MusicDTO
     * @throws NotFoundException NotFound exception
     */
    @GetMapping(path = "/random")
    public ResponseEntity<MusicDTO> random(@RequestParam(value = Constant.SAME_PROBABILITY, required = false) boolean sameProbability,
                                           @RequestParam(value = Constant.THEMES, required = false) List<Theme> themes,
                                           @RequestParam(value = Constant.EFFECTS, required = false) List<Effect> effects,
                                           @RequestParam(value = Constant.CONNECTION_MODE) ConnectionMode connectionMode)
            throws NotFoundException, IOException, SpotifyException {

        MusicDTO music = musicService.random(sameProbability, themes, effects, connectionMode);
        if ( connectionMode == ConnectionMode.OFFLINE && !music.getFlux().isFileExists() ) {
            musicService.refresh();
            music = musicService.random(sameProbability, themes, effects, connectionMode);
        }

        return RestUtils.create200(music);
    }

}
