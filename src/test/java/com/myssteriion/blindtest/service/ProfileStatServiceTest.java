package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.dao.ProfileStatDAO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

public class ProfileStatServiceTest extends AbstractTest {

	@Mock
	private ProfileStatDAO profileStatDao;

	@InjectMocks
	private ProfileStatService profileStatService;



	@Before
	public void before() {
		profileStatService = new ProfileStatService(profileStatDao);
	}



	@Test
	public void save() throws ConflictException {

		Integer profileStatId = 1;

		try {
			profileStatService.save(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
		}

		profileStatService = Mockito.spy( profileStatService = new ProfileStatService(profileStatDao) );
		MockitoAnnotations.initMocks(profileStatService);

		ProfileDTO profileDTOMock = new ProfileDTO("name", "avatarName");
		profileDTOMock.setId(1);

		ProfileStatDTO profileStatDtoMock = new ProfileStatDTO(profileStatId);
		profileStatDtoMock.setId(1);
		Mockito.doReturn(null).doReturn(profileStatDtoMock).doReturn(null).when(profileStatService).find(Mockito.any(ProfileStatDTO.class));
		Mockito.when(profileStatDao.save(Mockito.any(ProfileStatDTO.class))).thenReturn(profileStatDtoMock);

		ProfileStatDTO profileStatDto = new ProfileStatDTO(profileStatId);
		Assert.assertSame( profileStatDtoMock, profileStatService.save(profileStatDto) );

		try {
			profileStatService.save(profileStatDto);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (ConflictException e) {
			verifyException(new ConflictException("Dto already exists."), e);
		}

		ProfileStatDTO profileStatDtoSaved = profileStatService.save(profileStatDto);
		Assert.assertEquals( new Integer(1), profileStatDtoSaved.getId() );
		Assert.assertEquals( profileStatId, profileStatDtoSaved.getProfileId() );
		Assert.assertEquals( 0, profileStatDtoSaved.getPlayedGames() );
	}

	@Test
	public void update() throws NotFoundException {

		Integer profileStatId = 1;


		try {
			profileStatService.update(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
		}


		ProfileStatDTO profileStatDto = new ProfileStatDTO(profileStatId);
		try {
			profileStatService.update(profileStatDto);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto -> id' est obligatoire."), e);
		}


		ProfileStatDTO profileStatDtoMockNotSame = new ProfileStatDTO(profileStatId);
		profileStatDtoMockNotSame.setId(2);
		ProfileStatDTO profileStatDtoMockSame = new ProfileStatDTO(profileStatId);
		profileStatDtoMockSame.setId(1);
		Mockito.when(profileStatDao.findById(Mockito.anyInt())).thenReturn(Optional.empty(), Optional.of(profileStatDtoMockNotSame),
				Optional.of(profileStatDtoMockNotSame), Optional.of(profileStatDtoMockSame));
		Mockito.when(profileStatDao.save(Mockito.any(ProfileStatDTO.class))).thenReturn(profileStatDto);

		try {
			profileStatDto.setId(1);
			profileStatService.update(profileStatDto);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("Dto not found."), e);
		}

		profileStatDto.setId(1);
		ProfileStatDTO profileDtoSaved = profileStatService.update(profileStatDto);
		Assert.assertEquals( new Integer(1), profileDtoSaved.getId() );
	}

	@Test
	public void find() {

		ProfileStatDTO profileStatDTOMock = new ProfileStatDTO(1);
		Mockito.when(profileStatDao.findByProfileId(Mockito.anyInt())).thenReturn(Optional.empty(), Optional.of(profileStatDTOMock));
		Mockito.when(profileStatDao.findById(Mockito.anyInt())).thenReturn(Optional.of(profileStatDTOMock));


		try {
			profileStatService.find(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
		}

		ProfileStatDTO profileStatDTO = new ProfileStatDTO(1);
		Assert.assertNull( profileStatService.find(profileStatDTO) );
		Assert.assertNotNull( profileStatService.find(profileStatDTO) );

		profileStatDTO.setId(1);
		Assert.assertNotNull( profileStatService.find(profileStatDTO) );
	}

	@Test
	public void findByProfile() throws NotFoundException {

		ProfileDTO profileDto = new ProfileDTO("name");
		profileDto.setId(1);

		ProfileStatDTO profileStatDtoMock = new ProfileStatDTO(1);
		Mockito.when(profileStatDao.findByProfileId(Mockito.anyInt())).thenReturn(Optional.empty(), Optional.of(profileStatDtoMock));


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
			verifyException(new NotFoundException("Profile stat not found."), e);
		}

		ProfileStatDTO actual = profileStatService.findByProfile(profileDto);
		Assert.assertNotNull(actual);
		Assert.assertSame(profileStatDtoMock, actual);
	}

	@Test
	public void findAll() {

		ProfileStatDTO profileStatDto = new ProfileStatDTO(1);
		Mockito.when(profileStatDao.findAll(Mockito.any(Pageable.class))).thenReturn( new PageImpl<>(Arrays.asList(profileStatDto)) );

		Assert.assertEquals( new PageImpl<>(Arrays.asList(profileStatDto)), profileStatService.findAll(0) );
	}

}
