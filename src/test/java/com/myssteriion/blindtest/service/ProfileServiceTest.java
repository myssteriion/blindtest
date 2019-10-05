package com.myssteriion.blindtest.service;

import java.util.Arrays;
import java.util.List;

import com.myssteriion.blindtest.model.common.Avatar;
import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.db.exception.DaoException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.db.dao.ProfileDAO;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;

public class ProfileServiceTest extends AbstractTest {

	@Mock
	private ProfileDAO profileDao;
	
	@Mock
	private ProfileStatService profileStatService;
	
	@InjectMocks
	private ProfileService profileService;
	
	
	
	@Test
	public void save() throws DaoException, NotFoundException, ConflictException {
		
		String name = "name";
		String avatar = "avatar";
		
		
		try {
			profileService.save(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profileDto' est obligatoire."), e);
		}

		ProfileStatDTO profileStatDtoMock = new ProfileStatDTO(1);
		Mockito.when(profileStatService.save(Mockito.any(ProfileStatDTO.class))).thenReturn(profileStatDtoMock);
		
		ProfileDTO profileDtoMock = new ProfileDTO(name, new Avatar(avatar));
		profileDtoMock.setId(1);
		Mockito.when(profileDao.find(Mockito.any(ProfileDTO.class))).thenReturn(null, profileDtoMock, null);
		Mockito.when(profileDao.save(Mockito.any(ProfileDTO.class))).thenReturn(profileDtoMock);
		
		ProfileDTO profileDto = new ProfileDTO(name, new Avatar(avatar));
		Assert.assertSame( profileDtoMock, profileService.save(profileDto) );
		
		try {
			profileService.save(profileDto);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (ConflictException e) {
			verifyException(new ConflictException("Profile name is already used."), e);
		}

		ProfileDTO profileDtoSaved = profileService.save(profileDto);
		Assert.assertEquals( new Integer(1), profileDtoSaved.getId() );
		Assert.assertEquals( name, profileDtoSaved.getName() );
	}
	
	@Test
	public void update() throws DaoException, NotFoundException, ConflictException {
		
		String name = "name";
		String avatar = "avatar";
		
		
		try {
			profileService.update(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profileDto' est obligatoire."), e);
		}
		
		
		ProfileDTO profileDto = new ProfileDTO(name, new Avatar(avatar));
		try {
			profileService.update(profileDto);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profileDto -> id' est obligatoire."), e);
		}
		

		ProfileDTO profileStatDtoMockNotSame = new ProfileDTO(name, new Avatar(avatar));
		profileStatDtoMockNotSame.setId(2);
		ProfileDTO profileStatDtoMockSame = new ProfileDTO(name, new Avatar(avatar));
		profileStatDtoMockSame.setId(1);
		Mockito.when(profileDao.find(Mockito.any(ProfileDTO.class))).thenReturn(null, profileStatDtoMockNotSame, profileStatDtoMockNotSame, profileStatDtoMockSame);
		Mockito.when(profileDao.update(Mockito.any(ProfileDTO.class))).thenReturn(profileDto);
		
		try {
			profileDto.setId(1);
			profileService.update(profileDto);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("Profile not found."), e);
		}
		
		try {
			profileDto.setId(1);
			profileService.update(profileDto);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (ConflictException e) {
			verifyException(new ConflictException("Profile name is already used."), e);
		}
		
		profileDto.setId(1);
		profileDto.setName("pouet");
		profileDto.setAvatar( new Avatar("avapouet") );
		ProfileDTO profileDtoSaved = profileService.update(profileDto);
		Assert.assertEquals( new Integer(1), profileDtoSaved.getId() );
		Assert.assertEquals( "pouet", profileDtoSaved.getName() );
		Assert.assertEquals( "avapouet", profileDtoSaved.getAvatar().getName() );
	}

	@Test
	public void find() throws DaoException {

		ProfileDTO profileDtoMock = new ProfileDTO("name", new Avatar("avatar"));
		Mockito.when(profileDao.find(Mockito.any(ProfileDTO.class))).thenReturn(null, profileDtoMock);
		
		
		try {
			profileService.find(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profileDto' est obligatoire."), e);
		}
		
		ProfileDTO profileDto = new ProfileDTO("name", new Avatar("avatar"));
		Assert.assertNull( profileService.find(profileDto) );
		Assert.assertNotNull( profileService.find(profileDto) );
	}
	
	@Test
	public void findAll() throws DaoException {

		ProfileDTO profileDto = new ProfileDTO("name", new Avatar("avatar"));
		Mockito.when(profileDao.findAll()).thenReturn( Arrays.asList(profileDto) );
		
		List<ProfileDTO> list = profileService.findAll();
		Assert.assertEquals( 1, list.size() );
	}

}
