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

@Service
public class ProfileStatService {

	@Autowired
	private ProfileStatDAO profileStatDao;
	
	@Autowired
	private ProfileService profileService;
	
	
	
	public ProfileStatDTO save(ProfileStatDTO profileStatDto) throws DaoException, NotFoundException, ConflictException {
		
		Tool.verifyValue("profileStatDto", profileStatDto);
		checkProfileDto(profileStatDto);

		profileStatDto.setId(null);
		ProfileStatDTO foundProfileDto = profileStatDao.find(profileStatDto);
		
		if ( !Tool.isNullOrEmpty(foundProfileDto) )
			throw new ConflictException("profileStatDto already exists.");
		
		
		foundProfileDto = profileStatDao.save(profileStatDto);
		
		return foundProfileDto;
	}

	public ProfileStatDTO update(ProfileStatDTO profileStatDTO) throws DaoException, NotFoundException {

		Tool.verifyValue("profileStatDTO", profileStatDTO);
		Tool.verifyValue("profileStatDTO -> id", profileStatDTO.getId());

		ProfileStatDTO foundProfileStatDto = profileStatDao.find(profileStatDTO);
		if ( Tool.isNullOrEmpty(foundProfileStatDto) )
			throw new NotFoundException("profileStatDTO not found.");

		return profileStatDao.update(profileStatDTO);
	}

	public ProfileStatDTO find(ProfileStatDTO profileStatDto) throws DaoException {
		
		Tool.verifyValue("profileStatDto", profileStatDto);
		return profileStatDao.find(profileStatDto);
	}

	public ProfileStatDTO findByProfile(ProfileDTO profileDto) throws DaoException, NotFoundException {

		Tool.verifyValue("profileDto", profileDto);

		ProfileDTO foundProfileDto = profileService.find(profileDto);

		if ( Tool.isNullOrEmpty(foundProfileDto) )
			throw new NotFoundException("profileDto not found.");

		ProfileStatDTO profileStatDto = new ProfileStatDTO( foundProfileDto.getId() );
		ProfileStatDTO foundProfileStatDTO = profileStatDao.find(profileStatDto);

		if ( Tool.isNullOrEmpty(foundProfileStatDTO) )
			throw new NotFoundException("profileStatDto not found.");

		return foundProfileStatDTO;
	}

	public List<ProfileStatDTO> findAll() throws DaoException {
		return profileStatDao.findAll();
	}

	private void checkProfileDto(ProfileStatDTO profileStatDto) throws DaoException, NotFoundException {
	
		ProfileDTO profileDto = new ProfileDTO("ANY");
		profileDto.setId( profileStatDto.getProfileId() );
		profileDto = profileService.find(profileDto);
		if ( Tool.isNullOrEmpty(profileDto) )
			throw new NotFoundException("profileDto not found.");
	}
	
}
