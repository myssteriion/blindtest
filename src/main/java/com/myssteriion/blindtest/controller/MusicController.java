package com.myssteriion.blindtest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myssteriion.blindtest.rest.ResponseIModel;
import com.myssteriion.blindtest.rest.RestBuilder;
import com.myssteriion.blindtest.service.MusicService;

@RestController
@RequestMapping(
	path = "music"
)
public class MusicController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MusicController.class);
	
	@Autowired
	private MusicService service;
	
	
	
	@RequestMapping(
	path = "/refresh",
		method = RequestMethod.GET
	)
	public ResponseIModel<?> refresh() {
		
		try {
			
			service.refresh();
			return RestBuilder.create204();
		}
		catch (Exception e) {

			String message = "Can't refresh musics.";
			LOGGER.error(message, e);
			return RestBuilder.create500(message, e);
		}
	}
	
}
