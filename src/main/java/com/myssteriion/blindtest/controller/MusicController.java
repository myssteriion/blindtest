package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.model.base.Empty;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.rest.ResponseBuilder;
import com.myssteriion.blindtest.service.MusicService;

@CrossOrigin
@RestController
@RequestMapping(
	path = "musics"
)
public class MusicController {

	@Autowired
	private MusicService musicService;
	
	
	
	@RequestMapping(
		method = RequestMethod.GET,
		path = "/refresh"
	)
	public ResponseEntity<Empty> refresh() throws DaoException, ConflictException {
		
		musicService.refresh();
		return ResponseBuilder.create204();
	}
	
	@RequestMapping(
		method = RequestMethod.GET,
		path = "/random"
	)
	public ResponseEntity<MusicDTO> random() throws DaoException, NotFoundException {
		return ResponseBuilder.create200( musicService.random() );
	}
	
}
