package com.myssteriion.blindtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myssteriion.blindtest.db.common.AlreadyExistsException;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.model.base.Empty;
import com.myssteriion.blindtest.rest.ResponseBuilder;
import com.myssteriion.blindtest.service.MusicService;

@CrossOrigin
@RestController
@RequestMapping(
	path = "musics"
)
public class MusicController {

	@Autowired
	private MusicService service;
	
	
	
	@RequestMapping(
		path = "/refresh",
		method = RequestMethod.GET
	)
	public ResponseEntity<Empty> refresh() throws SqlException, AlreadyExistsException {
		
		service.refresh();
		return ResponseBuilder.create204();
	}
	
}
