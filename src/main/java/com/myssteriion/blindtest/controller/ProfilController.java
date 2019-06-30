package com.myssteriion.blindtest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myssteriion.blindtest.db.common.AlreadyExistsException;
import com.myssteriion.blindtest.db.common.NotFoundException;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.model.base.ListDTO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.rest.ResponseBuilder;
import com.myssteriion.blindtest.service.ProfilService;
import com.myssteriion.blindtest.tools.Constant;

@CrossOrigin
@RestController
@RequestMapping(
	path = "profils"
)
public class ProfilController {

	@Autowired
	private ProfilService service;
	
	
	
	@RequestMapping(
		method = RequestMethod.POST
	)
	public ResponseEntity<ProfilDTO> save(@RequestBody ProfilDTO dto) throws SqlException, AlreadyExistsException {
		return ResponseBuilder.create201( service.save(dto, true) );
	}
	
	@RequestMapping(
		method = RequestMethod.PUT,
		path = Constant.ID_PATH_PARAM
	)
	public ResponseEntity<ProfilDTO> update(@PathVariable("id") String id, @RequestBody ProfilDTO dto) throws SqlException, NotFoundException {
		
		dto.setId(id);
		return ResponseBuilder.create200( service.profilWasUpdated(dto) );
	}
		
	@RequestMapping(
		method = RequestMethod.GET
	)
	public ResponseEntity< ListDTO<ProfilDTO> > findAll() throws SqlException {
		
		List<ProfilDTO> list = service.findAll();
		return ResponseBuilder.create200(list);
	}
	
}
