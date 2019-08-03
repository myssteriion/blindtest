package com.myssteriion.blindtest.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.common.AlreadyExistsException;
import com.myssteriion.blindtest.db.common.NotFoundException;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.db.dao.ProfilStatDAO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.model.dto.ProfilStatDTO;

public class ProfilStatServiceTest extends AbstractTest {

	@Mock
	private ProfilStatDAO profilStatDao;
	
	@Mock
	private ProfilService profilService;
	
	@InjectMocks
	private ProfilStatService profilStatService;
	
	
	
	@Test
	public void save() throws SqlException, NotFoundException, AlreadyExistsException {
		
		Integer profilStatId = 1;
		
		try {
			profilStatService.save(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilStatDto' est obligatoire."), e);
		}
		
		
		ProfilDTO profilDTOMock = new ProfilDTO("name", "avatar");
		profilDTOMock.setId(1);
		Mockito.when(profilService.find(Mockito.any(ProfilDTO.class))).thenReturn(null, profilDTOMock);
		
		ProfilStatDTO profilStatDtoMock = new ProfilStatDTO(profilStatId);
		profilStatDtoMock.setId(1);
		Mockito.when(profilStatDao.find(Mockito.any(ProfilStatDTO.class))).thenReturn(null, profilStatDtoMock, (ProfilStatDTO) null);
		Mockito.when(profilStatDao.save(Mockito.any(ProfilStatDTO.class))).thenReturn(profilStatDtoMock);
		
		ProfilStatDTO profilStatDto = new ProfilStatDTO(profilStatId);
		
		try {
			profilStatService.save(profilStatDto);
			Assert.fail("Doit lever une NotFoundException car le mock throw.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("profilDto not found."), e);
		}
		
		Assert.assertSame( profilStatDtoMock, profilStatService.save(profilStatDto) );
		
		try {
			profilStatService.save(profilStatDto);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (AlreadyExistsException e) {
			verifyException(new AlreadyExistsException("profilStatDto already exists."), e);
		}

		ProfilStatDTO profilStatDtoSaved = profilStatService.save(profilStatDto);
		Assert.assertEquals( new Integer(1), profilStatDtoSaved.getId() );
		Assert.assertEquals( profilStatId, profilStatDtoSaved.getProfilId() );
		Assert.assertEquals( 0, profilStatDtoSaved.getPlayedGames() );
		Assert.assertEquals( 0, profilStatDtoSaved.getListenedMusics() );
		Assert.assertEquals( 0, profilStatDtoSaved.getFoundMusics() );
	}

	@Test
	public void update() throws SqlException, NotFoundException, AlreadyExistsException {

		Integer profilStatId = 1;


		try {
			profilStatService.update(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilStatDTO' est obligatoire."), e);
		}


		ProfilStatDTO profilStatDto = new ProfilStatDTO(profilStatId);
		try {
			profilStatService.update(profilStatDto);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilStatDTO -> id' est obligatoire."), e);
		}


		ProfilStatDTO profilStatDtoMockNotSame = new ProfilStatDTO(profilStatId);
		profilStatDtoMockNotSame.setId(2);
		ProfilStatDTO profilStatDtoMockSame = new ProfilStatDTO(profilStatId);
		profilStatDtoMockSame.setId(1);
		Mockito.when(profilStatDao.find(Mockito.any(ProfilStatDTO.class))).thenReturn(null, profilStatDtoMockNotSame, profilStatDtoMockNotSame, profilStatDtoMockSame);
		Mockito.when(profilStatDao.update(Mockito.any(ProfilStatDTO.class))).thenReturn(profilStatDto);

		try {
			profilStatDto.setId(1);
			profilStatService.update(profilStatDto);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("profilStatDTO not found."), e);
		}

		profilStatDto.setId(1);
		ProfilStatDTO profilDtoSaved = profilStatService.update(profilStatDto);
		Assert.assertEquals( new Integer(1), profilDtoSaved.getId() );
	}
	
	@Test
	public void find() throws SqlException {

		ProfilStatDTO profilStatDTOMock = new ProfilStatDTO(1);
		Mockito.when(profilStatDao.find(Mockito.any(ProfilStatDTO.class))).thenReturn(null, profilStatDTOMock);
		
		
		try {
			profilStatService.find(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilStatDto' est obligatoire."), e);
		}
		
		ProfilStatDTO profilStatDTO = new ProfilStatDTO(1);
		Assert.assertNull( profilStatService.find(profilStatDTO) );
		Assert.assertNotNull( profilStatService.find(profilStatDTO) );
	}
	
	@Test
	public void findAll() throws SqlException {

		ProfilStatDTO profilStatDto = new ProfilStatDTO(1);
		Mockito.when(profilStatDao.findAll()).thenReturn( Arrays.asList(profilStatDto) );
		
		List<ProfilStatDTO> list = profilStatService.findAll();
		Assert.assertEquals( 1, list.size() );
	}

}
