package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.dao.ProfileStatDAO;
import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.common.Avatar;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class ProfileStatServiceTest extends AbstractTest {

	@Mock
	private ProfileStatDAO profileStatDao;
	
	@Mock
	private ProfileService profileService;
	
	@InjectMocks
	private ProfileStatService profileStatService;
	
	
	
	@Test
	public void save() throws DaoException, NotFoundException, ConflictException {
		
		Integer profileStatId = 1;
		
		try {
			profileStatService.save(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profileStatDto' est obligatoire."), e);
		}
		
		
		ProfileDTO profileDTOMock = new ProfileDTO("name", new Avatar("avatar"));
		profileDTOMock.setId(1);
		Mockito.when(profileService.find(Mockito.any(ProfileDTO.class))).thenReturn(null, profileDTOMock);
		
		ProfileStatDTO profileStatDtoMock = new ProfileStatDTO(profileStatId);
		profileStatDtoMock.setId(1);
		Mockito.when(profileStatDao.find(Mockito.any(ProfileStatDTO.class))).thenReturn(null, profileStatDtoMock, (ProfileStatDTO) null);
		Mockito.when(profileStatDao.save(Mockito.any(ProfileStatDTO.class))).thenReturn(profileStatDtoMock);
		
		ProfileStatDTO profileStatDto = new ProfileStatDTO(profileStatId);
		
		try {
			profileStatService.save(profileStatDto);
			Assert.fail("Doit lever une NotFoundException car le mock throw.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("profileDto not found."), e);
		}
		
		Assert.assertSame( profileStatDtoMock, profileStatService.save(profileStatDto) );
		
		try {
			profileStatService.save(profileStatDto);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (ConflictException e) {
			verifyException(new ConflictException("profileStatDto already exists."), e);
		}

		ProfileStatDTO profileStatDtoSaved = profileStatService.save(profileStatDto);
		Assert.assertEquals( new Integer(1), profileStatDtoSaved.getId() );
		Assert.assertEquals( profileStatId, profileStatDtoSaved.getProfileId() );
		Assert.assertEquals( 0, profileStatDtoSaved.getPlayedGames() );
	}

	@Test
	public void update() throws DaoException, NotFoundException {

		Integer profileStatId = 1;


		try {
			profileStatService.update(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profileStatDTO' est obligatoire."), e);
		}


		ProfileStatDTO profileStatDto = new ProfileStatDTO(profileStatId);
		try {
			profileStatService.update(profileStatDto);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profileStatDTO -> id' est obligatoire."), e);
		}


		ProfileStatDTO profileStatDtoMockNotSame = new ProfileStatDTO(profileStatId);
		profileStatDtoMockNotSame.setId(2);
		ProfileStatDTO profileStatDtoMockSame = new ProfileStatDTO(profileStatId);
		profileStatDtoMockSame.setId(1);
		Mockito.when(profileStatDao.find(Mockito.any(ProfileStatDTO.class))).thenReturn(null, profileStatDtoMockNotSame, profileStatDtoMockNotSame, profileStatDtoMockSame);
		Mockito.when(profileStatDao.update(Mockito.any(ProfileStatDTO.class))).thenReturn(profileStatDto);

		try {
			profileStatDto.setId(1);
			profileStatService.update(profileStatDto);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("profileStatDTO not found."), e);
		}

		profileStatDto.setId(1);
		ProfileStatDTO profileDtoSaved = profileStatService.update(profileStatDto);
		Assert.assertEquals( new Integer(1), profileDtoSaved.getId() );
	}
	
	@Test
	public void find() throws DaoException {

		ProfileStatDTO profileStatDTOMock = new ProfileStatDTO(1);
		Mockito.when(profileStatDao.find(Mockito.any(ProfileStatDTO.class))).thenReturn(null, profileStatDTOMock);
		
		
		try {
			profileStatService.find(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profileStatDto' est obligatoire."), e);
		}
		
		ProfileStatDTO profileStatDTO = new ProfileStatDTO(1);
		Assert.assertNull( profileStatService.find(profileStatDTO) );
		Assert.assertNotNull( profileStatService.find(profileStatDTO) );
	}

	@Test
	public void findByProfile() throws DaoException, NotFoundException {

		ProfileDTO profileDto = new ProfileDTO("name");
		profileDto.setId(1);
		Mockito.when(profileService.find(Mockito.any(ProfileDTO.class))).thenReturn(null, profileDto);

		ProfileStatDTO profileStatDtoMock = new ProfileStatDTO(1);
		Mockito.when(profileStatDao.find(Mockito.any(ProfileStatDTO.class))).thenReturn(null, profileStatDtoMock);


		try {
			profileStatService.findByProfile(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profileDto' est obligatoire."), e);
		}

		try {
			profileStatService.findByProfile(profileDto);
			Assert.fail("Doit lever une NotFoundException car le stub (profileService) renvoi null.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("profileDto not found."), e);
		}

		try {
			profileStatService.findByProfile(profileDto);
			Assert.fail("Doit lever une NotFoundException car le stub (profileStatDao) renvoi null.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("profileStatDto not found."), e);
		}


		ProfileStatDTO actual = profileStatService.findByProfile(profileDto);
		Assert.assertNotNull(actual);
		Assert.assertSame(profileStatDtoMock, actual);
	}

	@Test
	public void findAll() throws DaoException {

		ProfileStatDTO profileStatDto = new ProfileStatDTO(1);
		Mockito.when(profileStatDao.findAll()).thenReturn( Arrays.asList(profileStatDto) );
		
		List<ProfileStatDTO> list = profileStatService.findAll();
		Assert.assertEquals( 1, list.size() );
	}

}
