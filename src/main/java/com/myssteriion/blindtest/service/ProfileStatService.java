package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.db.dao.ProfileStatDAO;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for ProfileStatDTO.
 */
@Service
public class ProfileStatService {

	@Autowired
	private ProfileStatDAO profileStatDao;
	
	@Autowired
	private ProfileService profileService;



	/**
	 * Save profile stat dto.
	 *
	 * @param profileStatDto the profile stat dto
	 * @return the profile stat dto
	 * @throws DaoException      the dao exception
	 * @throws NotFoundException the not found exception
	 * @throws ConflictException the conflict exception
	 */
	public ProfileStatDTO save(ProfileStatDTO profileStatDto) throws DaoException, NotFoundException, ConflictException {
		
		Tool.verifyValue("profileStatDto", profileStatDto);
		checkProfileDto(profileStatDto);

		profileStatDto.setId(null);
		ProfileStatDTO foundProfileDto = profileStatDao.find(profileStatDto);
		
		if ( !Tool.isNullOrEmpty(foundProfileDto) )
			throw new ConflictException("Profile stat already exists.");
		
		
		foundProfileDto = profileStatDao.save(profileStatDto);
		
		return foundProfileDto;
	}

	/**
	 * Update profile stat dto.
	 *
	 * @param profileStatDTO the profile stat dto
	 * @return the profile stat dto
	 * @throws DaoException      the dao exception
	 * @throws NotFoundException the not found exception
	 */
	public ProfileStatDTO update(ProfileStatDTO profileStatDTO) throws DaoException, NotFoundException {

		Tool.verifyValue("profileStatDTO", profileStatDTO);
		Tool.verifyValue("profileStatDTO -> id", profileStatDTO.getId());

		ProfileStatDTO foundProfileStatDto = profileStatDao.find(profileStatDTO);
		if ( Tool.isNullOrEmpty(foundProfileStatDto) )
			throw new NotFoundException("Profile stat not found.");

		return profileStatDao.update(profileStatDTO);
	}

	/**
	 * Find profile stat dto.
	 *
	 * @param profileStatDto the profile stat dto
	 * @return the profile stat dto
	 * @throws DaoException the dao exception
	 */
	public ProfileStatDTO find(ProfileStatDTO profileStatDto) throws DaoException {
		
		Tool.verifyValue("profileStatDto", profileStatDto);
		return profileStatDao.find(profileStatDto);
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

		ProfileDTO foundProfileDto = profileService.find(profileDto);

		if ( Tool.isNullOrEmpty(foundProfileDto) )
			throw new NotFoundException("Profile not found.");

		ProfileStatDTO profileStatDto = new ProfileStatDTO( foundProfileDto.getId() );
		ProfileStatDTO foundProfileStatDTO = profileStatDao.find(profileStatDto);

		if ( Tool.isNullOrEmpty(foundProfileStatDTO) )
			throw new NotFoundException("Profile stat not found.");

		return foundProfileStatDTO;
	}

	/**
	 * Find all profileStat list.
	 *
	 * @return the profileStat list
	 * @throws DaoException the dao exception
	 */
	public List<ProfileStatDTO> findAll() throws DaoException {
		return profileStatDao.findAll();
	}

	/**
	 * Delete profileStat dto.
	 *
	 * @param profileStat the profile dto
	 * @return RUE if the was deleted, FALSE otherwise
	 * @throws DaoException      the dao exception
	 * @throws NotFoundException the not found exception
	 */
	public boolean delete(ProfileStatDTO profileStat) throws DaoException, NotFoundException {

		Tool.verifyValue("profileStat", profileStat);
		Tool.verifyValue("profileStat -> id", profileStat.getId());

		ProfileStatDTO foundMusicDto = profileStatDao.find(profileStat);
		if ( Tool.isNullOrEmpty(foundMusicDto) )
			throw new NotFoundException("Profile stat not found.");

		return profileStatDao.delete(profileStat);
	}


	/**
	 * Check if the profile exists for profileStat.
	 *
	 * @param profileStatDto the profileStatDto
	 * @throws DaoException	 	 the dao exception
	 * @throws NotFoundException the not found exception
	 */
	private void checkProfileDto(ProfileStatDTO profileStatDto) throws DaoException, NotFoundException {
	
		ProfileDTO profileDto = new ProfileDTO("ANY");
		profileDto.setId( profileStatDto.getProfileId() );
		profileDto = profileService.find(profileDto);
		if ( Tool.isNullOrEmpty(profileDto) )
			throw new NotFoundException("Profile not found.");
	}
	
}
