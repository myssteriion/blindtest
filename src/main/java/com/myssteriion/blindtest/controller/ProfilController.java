package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.base.ListDTO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.rest.ResponseBuilder;
import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.service.ProfilService;
import com.myssteriion.blindtest.tools.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "profils")
public class ProfilController {

	@Autowired
	private ProfilService profilService;
	
	
	
	@PostMapping
	public ResponseEntity<ProfilDTO> save(@RequestBody ProfilDTO profilDto) throws DaoException, NotFoundException, ConflictException {
		return ResponseBuilder.create201( profilService.save(profilDto) );
	}
	
	@PutMapping(path = Constant.ID_PATH_PARAM)
	public ResponseEntity<ProfilDTO> update(@PathVariable("id") Integer id, @RequestBody ProfilDTO profilDto) throws DaoException, NotFoundException, ConflictException {
		
		profilDto.setId(id);
		return ResponseBuilder.create200( profilService.update(profilDto) );
	}
		
	@GetMapping
	public ResponseEntity< ListDTO<ProfilDTO> > findAll() throws DaoException {
		
		List<ProfilDTO> list = profilService.findAll();
		return ResponseBuilder.create200(list);
	}
	
}
