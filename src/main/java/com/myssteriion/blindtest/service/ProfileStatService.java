package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.db.dao.ProfileStatDAO;
import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for ProfileStatDTO.
 */
@Service
public class ProfileStatService extends AbstractCRUDService<ProfileStatDTO, ProfileStatDAO>   {

	@Autowired
	public ProfileStatService(ProfileStatDAO dao) {
		super(dao);
	}



	/**
	 * Find profile stat dto by profileId.
	 *
	 * @param profileDto the profile dto
	 * @return the profile stat dto
	 * @throws DaoException      the dao exception
	 * @throws NotFoundException the not found exception
	 */
	public ProfileStatDTO findByProfile(ProfileDTO profileDto) throws DaoException, NotFoundException {

		Tool.verifyValue("profileDto", profileDto);
		Tool.verifyValue("profileDto -> id", profileDto.getId());

		ProfileStatDTO profileStatDto = new ProfileStatDTO( profileDto.getId() );
		ProfileStatDTO foundProfileStatDTO = dao.find(profileStatDto);

		if ( Tool.isNullOrEmpty(foundProfileStatDTO) )
			throw new NotFoundException("Profile stat not found.");

		return foundProfileStatDTO;
	}

}
