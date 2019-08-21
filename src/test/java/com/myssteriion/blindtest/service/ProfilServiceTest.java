package com.myssteriion.blindtest.service;

import java.util.Arrays;
import java.util.List;

import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.db.exception.DaoException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.db.dao.ProfilDAO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.model.dto.ProfilStatDTO;

public class ProfilServiceTest extends AbstractTest {

	@Mock
	private ProfilDAO profilDao;
	
	@Mock
	private ProfilStatService profilStatService;
	
	@InjectMocks
	private ProfilService profilService;
	
	
	
	@Test
	public void save() throws DaoException, NotFoundException, ConflictException {
		
		String name = "name";
		String avatar = "avatar";
		
		
		try {
			profilService.save(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilDto' est obligatoire."), e);
		}

		ProfilStatDTO profilStatDtoMock = new ProfilStatDTO(1);
		Mockito.when(profilStatService.save(Mockito.any(ProfilStatDTO.class))).thenReturn(profilStatDtoMock);
		
		ProfilDTO profilDtoMock = new ProfilDTO(name, avatar);
		profilDtoMock.setId(1);
		Mockito.when(profilDao.find(Mockito.any(ProfilDTO.class))).thenReturn(null, profilDtoMock, null);
		Mockito.when(profilDao.save(Mockito.any(ProfilDTO.class))).thenReturn(profilDtoMock);
		
		ProfilDTO profilDto = new ProfilDTO(name, avatar);
		Assert.assertSame( profilDtoMock, profilService.save(profilDto) );
		
		try {
			profilService.save(profilDto);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (ConflictException e) {
			verifyException(new ConflictException("The profilDto name is already used."), e);
		}

		ProfilDTO profilDtoSaved = profilService.save(profilDto);
		Assert.assertEquals( new Integer(1), profilDtoSaved.getId() );
		Assert.assertEquals( name, profilDtoSaved.getName() );
	}
	
	@Test
	public void update() throws DaoException, NotFoundException, ConflictException {
		
		String name = "name";
		String avatar = "avatar";
		
		
		try {
			profilService.update(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilDto' est obligatoire."), e);
		}
		
		
		ProfilDTO profilDto = new ProfilDTO(name, avatar);
		try {
			profilService.update(profilDto);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilDto -> id' est obligatoire."), e);
		}
		

		ProfilDTO profilStatDtoMockNotSame = new ProfilDTO(name, avatar);
		profilStatDtoMockNotSame.setId(2);
		ProfilDTO profilStatDtoMockSame = new ProfilDTO(name, avatar);
		profilStatDtoMockSame.setId(1);
		Mockito.when(profilDao.find(Mockito.any(ProfilDTO.class))).thenReturn(null, profilStatDtoMockNotSame, profilStatDtoMockNotSame, profilStatDtoMockSame);
		Mockito.when(profilDao.update(Mockito.any(ProfilDTO.class))).thenReturn(profilDto);
		
		try {
			profilDto.setId(1);
			profilService.update(profilDto);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("profilDto not found."), e);
		}
		
		try {
			profilDto.setId(1);
			profilService.update(profilDto);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (ConflictException e) {
			verifyException(new ConflictException("The profilDto name is already used."), e);
		}
		
		profilDto.setId(1);
		profilDto.setName("pouet");
		profilDto.setAvatar("avapouet");
		ProfilDTO profilDtoSaved = profilService.update(profilDto);
		Assert.assertEquals( new Integer(1), profilDtoSaved.getId() );
		Assert.assertEquals( "pouet", profilDtoSaved.getName() );
		Assert.assertEquals( "avapouet", profilDtoSaved.getAvatar());
	}

	@Test
	public void find() throws DaoException {

		ProfilDTO profilDtoMock = new ProfilDTO("name", "avatar");
		Mockito.when(profilDao.find(Mockito.any(ProfilDTO.class))).thenReturn(null, profilDtoMock);
		
		
		try {
			profilService.find(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilDto' est obligatoire."), e);
		}
		
		ProfilDTO profilDto = new ProfilDTO("name", "avatar");
		Assert.assertNull( profilService.find(profilDto) );
		Assert.assertNotNull( profilService.find(profilDto) );
	}
	
	@Test
	public void findAll() throws DaoException {

		ProfilDTO profilDto = new ProfilDTO("name", "avatar");
		Mockito.when(profilDao.findAll()).thenReturn( Arrays.asList(profilDto) );
		
		List<ProfilDTO> list = profilService.findAll();
		Assert.assertEquals( 1, list.size() );
	}

}
