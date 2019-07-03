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
import com.myssteriion.blindtest.db.dao.ProfilDAO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;

public class ProfilServiceTest extends AbstractTest {

	@Mock
	private ProfilDAO profilDao;
	
	@InjectMocks
	private ProfilService profilService;
	
	
	
	@Test
	public void save() throws SqlException, NotFoundException, AlreadyExistsException {
		
		String name = "name";
		String avatar = "avatar";
		
		
		try {
			profilService.save(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilDto' est obligatoire."), e);
		}
		
		
		ProfilDTO profilDtoMock = new ProfilDTO(name, avatar);
		profilDtoMock.setId(1);
		Mockito.when(profilDao.find(Mockito.any(ProfilDTO.class))).thenReturn(null, profilDtoMock, null);
		Mockito.when(profilDao.save(Mockito.any(ProfilDTO.class))).thenReturn(profilDtoMock);
		
		ProfilDTO profilDto = new ProfilDTO(name, avatar);
		Assert.assertSame( profilDtoMock, profilService.save(profilDto) );
		
		try {
			profilService.save(profilDto);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (AlreadyExistsException e) {
			verifyException(new AlreadyExistsException("profilDto already exists."), e);
		}

		ProfilDTO profilDtoSaved = profilService.save(profilDto);
		Assert.assertEquals( new Integer(1), profilDtoSaved.getId() );
		Assert.assertEquals( name, profilDtoSaved.getName() );
	}
	
	@Test
	public void updated() throws SqlException, NotFoundException {
		
		String name = "name";
		String avatar = "avatar";
		
		
		try {
			profilService.updated(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'profilDto' est obligatoire."), e);
		}
		
		
		ProfilDTO profilDto = new ProfilDTO(name, avatar);
		profilDto.setId(1);
		Mockito.when(profilDao.find(Mockito.any(ProfilDTO.class))).thenReturn(null, profilDto);
		Mockito.when(profilDao.update(Mockito.any(ProfilDTO.class))).thenReturn(profilDto);
		
		try {
			profilService.updated(profilDto);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("profilDto not found."), e);
		}
		
		profilDto.setName("pouet");
		profilDto.setAvatar("avapouet");
		ProfilDTO profilDtoSaved = profilService.updated(profilDto);
		Assert.assertEquals( new Integer(1), profilDtoSaved.getId() );
		Assert.assertEquals( "pouet", profilDtoSaved.getName() );
		Assert.assertEquals( "avapouet", profilDtoSaved.getAvatar());
	}

	@Test
	public void findAll() throws SqlException {

		ProfilDTO profilDto = new ProfilDTO("name", "avatar");
		Mockito.when(profilDao.findAll()).thenReturn( Arrays.asList(profilDto) );
		
		List<ProfilDTO> list = profilService.findAll();
		Assert.assertEquals( 1, list.size() );
	}

}
