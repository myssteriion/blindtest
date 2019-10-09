package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.db.dao.ProfileDAO;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * Service for ProfileDTO.
 */
@Service
public class ProfileService extends AbstractCRUDService<ProfileDTO, ProfileDAO>  {

	private ProfileStatService profileStatService;

	private AvatarService avatarService;



	@Autowired
	public ProfileService(ProfileDAO profileDao, ProfileStatService profileStatService, AvatarService avatarService) {
		super(profileDao);
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
	public ProfileDTO update(ProfileDTO dto) throws NotFoundException {

		ProfileDTO profile = super.update(dto);
		createProfileAvatarFlux(profile);
		return profile;
	}

	@Override
	public ProfileDTO find(ProfileDTO dto) {

		Tool.verifyValue("dto", dto);

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
	 * Find a page of Profile filtered by a prefix name.
	 *
	 * @param namePrefix the name prefix
	 * @param page       the page
	 * @return the page
	 */
	public Page<ProfileDTO> findAllByNameStartingWith(String namePrefix, int page) {

		if (namePrefix == null)
			namePrefix = "";

		Page<ProfileDTO> pageable = dao.findAllByNameStartingWithIgnoreCase( namePrefix, creatPageable(page) );
		pageable.forEach(this::createProfileAvatarFlux);

		return pageable;
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
