package com.myssteriion.blindtest.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.db.dao.ProfilDAO;
import com.myssteriion.blindtest.model.dto.ProfilDTO;

public class ProfilServiceTest extends AbstractTest {

	@Mock
	private ProfilDAO dao;
	
	@InjectMocks
	private ProfilService service;
	
	
	
	@Test
	public void save() throws SqlException {
		
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
		SqlException sql = new SqlException("sql");
		Mockito.when(dao.save(Mockito.any(ProfilDTO.class))).thenThrow(sql, sql).thenReturn(dto);
		
		ProfilDTO dtoSaved = service.save(dto, false);
		Assert.assertSame(dto, dtoSaved);
		
		try {
			service.save(dto, true);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (SqlException e) {
			verifyException(new SqlException("sql"), e);
		}

		dtoSaved = service.save(dto, false);
		Assert.assertEquals( "1", dtoSaved.getId() );
		Assert.assertEquals( name, dtoSaved.getName() );
		Assert.assertEquals( 0, dtoSaved.getPlayedGames() );
		Assert.assertEquals( 0, dtoSaved.getListenedMusics() );
		Assert.assertEquals( 0, dtoSaved.getFoundMusics() );
	}
	
	@Test
	public void update() throws SqlException {
		
		String name = "name";
		String avatar = "avatar";
		
		
		try {
			service.update(null, false);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
		}
		
		
		ProfilDTO dto = new ProfilDTO(name, avatar);
		dto.setId("1");
		SqlException sql = new SqlException("sql");
		Mockito.when(dao.update(Mockito.any(ProfilDTO.class))).thenThrow(sql, sql).thenReturn(dto);
		
		ProfilDTO dtoSaved = service.update(dto, false);
		Assert.assertSame(dto, dtoSaved);
		
		try {
			service.update(dto, true);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (SqlException e) {
			verifyException(new SqlException("sql"), e);
		}

		dtoSaved = service.update(dto, false);
		Assert.assertEquals( "1", dtoSaved.getId() );
		Assert.assertEquals( name, dtoSaved.getName() );
		Assert.assertEquals( 0, dtoSaved.getPlayedGames() );
		Assert.assertEquals( 0, dtoSaved.getListenedMusics() );
		Assert.assertEquals( 0, dtoSaved.getFoundMusics() );
	}

	@Test
	public void saveOrUpdate() throws SqlException {
		
		String name = "name";
		String avatar = "avatar";
		
		
		try {
			service.saveOrUpdate(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
		}
		
		
		ProfilDTO dto = new ProfilDTO(name, avatar);
		dto.setId("1");
		Mockito.when(dao.find(Mockito.any(ProfilDTO.class))).thenReturn(null, dto);
		Mockito.when(dao.save(Mockito.any(ProfilDTO.class))).thenReturn(dto);
		Mockito.when(dao.update(Mockito.any(ProfilDTO.class))).thenReturn(dto);
		
		ProfilDTO dtoSaved = service.saveOrUpdate(dto);
		Assert.assertEquals( "1", dtoSaved.getId() );
		Assert.assertEquals( name, dtoSaved.getName() );
		Assert.assertEquals( 0, dtoSaved.getPlayedGames() );
		Assert.assertEquals( 0, dtoSaved.getListenedMusics() );
		Assert.assertEquals( 0, dtoSaved.getFoundMusics() );

		dtoSaved = service.saveOrUpdate(dto);
		Assert.assertEquals( "1", dtoSaved.getId() );
		Assert.assertEquals( name, dtoSaved.getName() );
		Assert.assertEquals( 0, dtoSaved.getPlayedGames() );
		Assert.assertEquals( 0, dtoSaved.getListenedMusics() );
		Assert.assertEquals( 0, dtoSaved.getFoundMusics() );
	}

	@Test
	public void findAll() throws SqlException {

		ProfilDTO dto = new ProfilDTO("name", "avatar");
		Mockito.when(dao.findAll()).thenReturn( Arrays.asList(dto) );
		
		List<ProfilDTO> list = service.findAll();
		Assert.assertEquals( 1, list.size() );
	}

}
