package com.myssteriion.blindtest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.rest.ResponseIModel;
import com.myssteriion.blindtest.rest.RestBuilder;
import com.myssteriion.blindtest.service.ProfilService;

@RestController
@RequestMapping(
	path = "profils"
)
public class ProfilController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProfilController.class);
	
	@Autowired
	private ProfilService service;
	
	
	
	@RequestMapping(
		method = RequestMethod.GET
	)
	public ResponseIModel<?> findAll() {
		
		try {
			
			List<ProfilDTO> list = service.findAll();
			return RestBuilder.create200(list);
		}
		catch (Exception e) {

			String message = "Can't find all dto.";
			LOGGER.error(message, e);
			return RestBuilder.create500(message, e);
		}
	}
	
}
