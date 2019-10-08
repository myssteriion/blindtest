package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.rest.ResponseBuilder;
import com.myssteriion.blindtest.service.ProfileStatService;
import com.myssteriion.blindtest.tools.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for ProfileStatDTO.
 */
@CrossOrigin
@RestController
@RequestMapping(path = "profilestats")
public class ProfileStatController {

	private final ProfileStatService profileStatService;

	/**
	 * Instantiates a new Profile stat controller.
	 *
	 * @param profileStatService the profile stat service
	 */
	@Autowired
	public ProfileStatController(ProfileStatService profileStatService) {
		this.profileStatService = profileStatService;
	}



	/**
	 * Find pageable of profile stat.
	 *
	 * @param page the page
	 * @return the pageable of profile stat
	 */
	@GetMapping
	public ResponseEntity< Page<ProfileStatDTO> > findAll(
			@RequestParam(value = Constant.PAGE, required = false, defaultValue = Constant.PAGE_DEFAULT_VALUE) Integer page) {

		return ResponseBuilder.create200( profileStatService.findAll(page) );
	}

}
