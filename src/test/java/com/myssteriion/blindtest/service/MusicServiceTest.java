package com.myssteriion.blindtest.service;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.dao.MusicDAO;
import com.myssteriion.blindtest.db.exception.EntityManagerException;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;

public class MusicServiceTest extends AbstractTest {

	@Mock
	private MusicDAO dao;
	
	@InjectMocks
	private MusicService service;
	
	
	
	@Test
	public void refresh() throws EntityManagerException {
		service.refresh();
	}
	
	@Test
	public void saveOrUpdate() throws EntityManagerException {
		
		String name = "name";
		Theme theme = Theme.ANNEES_80;
		
		
		try {
			service.saveOrUpdate(null, theme);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'name' est obligatoire."), e);
		}
		
		try {
			service.saveOrUpdate("", theme);
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
			verifyException(new IllegalArgumentException("Le champ 'theme' est obligatoire."), e);
		}
		
		
		MusicDTO dto = new MusicDTO(name, theme);
		dto.setId("1");
		Mockito.when(dao.saveOrUpdate(Mockito.any(MusicDTO.class))).thenReturn(dto);
		
		MusicDTO dtoSaved = service.saveOrUpdate(name, theme);
		Assert.assertEquals( "1", dtoSaved.getId() );
		Assert.assertEquals( name, dtoSaved.getName() );
		Assert.assertEquals( theme, dtoSaved.getTheme() );
		Assert.assertEquals( 0, dtoSaved.getNbPlayed() );
	}

}
