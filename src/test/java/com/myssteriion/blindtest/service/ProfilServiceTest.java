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
	public void save() throws EntityManagerException {
		
		String name = "name";
		String avatar = "avatar";
		
		
		try {
			service.save(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
		}
		
		ProfilDTO dto = new ProfilDTO(name, avatar);
		dto.setId("1");
		Mockito.when(dao.save(Mockito.any(ProfilDTO.class))).thenReturn(dto);
		
		ProfilDTO dtoSaved = service.save(dto);
		Assert.assertEquals( "1", dtoSaved.getId() );
		Assert.assertEquals( name, dtoSaved.getName() );
		Assert.assertEquals( avatar, dtoSaved.getAvatar() );
		Assert.assertEquals( 0, dtoSaved.getPlayedGames() );
		Assert.assertEquals( 0, dtoSaved.getListenedMusics() );
		Assert.assertEquals( 0, dtoSaved.getFoundMusics() );
	}

	@Test
	public void findAll() throws EntityManagerException {

		ProfilDTO dto = new ProfilDTO("name", "avatar");
		Mockito.when(dao.findAll()).thenReturn( Arrays.asList(dto) );
		
		List<ProfilDTO> list = service.findAll();
		Assert.assertEquals( 1, list.size() );
	}

}
