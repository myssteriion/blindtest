package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.model.common.Effect;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.entity.MusicEntity;
import com.myssteriion.blindtest.model.music.ThemeInfo;
import com.myssteriion.blindtest.service.MusicService;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.utils.exception.NotFoundException;
import com.myssteriion.utils.rest.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Controller for music.
 */
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
     * @param themes  the themes filter (optional)
     * @param effects the effects filter (optional)
     * @return a music
     * @throws NotFoundException NotFound exception
     */
    @GetMapping(path = "/random")
    public ResponseEntity<MusicEntity> random(@RequestParam(value = Constant.THEMES, required = false) List<Theme> themes,
                                              @RequestParam(value = Constant.EFFECTS, required = false) List<Effect> effects)
            throws NotFoundException, IOException {
        
        MusicEntity music = musicService.random(themes, effects);
        if ( !music.getFlux().isFileExists() ) {
            musicService.refresh();
            music = musicService.random(themes, effects);
        }
        
        return RestUtils.create200(music);
    }
    
}
