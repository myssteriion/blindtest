package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.base.ListDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.rest.ResponseBuilder;
import com.myssteriion.blindtest.service.ProfileStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "profilestats")
public class ProfileStatController {

	@Autowired
	private ProfileStatService profileStatService;
	
	
	
	@GetMapping
	public ResponseEntity< ListDTO<ProfileStatDTO> > findAll() throws DaoException {
		
		List<ProfileStatDTO> list = profileStatService.findAll();
		return ResponseBuilder.create200(list);
	}
	
}
