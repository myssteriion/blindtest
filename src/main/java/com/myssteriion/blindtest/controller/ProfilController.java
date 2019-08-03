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

import com.myssteriion.blindtest.db.common.ConflictException;
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
	private ProfilService profilService;
	
	
	
	@RequestMapping(
		method = RequestMethod.POST
	)
	public ResponseEntity<ProfilDTO> save(@RequestBody ProfilDTO profilDto) throws SqlException, NotFoundException, ConflictException {
		return ResponseBuilder.create201( profilService.save(profilDto) );
	}
	
	@RequestMapping(
		method = RequestMethod.PUT,
		path = Constant.ID_PATH_PARAM
	)
	public ResponseEntity<ProfilDTO> update(@PathVariable("id") Integer id, @RequestBody ProfilDTO profilDto) throws SqlException, NotFoundException, ConflictException {
		
		profilDto.setId(id);
		return ResponseBuilder.create200( profilService.update(profilDto) );
	}
		
	@RequestMapping(
		method = RequestMethod.GET
	)
	public ResponseEntity< ListDTO<ProfilDTO> > findAll() throws SqlException {
		
		List<ProfilDTO> list = profilService.findAll();
		return ResponseBuilder.create200(list);
	}
	
}
