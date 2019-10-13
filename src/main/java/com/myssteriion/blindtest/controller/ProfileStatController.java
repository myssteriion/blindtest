package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.rest.ResponseBuilder;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.service.ProfileService;
import com.myssteriion.blindtest.service.ProfileStatService;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.blindtest.tools.exception.CustomRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for ProfileStatDTO.
 */
@CrossOrigin
@RestController
@RequestMapping(path = "profilestats")
public class ProfileStatController {

	private ProfileStatService profileStatService;

	private ProfileService profileService;



	/**
	 * Instantiates a new Profile stat controller.
	 *
	 * @param profileStatService the profile stat service
	 */
	@Autowired
	public ProfileStatController(ProfileStatService profileStatService, ProfileService profileService) {
		this.profileStatService = profileStatService;
		this.profileService = profileService;
	}



	/**
	 * Find profiles stats list from profiles ids list.
	 *
	 * @param profilesIds the profiles ids list
	 * @return the profiles stats list
	 */
	@GetMapping
	public ResponseEntity< Page<ProfileStatDTO> > findAllByProfilesIds(
			@RequestParam(value = Constant.PROFILES_IDS, required = false, defaultValue = Constant.PROFILES_IDS_DEFAULT_VALUE) List<Integer> profilesIds) {

		List<ProfileStatDTO> profilesStats = new ArrayList<>();

		profilesIds.forEach(id -> {

			try {

				ProfileDTO profile = new ProfileDTO().setId(id);
				profile = profileService.find(profile);
				if (profile == null)
					throw new NotFoundException("Profile not found.");

				profilesStats.add( profileStatService.findByProfile(profile));
			}
			catch (NotFoundException e) {
				throw new CustomRuntimeException("Can't find profile stat.", e);
			}
		});

		return ResponseBuilder.create200( new PageImpl<>(profilesStats) );
	}

}
