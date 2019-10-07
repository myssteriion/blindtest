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

	private final ProfileService profileService;

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
	 * Gets all ProfileDTO.
	 *
	 * @return the ProfileDTO list
	 */
	@GetMapping
	public ResponseEntity< Page<ProfileDTO> > findAll() {
		return ResponseBuilder.create200( profileService.findAll() );
	}

	@DeleteMapping(path = Constant.ID_PATH_PARAM)
	public ResponseEntity<Empty> delete(@PathVariable(Constant.ID) Integer id) throws NotFoundException {

		ProfileDTO profileDto = new ProfileDTO("ANY");
		profileDto.setId(id);

		profileService.delete(profileDto);

		return ResponseBuilder.create204();
	}

}
