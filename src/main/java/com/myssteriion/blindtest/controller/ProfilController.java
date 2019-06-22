package com.myssteriion.blindtest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myssteriion.blindtest.db.exception.SqlException;
import com.myssteriion.blindtest.model.base.ListDTO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.rest.ResponseBuilder;
import com.myssteriion.blindtest.service.ProfilService;

@RestController
@RequestMapping(
	path = "profils"
)
public class ProfilController {

	@Autowired
	private ProfilService service;
	
	
	
	@RequestMapping(
		method = RequestMethod.GET
	)
	public ResponseEntity< ListDTO<ProfilDTO> > findAll() throws SqlException {
		
		List<ProfilDTO> list = service.findAll();
		return ResponseBuilder.create200(list);
	}
	
}
