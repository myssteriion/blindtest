package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.rest.ResponseBuilder;
import com.myssteriion.blindtest.service.ProfileStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for ProfileStatDTO.
 */
@CrossOrigin
@RestController
@RequestMapping(path = "profilestats")
public class ProfileStatController {

	private final ProfileStatService profileStatService;

	@Autowired
	public ProfileStatController(ProfileStatService profileStatService) {
		this.profileStatService = profileStatService;
	}



	/**
	 * Gets all ProfileStatDTO.
	 */
	@GetMapping
	public ResponseEntity< Page<ProfileStatDTO> > findAll() {
		return ResponseBuilder.create200( profileStatService.findAll() );
	}
	
}
