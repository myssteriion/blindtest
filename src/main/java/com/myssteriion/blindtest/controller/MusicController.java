package com.myssteriion.blindtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myssteriion.blindtest.rest.RestBuilder;
import com.myssteriion.blindtest.service.MusicService;

@RestController
@RequestMapping(
	path = "music"
)
public class MusicController {

	@Autowired
	private MusicService service;
	
	
	
	@RequestMapping(
	path = "/refresh",
		method = RequestMethod.GET
	)
	public ResponseEntity refresh() {
		
		try {
			service.refresh();
			return RestBuilder.createEmpty200();
		}
		catch (Exception e) {

			String message = "Can't refresh musics.";
			return RestBuilder.create500(message, e);
		}
	}
	
}
