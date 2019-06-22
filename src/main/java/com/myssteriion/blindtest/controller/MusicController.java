package com.myssteriion.blindtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myssteriion.blindtest.db.exception.SqlException;
import com.myssteriion.blindtest.model.base.EmptyModel;
import com.myssteriion.blindtest.rest.RestBuilder;
import com.myssteriion.blindtest.service.MusicService;

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
	public ResponseEntity<EmptyModel> refresh() throws SqlException {
		
		service.refresh();
		return RestBuilder.create204();
	}
	
}
