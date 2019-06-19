package com.myssteriion.blindtest.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.dao.ProfilDAO;
import com.myssteriion.blindtest.db.exception.EntityManagerException;
import com.myssteriion.blindtest.model.dto.ProfilDTO;

public class ProfilServiceTest extends AbstractTest {

	@Mock
	private ProfilDAO dao;
	
	@InjectMocks
	private ProfilService service;
	
	
	
	@Test
	public void findAll() throws EntityManagerException {

		ProfilDTO dto = new ProfilDTO("name", "avatar");
		Mockito.when(dao.findAll()).thenReturn( Arrays.asList(dto) );
		
		List<ProfilDTO> list = service.findAll();
		Assert.assertEquals( 1, list.size() );
	}
	
	@Test
	public void saveOrUpdate() throws EntityManagerException {
		
		String name = "name";
		String avatar = "avatar";
		
		
		try {
			service.saveOrUpdate(null, avatar);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'name' est obligatoire."), e);
		}
		
		try {
			service.saveOrUpdate("", avatar);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'name' est obligatoire."), e);
		}
		
		try {
			service.saveOrUpdate(name, null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'avatar' est obligatoire."), e);
		}
		
		try {
			service.saveOrUpdate(name, "");
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'avatar' est obligatoire."), e);
		}
		
		
		ProfilDTO dto = new ProfilDTO(name, avatar);
		dto.setId("1");
		Mockito.when(dao.saveOrUpdate(Mockito.any(ProfilDTO.class))).thenReturn(dto);
		
		ProfilDTO dtoSaved = service.saveOrUpdate(name, avatar);
		Assert.assertEquals( "1", dtoSaved.getId() );
		Assert.assertEquals( name, dtoSaved.getName() );
		Assert.assertEquals( avatar, dtoSaved.getAvatar() );
		Assert.assertEquals( 0, dtoSaved.getPlayedGames() );
		Assert.assertEquals( 0, dtoSaved.getListenedMusics() );
		Assert.assertEquals( 0, dtoSaved.getFoundMusics() );
	}

}
