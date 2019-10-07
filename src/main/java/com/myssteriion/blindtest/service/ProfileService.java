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

import java.util.List;

/**
 * Service for ProfileDTO.
 */
@Service
public class ProfileService extends AbstractCRUDService<ProfileDTO, ProfileDAO>  {

	private ProfileStatService profileStatService;



	@Autowired
	public ProfileService(ProfileDAO profileDao, ProfileStatService profileStatService) {
		super(profileDao);
		this.profileStatService = profileStatService;
	}



	@Override
	public ProfileDTO save(ProfileDTO dto) throws ConflictException {

		ProfileDTO dtoSaved = super.save(dto);
		profileStatService.save( new ProfileStatDTO(dtoSaved.getId()) );
		return dtoSaved;
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
			profile.createAvatar();

		return profile;
	}

	@Override
	public Page<ProfileDTO> findAll() {

		Page<ProfileDTO> page = super.findAll();
		page.forEach(ProfileDTO::createAvatar);

		return page;
	}

	@Override
	public void delete(ProfileDTO profile) throws NotFoundException {

		Tool.verifyValue("profile", profile);
		Tool.verifyValue("profile -> id", profile.getId());

		profileStatService.delete( profileStatService.findByProfile(profile) );
		super.delete(profile);
	}

}
