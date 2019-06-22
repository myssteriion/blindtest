package com.myssteriion.blindtest.service;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.db.dao.MusicDAO;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;

public class MusicServiceTest extends AbstractTest {

	@Mock
	private MusicDAO dao;
	
	@InjectMocks
	private MusicService service;
	
	
	
	@Test
	public void refresh() throws SqlException {
		service.refresh();
	}
	
	@Test
	public void save() throws SqlException {
		
		String name = "name";
		Theme theme = Theme.ANNEES_80;
		
		
		try {
			service.save(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
		}
		
		
		MusicDTO dto = new MusicDTO(name, theme);
		dto.setId("1");
		Mockito.when(dao.save(Mockito.any(MusicDTO.class))).thenReturn(dto);
		Mockito.when(dao.find(Mockito.any(MusicDTO.class))).thenReturn(null, dto);
		
		MusicDTO dtoSaved = service.save(dto);
		Assert.assertEquals( "1", dtoSaved.getId() );
		Assert.assertEquals( name, dtoSaved.getName() );
		Assert.assertEquals( theme, dtoSaved.getTheme() );
		Assert.assertEquals( 0, dtoSaved.getPlayed() );
		
		dtoSaved = service.save(dto);
		Assert.assertEquals( "1", dtoSaved.getId() );
		Assert.assertEquals( name, dtoSaved.getName() );
		Assert.assertEquals( theme, dtoSaved.getTheme() );
		Assert.assertEquals( 0, dtoSaved.getPlayed() );
	}

}
