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
	private ProfilDAO dao;
	
	@InjectMocks
	private ProfilService service;
	
	
	
	@Test
	public void save() throws SqlException, AlreadyExistsException {
		
		String name = "name";
		String avatar = "avatar";
		
		
		try {
			service.save(null, false);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
		}
		
		
		ProfilDTO dto = new ProfilDTO(name, avatar);
		dto.setId("1");
		Mockito.when(dao.find(Mockito.any(ProfilDTO.class))).thenReturn(null, dto);
		Mockito.when(dao.save(Mockito.any(ProfilDTO.class))).thenReturn(dto);
		
		ProfilDTO dtoSaved = service.save(dto, false);
		Assert.assertSame(dto, dtoSaved);
		
		try {
			service.save(dto, true);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (AlreadyExistsException e) {
			verifyException(new AlreadyExistsException("DTO already exists."), e);
		}

		dtoSaved = service.save(dto, false);
		Assert.assertEquals( "1", dtoSaved.getId() );
		Assert.assertEquals( name, dtoSaved.getName() );
		Assert.assertEquals( 0, dtoSaved.getPlayedGames() );
		Assert.assertEquals( 0, dtoSaved.getListenedMusics() );
		Assert.assertEquals( 0, dtoSaved.getFoundMusics() );
	}
	
	@Test
	public void profilWasUpdated() throws SqlException, NotFoundException {
		
		String name = "name";
		String avatar = "avatar";
		
		
		try {
			service.profilWasUpdated(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
		}
		
		
		ProfilDTO dto = new ProfilDTO(name, avatar);
		dto.setId("1");
		Mockito.when(dao.find(Mockito.any(ProfilDTO.class))).thenReturn(null, dto);
		Mockito.when(dao.update(Mockito.any(ProfilDTO.class))).thenReturn(dto);
		
		try {
			service.profilWasUpdated(dto);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("DTO not found."), e);
		}
		
		dto.setName("pouet");
		dto.setAvatar("avapouet");
		ProfilDTO dtoSaved = service.profilWasUpdated(dto);
		Assert.assertEquals( "1", dtoSaved.getId() );
		Assert.assertEquals( "pouet", dtoSaved.getName() );
		Assert.assertEquals( "avapouet", dtoSaved.getAvatar());
		Assert.assertEquals( 0, dtoSaved.getPlayedGames() );
		Assert.assertEquals( 0, dtoSaved.getListenedMusics() );
		Assert.assertEquals( 0, dtoSaved.getFoundMusics() );
	}

	@Test
	public void profilWasPlayed() throws SqlException, NotFoundException {
		
		String name = "name";
		String avatar = "avatar";
		
		
		try {
			service.profilWasPlayed(null, false, false);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
		}
		
		
		ProfilDTO dto = new ProfilDTO(name, avatar);
		dto.setId("1");
		Mockito.when(dao.find(Mockito.any(ProfilDTO.class))).thenReturn(null, dto);
		Mockito.when(dao.update(Mockito.any(ProfilDTO.class))).thenReturn(dto);
		
		try {
			service.profilWasPlayed(dto, false, false);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("DTO not found."), e);
		}

		ProfilDTO dtoSaved = service.profilWasPlayed(dto, false, false);
		Assert.assertEquals( "1", dtoSaved.getId() );
		Assert.assertEquals( name, dtoSaved.getName() );
		Assert.assertEquals( 0, dtoSaved.getPlayedGames() );
		Assert.assertEquals( 1, dtoSaved.getListenedMusics() );
		Assert.assertEquals( 0, dtoSaved.getFoundMusics() );
		
		dtoSaved = service.profilWasPlayed(dto, true, false);
		Assert.assertEquals( "1", dtoSaved.getId() );
		Assert.assertEquals( name, dtoSaved.getName() );
		Assert.assertEquals( 1, dtoSaved.getPlayedGames() );
		Assert.assertEquals( 2, dtoSaved.getListenedMusics() );
		Assert.assertEquals( 0, dtoSaved.getFoundMusics() );
		
		dtoSaved = service.profilWasPlayed(dto, false, true);
		Assert.assertEquals( "1", dtoSaved.getId() );
		Assert.assertEquals( name, dtoSaved.getName() );
		Assert.assertEquals( 1, dtoSaved.getPlayedGames() );
		Assert.assertEquals( 3, dtoSaved.getListenedMusics() );
		Assert.assertEquals( 1, dtoSaved.getFoundMusics() );
	}
	
	@Test
	public void findAll() throws SqlException {

		ProfilDTO dto = new ProfilDTO("name", "avatar");
		Mockito.when(dao.findAll()).thenReturn( Arrays.asList(dto) );
		
		List<ProfilDTO> list = service.findAll();
		Assert.assertEquals( 1, list.size() );
	}

}
