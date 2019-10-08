package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.model.base.Empty;
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
	 * Scan avatar folder and refresh the cache.
	 *
	 * @return nothing
	 */
	@GetMapping(path = "/refresh")
	public ResponseEntity<Empty> refresh() {
		
		avatarService.refresh();
		return ResponseBuilder.create204();
	}

	/**
	 * Find pageable of avatar.
	 *
	 * @param page the page
	 * @return the pageable of avatar
	 */
	@GetMapping
	public ResponseEntity< Page<AvatarDTO> > findAll(
			@RequestParam(value = Constant.PAGE, required = false, defaultValue = Constant.PAGE_DEFAULT_VALUE) Integer page) {

		return ResponseBuilder.create200( avatarService.findAll(page) );
	}
	
}
