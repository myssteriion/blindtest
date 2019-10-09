package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.model.dto.AvatarDTO;
import com.myssteriion.blindtest.rest.ResponseBuilder;
import com.myssteriion.blindtest.service.AvatarService;
import com.myssteriion.blindtest.tools.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for Avatar
 */
@CrossOrigin
@RestController
@RequestMapping(path = "avatars")
public class AvatarController {

	@Autowired
	private AvatarService avatarService;



	/**
	 * Find pageable of avatar filtered by prefix name.
	 *
	 * @param pageNumber the page number
	 * @return the pageable of avatars
	 */
	@GetMapping
	public ResponseEntity< Page<AvatarDTO> > findAllByNameStartingWith(
			@RequestParam(value = Constant.PREFIX_NAME, required = false, defaultValue = Constant.PREFIX_NAME_DEFAULT_VALUE) String prefixName,
			@RequestParam(value = Constant.PAGE_NUMBER, required = false, defaultValue = Constant.PAGE_NUMBER_DEFAULT_VALUE) Integer pageNumber) {

		Page<AvatarDTO> page = avatarService.findAllByNameStartingWith(prefixName, pageNumber);
		if ( avatarService.needRefresh() ) {
			avatarService.refresh();
			page = avatarService.findAllByNameStartingWith(prefixName, pageNumber);
		}

		return ResponseBuilder.create200(page);
	}
	
}
