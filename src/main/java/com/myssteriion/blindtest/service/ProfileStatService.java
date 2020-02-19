package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.db.dao.ProfileStatDAO;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.utils.Tools;
import com.myssteriion.utils.rest.exception.NotFoundException;
import com.myssteriion.utils.service.AbstractCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for ProfileStatDTO.
 */
@Service
public class ProfileStatService extends AbstractCRUDService<ProfileStatDTO, ProfileStatDAO> {

	@Autowired
	public ProfileStatService(ProfileStatDAO profileStatDao) {
		super(profileStatDao);
	}



	@Override
	public ProfileStatDTO find(ProfileStatDTO dto) {

		Tools.verifyValue("entity", dto);

		if ( Tools.isNullOrEmpty(dto.getId()) )
			return dao.findByProfileId(dto.getProfileId()).orElse(null);
		else
			return super.find(dto);
	}

	/**
	 * Find profile stat dto by profileId.
	 *
	 * @param profileDto the profile dto
	 * @return the profile stat dto
	 * @throws NotFoundException the not found exception
	 */
	public ProfileStatDTO findByProfile(ProfileDTO profileDto) throws NotFoundException {

		Tools.verifyValue("profile", profileDto);
		Tools.verifyValue("profile -> id", profileDto.getId());

		ProfileStatDTO profileStatDto = new ProfileStatDTO( profileDto.getId() );
		ProfileStatDTO foundProfileStatDTO = find(profileStatDto);

		if ( Tools.isNullOrEmpty(foundProfileStatDTO) )
			throw new NotFoundException("Profile stat not found.");

		return foundProfileStatDTO;
	}

}
