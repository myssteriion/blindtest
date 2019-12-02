package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.db.dao.ProfileDAO;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.properties.ConfigProperties;
import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Service for ProfileDTO.
 */
@Service
public class ProfileService extends AbstractCRUDService<ProfileDTO, ProfileDAO>  {

	private ProfileStatService profileStatService;

	private AvatarService avatarService;



	@Autowired
	public ProfileService(ProfileDAO profileDao, ProfileStatService profileStatService, AvatarService avatarService, ConfigProperties configProperties) {
		super(profileDao, configProperties);
		this.profileStatService = profileStatService;
		this.avatarService = avatarService;
	}



	@Override
	public ProfileDTO save(ProfileDTO dto) throws ConflictException {

		ProfileDTO profile = super.save(dto);
		createProfileAvatarFlux(profile);
		profileStatService.save( new ProfileStatDTO(profile.getId()) );
		return profile;
	}

	@Override
	public ProfileDTO update(ProfileDTO dto) throws NotFoundException, ConflictException {

		ProfileDTO profile = super.update(dto);
		createProfileAvatarFlux(profile);
		return profile;
	}

	@Override
	public ProfileDTO find(ProfileDTO dto) {

		Tool.verifyValue("entity", dto);

		ProfileDTO profile;
		if ( Tool.isNullOrEmpty(dto.getId()) )
			profile = dao.findByName(dto.getName()).orElse(null);
		else
			profile = super.find(dto);

		if (profile != null)
			createProfileAvatarFlux(profile);

		return profile;
	}

	/**
	 * Find a pageNumber of Profile filtered by a prefix name.
	 *
	 * @param namePrefix the name prefix
	 * @param pageNumber the page number
	 * @return the page of profiles
	 */
	public Page<ProfileDTO> findAllByNameStartingWith(String namePrefix, int pageNumber) {

		if (namePrefix == null)
			namePrefix = "";

		Sort.Order order = new Sort.Order(Sort.Direction.ASC, "name").ignoreCase();
		Pageable pageable = PageRequest.of( pageNumber, configProperties.getPaginationElementsPerPageProfiles(), Sort.by(order) );

		Page<ProfileDTO> page = dao.findAllByNameStartingWithIgnoreCase(namePrefix, pageable);
		page.forEach(this::createProfileAvatarFlux);

		return page;
	}

	@Override
	public void delete(ProfileDTO profile) throws NotFoundException {

		Tool.verifyValue("profile", profile);
		Tool.verifyValue("profile -> id", profile.getId());

		profileStatService.delete( profileStatService.findByProfile(profile) );
		super.delete(profile);
	}


	/**
	 * Create profile avatar flux.
	 *
	 * @param dto the dto
	 */
	private void createProfileAvatarFlux(ProfileDTO dto) {

		// setter is useful for create avatar inside profile
		dto.setAvatarName( dto.getAvatarName() );
		avatarService.createAvatarFlux( dto.getAvatar() );
	}

}
