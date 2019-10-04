package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.base.Empty;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.rest.ResponseBuilder;
import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for MusicDTO.
 */
@CrossOrigin
@RestController
@RequestMapping(path = "musics")
public class MusicController {

	@Autowired
	private MusicService musicService;



	/**
	 * Scan music folder and refresh the bdd.
	 *
	 * @return nothing
	 */
	@GetMapping(path = "/refresh")
	public ResponseEntity<Empty> refresh() throws DaoException, ConflictException {
		
		musicService.refresh();
		return ResponseBuilder.create204();
	}

	/**
	 * Randomly choose a music.
	 *
	 * @return a MusicDTO
	 * @throws DaoException
	 * @throws NotFoundException
	 */
	@GetMapping(path = "/random")
	public ResponseEntity<MusicDTO> random() throws DaoException, NotFoundException {
		return ResponseBuilder.create200( musicService.random() );
	}
	
}
