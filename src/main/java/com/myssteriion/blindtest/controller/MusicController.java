package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.model.base.Empty;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.rest.ResponseBuilder;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Controller for MusicDTO.
 */
@CrossOrigin
@RestController
@RequestMapping(path = "musics")
public class MusicController {

	private final MusicService musicService;

	@Autowired
	public MusicController(MusicService musicService) {
		this.musicService = musicService;
	}



	/**
	 * Scan music folder and refresh the DB.
	 *
	 * @return nothing
	 */
	@GetMapping(path = "/refresh")
	public ResponseEntity<Empty> refresh() {
		
		musicService.refresh();
		return ResponseBuilder.create204();
	}

	/**
	 * Randomly choose a music.
	 *
	 * @return a MusicDTO
	 * @throws NotFoundException NotFound exception
	 */
	@GetMapping(path = "/random")
	public ResponseEntity<MusicDTO> random() throws NotFoundException, IOException {
		return ResponseBuilder.create200( musicService.random() );
	}
	
}
