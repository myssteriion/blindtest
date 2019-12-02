package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.dao.ProfileDAO;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

public class ProfileServiceTest extends AbstractTest {

	@Mock
	private ProfileDAO profileDao;
	
	@Mock
	private ProfileStatService profileStatService;

	@Mock
	private AvatarService avatarService;

	@InjectMocks
	private ProfileService profileService;



	@Before
	public void before() {
		profileService = new ProfileService(profileDao, profileStatService, avatarService, configProperties);
		stubProperties();
	}



	@Test
	public void save() throws ConflictException {

		String name = "name";
		String avatarName = "avatarName";


		try {
			profileService.save(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'entity' est obligatoire."), e);
		}

		profileService = Mockito.spy( new ProfileService(profileDao, profileStatService, avatarService, configProperties) );
		MockitoAnnotations.initMocks(profileService);

		ProfileStatDTO profileStatDtoMock = new ProfileStatDTO(1);
		Mockito.when(profileStatService.save(Mockito.any(ProfileStatDTO.class))).thenReturn(profileStatDtoMock);

		ProfileDTO profileDtoMock = new ProfileDTO(name, avatarName);
		profileDtoMock.setId(1);
		Mockito.doReturn(null).doReturn(profileDtoMock).doReturn(null).when(profileService).find(Mockito.any(ProfileDTO.class));
		Mockito.when(profileDao.save(Mockito.any(ProfileDTO.class))).thenReturn(profileDtoMock);

		ProfileDTO profileDto = new ProfileDTO(name, avatarName);
		Assert.assertSame( profileDtoMock, profileService.save(profileDto) );

		try {
			profileService.save(profileDto);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (ConflictException e) {
			verifyException(new ConflictException("Entity already exists."), e);
		}

		ProfileDTO profileDtoSaved = profileService.save(profileDto);
		Assert.assertEquals( new Integer(1), profileDtoSaved.getId() );
		Assert.assertEquals( name, profileDtoSaved.getName() );
	}

	@Test
	public void update() throws NotFoundException {

		String name = "name";
		String avatarName = "avatarName";


		try {
			profileService.update(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'entity' est obligatoire."), e);
		}


		ProfileDTO profileDto = new ProfileDTO(name, avatarName);
		try {
			profileService.update(profileDto);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'entity -> id' est obligatoire."), e);
		}


		ProfileDTO profileStatDtoMockNotSame = new ProfileDTO(name, avatarName);
		profileStatDtoMockNotSame.setId(2);
		ProfileDTO profileStatDtoMockSame = new ProfileDTO(name, avatarName);
		profileStatDtoMockSame.setId(1);
		Mockito.when(profileDao.findById(Mockito.anyInt())).thenReturn(Optional.empty(), Optional.of(profileStatDtoMockNotSame),
				Optional.of(profileStatDtoMockNotSame), Optional.of(profileStatDtoMockSame));
		Mockito.when(profileDao.save(Mockito.any(ProfileDTO.class))).thenReturn(profileDto);

		try {
			profileDto.setId(1);
			profileService.update(profileDto);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("Entity not found."), e);
		}

		profileDto.setId(1);
		profileDto.setName("pouet");
		profileDto.setAvatarName("avapouet");
		ProfileDTO profileDtoSaved = profileService.update(profileDto);
		Assert.assertEquals( new Integer(1), profileDtoSaved.getId() );
		Assert.assertEquals( "pouet", profileDtoSaved.getName() );
		Assert.assertEquals( "avapouet", profileDtoSaved.getAvatar().getName() );
	}

	@Test
	public void find() {

		ProfileDTO profileDtoMock = new ProfileDTO("name", "avatarName");
		Mockito.when(profileDao.findByName(Mockito.anyString())).thenReturn(Optional.empty(), Optional.of(profileDtoMock));


		try {
			profileService.find(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'entity' est obligatoire."), e);
		}

		ProfileDTO profileDto = new ProfileDTO("name", "avatarName");
		Assert.assertNull( profileService.find(profileDto) );
		Assert.assertNotNull( profileService.find(profileDto) );
	}

	@Test
	public void findAllByNameStartingWith() {

		ProfileDTO profileDto = new ProfileDTO("name", "avatarName");
		Mockito.when(profileDao.findAllByNameStartingWithIgnoreCase(Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn( new PageImpl<>(Collections.singletonList(profileDto)));

		Assert.assertEquals( new PageImpl<>(Collections.singletonList(profileDto)),  profileService.findAllByNameStartingWith(null, 0) );
		Assert.assertEquals( new PageImpl<>(Collections.singletonList(profileDto)),  profileService.findAllByNameStartingWith("", 0) );
	}

	@Test
	public void delete() throws NotFoundException {

		ProfileStatDTO profileStatDtoMock = new ProfileStatDTO();
		Mockito.when(profileStatService.findByProfile(Mockito.any(ProfileDTO.class))).thenReturn(profileStatDtoMock);


		try {
			profileService.delete(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profile' est obligatoire."), e);
		}


		ProfileDTO profileDto = new ProfileDTO("name", "avatarName");

		try {
			profileService.delete(profileDto);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profile -> id' est obligatoire."), e);
		}

		Mockito.when(profileDao.findById(Mockito.anyInt())).thenReturn(Optional.empty(), Optional.of(profileDto));
		profileDto.setId(1);

		try {
			profileService.delete(profileDto);
			Assert.fail("Doit lever une NotFoundException car le mock throw.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("Entity not found."), e);
		}

		profileService.delete(profileDto);
	}

}
