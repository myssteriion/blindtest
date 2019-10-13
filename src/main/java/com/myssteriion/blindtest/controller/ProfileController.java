package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.model.base.Empty;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.rest.ResponseBuilder;
import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.service.ProfileService;
import com.myssteriion.blindtest.tools.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for ProfileDTO.
 */
@CrossOrigin
@RestController
@RequestMapping(path = "profiles")
public class ProfileController {

	private ProfileService profileService;

	/**
	 * Instantiates a new Profile controller.
	 *
	 * @param profileService the profile service
	 */
	@Autowired
	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}


	/**
	 * Save a new ProfileDTO.
	 *
	 * @param profileDto the ProfileDTO
	 * @return the ProfileDTO saved
	 * @throws ConflictException Conflict exception
	 */
	@PostMapping
	public ResponseEntity<ProfileDTO> save(@RequestBody ProfileDTO profileDto) throws ConflictException {
		return ResponseBuilder.create201( profileService.save(profileDto) );
	}

	/**
	 * Update an existing ProfileDTO.
	 *
	 * @param id         the ProfileDTO id
	 * @param profileDto the 'new' ProfileDTO
	 * @return the ProfileDTO modified
	 * @throws NotFoundException NotFound exception
	 */
	@PutMapping(path = Constant.ID_PATH_PARAM)
	public ResponseEntity<ProfileDTO> update(@PathVariable(Constant.ID) Integer id, @RequestBody ProfileDTO profileDto) throws NotFoundException {
		
		profileDto.setId(id);
		return ResponseBuilder.create200( profileService.update(profileDto) );
	}

	/**
	 * Find pageable of profile filtered by prefix name.
	 *
	 * @param pageNumber the page number
	 * @return the pageable of profiles
	 */
	@GetMapping
	public ResponseEntity< Page<ProfileDTO> > findAllByNameStartingWith(
			@RequestParam(value = Constant.PREFIX_NAME, required = false, defaultValue = Constant.PREFIX_NAME_DEFAULT_VALUE) String prefixName,
			@RequestParam(value = Constant.PAGE_NUMBER, required = false, defaultValue = Constant.PAGE_NUMBER_DEFAULT_VALUE) Integer pageNumber) {

		return ResponseBuilder.create200( profileService.findAllByNameStartingWith(prefixName, pageNumber) );
	}

	/**
	 * Delete response entity.
	 *
	 * @param id the id
	 * @return the response entity
	 * @throws NotFoundException the not found exception
	 */
	@DeleteMapping(path = Constant.ID_PATH_PARAM)
	public ResponseEntity<Empty> delete(@PathVariable(Constant.ID) Integer id) throws NotFoundException {

		ProfileDTO profileDto = new ProfileDTO("ANY");
		profileDto.setId(id);

		profileService.delete(profileDto);

		return ResponseBuilder.create204();
	}

}
