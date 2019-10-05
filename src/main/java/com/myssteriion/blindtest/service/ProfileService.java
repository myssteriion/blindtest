package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.db.dao.ProfileDAO;
import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for ProfileDTO.
 */
@Service
public class ProfileService extends AbstractCRUDService<ProfileDTO, ProfileDAO>  {

	private ProfileStatService profileStatService;



	@Autowired
	public ProfileService(ProfileDAO dao, ProfileStatService profileStatService) {
		super(dao);
		this.profileStatService = profileStatService;
	}



	@Override
	public ProfileDTO save(ProfileDTO dto) throws DaoException, ConflictException {

		ProfileDTO dtoSaved = super.save(dto);

		profileStatService.save( new ProfileStatDTO(dtoSaved.getId()) );
		return dtoSaved;
	}

	@Override
	public void delete(ProfileDTO profile) throws DaoException, NotFoundException {

		Tool.verifyValue("profile", profile);
		Tool.verifyValue("profile -> id", profile.getId());

		profileStatService.delete( profileStatService.findByProfile(profile) );
		super.delete(profile);
	}

}
