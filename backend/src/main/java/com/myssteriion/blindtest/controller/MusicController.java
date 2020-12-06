package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.model.entity.MusicEntity;
import com.myssteriion.blindtest.model.music.MusicFilter;
import com.myssteriion.blindtest.model.music.ThemeInfo;
import com.myssteriion.blindtest.service.MusicService;
import com.myssteriion.utils.exception.NotFoundException;
import com.myssteriion.utils.model.impl.Empty;
import com.myssteriion.utils.rest.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
     * Refresh musics.
     *
     * @return empty response
     */
    @PostMapping(path = "/refresh")
    public ResponseEntity<Empty> refresh() {
        musicService.init();
        return RestUtils.create204();
    }
    
    /**
     * Get nb musics by themes by connection mode.
     *
     * @return the themesInfo
     */
    @GetMapping(path = "/compute-themes-info")
    public ResponseEntity< Page<ThemeInfo> > computeThemesInfo() {
        return RestUtils.create200( new PageImpl<>(musicService.computeThemesInfo()) );
    }
    
    /**
     * Randomly choose a music.
     *
     * @param musicFilter the musicFilter)
     * @return a music
     * @throws NotFoundException NotFound exception
     */
    @PostMapping(path = "/random")
    public ResponseEntity<MusicEntity> random(@RequestBody MusicFilter musicFilter) throws NotFoundException, IOException {
        return RestUtils.create200(  musicService.random(musicFilter) );
    }
    
}
