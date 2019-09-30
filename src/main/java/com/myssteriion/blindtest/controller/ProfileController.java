package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.base.ListDTO;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.rest.ResponseBuilder;
import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.service.ProfileService;
import com.myssteriion.blindtest.tools.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "profiles")
public class ProfileController {

	@Autowired
	private ProfileService profileService;
	
	
	
	@PostMapping
	public ResponseEntity<ProfileDTO> save(@RequestBody ProfileDTO profileDto) throws DaoException, NotFoundException, ConflictException {
		return ResponseBuilder.create201( profileService.save(profileDto) );
	}
	
	@PutMapping(path = Constant.ID_PATH_PARAM)
	public ResponseEntity<ProfileDTO> update(@PathVariable("id") Integer id, @RequestBody ProfileDTO profileDto) throws DaoException, NotFoundException, ConflictException {
		
		profileDto.setId(id);
		return ResponseBuilder.create200( profileService.update(profileDto) );
	}
		
	@GetMapping
	public ResponseEntity< ListDTO<ProfileDTO> > findAll() throws DaoException {
		
		List<ProfileDTO> list = profileService.findAll();
		return ResponseBuilder.create200(list);
	}
	
}
