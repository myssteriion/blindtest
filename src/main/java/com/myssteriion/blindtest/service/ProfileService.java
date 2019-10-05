package com.myssteriion.blindtest.service;

import java.util.List;

import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.db.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.db.dao.ProfileDAO;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.tools.Tool;

/**
 * Service for ProfileDTO.
 */
@Service
public class ProfileService {

	@Autowired
	private ProfileDAO profileDao;
	
	@Autowired
	private ProfileStatService profileStatService;



	/**
	 * Save profile dto.
	 *
	 * @param profileDto the profile dto
	 * @return the profile dto
	 * @throws DaoException      the dao exception
	 * @throws NotFoundException the not found exception
	 * @throws ConflictException the conflict exception
	 */
	public ProfileDTO save(ProfileDTO profileDto) throws DaoException, NotFoundException, ConflictException {
		
		Tool.verifyValue("profileDto", profileDto);
		
		profileDto.setId(null);
		ProfileDTO foundProfileDto = profileDao.find(profileDto);
		
		if ( !Tool.isNullOrEmpty(foundProfileDto) )
			throw new ConflictException("Profile name is already used.");
		
		
		foundProfileDto = profileDao.save(profileDto);
		
		profileStatService.save( new ProfileStatDTO(foundProfileDto.getId()) );
		
		return foundProfileDto;
	}

	/**
	 * Update profile dto.
	 *
	 * @param profileDto the profile dto
	 * @return the profile dto
	 * @throws DaoException      the dao exception
	 * @throws NotFoundException the not found exception
	 * @throws ConflictException the conflict exception
	 */
	public ProfileDTO update(ProfileDTO profileDto) throws DaoException, NotFoundException, ConflictException {
		
		Tool.verifyValue("profileDto", profileDto);
		Tool.verifyValue("profileDto -> id", profileDto.getId());
		
		ProfileDTO foundProfileDto = profileDao.find(profileDto);
		if ( Tool.isNullOrEmpty(foundProfileDto) )
			throw new NotFoundException("Profile not found.");
		
		
		Integer id = profileDto.getId();
		profileDto.setId(null);
		
		foundProfileDto = profileDao.find(profileDto);
		if ( foundProfileDto != null  && !foundProfileDto.getId().equals(id) )
			throw new ConflictException("Profile name is already used.");
		
		profileDto.setId(id);
		return profileDao.update(profileDto);
	}

	/**
	 * Find profile dto.
	 *
	 * @param profileDto the profile dto
	 * @return the profile dto
	 * @throws DaoException the dao exception
	 */
	public ProfileDTO find(ProfileDTO profileDto) throws DaoException {
		
		Tool.verifyValue("profileDto", profileDto);
		return profileDao.find(profileDto);
	}

	/**
	 * Find all profiles.
	 *
	 * @return the profiles list
	 * @throws DaoException DB exception
	 */
	public List<ProfileDTO> findAll() throws DaoException {
		return profileDao.findAll();
	}

	/**
	 * Delete profile dto.
	 *
	 * @param profileDto the profile dto
	 * @return RUE if the was deleted, FALSE otherwise
	 * @throws DaoException      the dao exception
	 * @throws NotFoundException the not found exception
	 */
	public boolean delete(ProfileDTO profileDto) throws DaoException, NotFoundException {

		Tool.verifyValue("profileDto", profileDto);
		Tool.verifyValue("profileDto -> id", profileDto.getId());

		ProfileDTO foundMusicDto = profileDao.find(profileDto);
		if ( Tool.isNullOrEmpty(foundMusicDto) )
			throw new NotFoundException("Profile not found.");

		return profileDao.delete(profileDto);
	}

}
