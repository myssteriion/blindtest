package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.rest.ResponseBuilder;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.service.MusicService;
import com.myssteriion.blindtest.spotify.exception.SpotifyException;
import com.myssteriion.blindtest.tools.Constant;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin
@RestController
@RequestMapping(path = "musics")
public class MusicController {

	private MusicService musicService;



	@Autowired
	public MusicController(MusicService musicService) {
		this.musicService = musicService;
	}



	/**
	 * Randomly choose a music.
	 *
	 * @param themes themes filter (optional)
	 * @return a MusicDTO
	 * @throws NotFoundException NotFound exception
	 */
	@GetMapping(path = "/random")
	public ResponseEntity<MusicDTO> random(@RequestParam(value = Constant.THEMES, required = false) List<Theme> themes,
										   @RequestParam(value = Constant.ONLINE_MODE, defaultValue = Constant.ONLINE_MODE_DEFAULT_VALUE) boolean onlineMode)
			throws NotFoundException, IOException, SpotifyException {

		MusicDTO music = musicService.random(themes, onlineMode);
		if ( !onlineMode && !music.getFlux().isFileExists() ) {
			musicService.refresh();
			music = musicService.random(themes, onlineMode);
		}

		return ResponseBuilder.create200(music);
	}
	
}
