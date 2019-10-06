package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.base.Empty;
import com.myssteriion.blindtest.model.base.ItemsPage;
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

/**
 * Controller for ProfileDTO.
 */
@CrossOrigin
@RestController
@RequestMapping(path = "profiles")
public class ProfileController {

	@Autowired
	private ProfileService profileService;


	/**
	 * Save a new ProfileDTO.
	 *
	 * @param profileDto the ProfileDTO
	 * @return the ProfileDTO saved
	 * @throws DaoException      DB exception
	 * @throws NotFoundException NotFound exception
	 * @throws ConflictException Conflict exception
	 */
	@PostMapping
	public ResponseEntity<ProfileDTO> save(@RequestBody ProfileDTO profileDto) throws DaoException, NotFoundException, ConflictException {
		return ResponseBuilder.create201( profileService.save(profileDto) );
	}

	/**
	 * Update an existing ProfileDTO.
	 *
	 * @param id         the ProfileDTO id
	 * @param profileDto the 'new' ProfileDTO
	 * @return the ProfileDTO modified
	 * @throws DaoException      DB exception
	 * @throws NotFoundException NotFound exception
	 * @throws ConflictException Conflict exception
	 */
	@PutMapping(path = Constant.ID_PATH_PARAM)
	public ResponseEntity<ProfileDTO> update(@PathVariable("id") Integer id, @RequestBody ProfileDTO profileDto) throws DaoException, NotFoundException, ConflictException {
		
		profileDto.setId(id);
		return ResponseBuilder.create200( profileService.update(profileDto) );
	}

	/**
	 * Gets all ProfileDTO.
	 *
	 * @return the ProfileDTO list
	 * @throws DaoException DB exception
	 */
	@GetMapping
	public ResponseEntity< ItemsPage<ProfileDTO> > findAll() throws DaoException {
		
		List<ProfileDTO> list = profileService.findAll();
		return ResponseBuilder.create200(list);
	}

	@DeleteMapping(path = Constant.ID_PATH_PARAM)
	public ResponseEntity<Empty> delete(@PathVariable("id") Integer id) throws DaoException, NotFoundException, ConflictException {

		ProfileDTO profileDto = new ProfileDTO("ANY");
		profileDto.setId(id);

		profileService.delete(profileDto);

		return ResponseBuilder.create204();
	}

}
