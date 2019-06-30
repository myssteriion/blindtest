package com.myssteriion.blindtest.service;

import java.util.ArrayList;
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
import com.myssteriion.blindtest.db.dao.MusicDAO;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;

public class MusicServiceTest extends AbstractTest {

	@Mock
	private MusicDAO dao;
	
	@InjectMocks
	private MusicService service;
	
	
	
	@Test
	public void refresh() throws SqlException, AlreadyExistsException {
		service.refresh();
	}
	
	@Test
	public void save() throws SqlException, AlreadyExistsException {
		
		String name = "name";
		Theme theme = Theme.ANNEES_80;
		
		
		try {
			service.save(null, false);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
		}
		
		
		MusicDTO dtoMock = new MusicDTO(name, theme);
		dtoMock.setId("1");
		Mockito.when(dao.find(Mockito.any(MusicDTO.class))).thenReturn(null, dtoMock);
		Mockito.when(dao.save(Mockito.any(MusicDTO.class))).thenReturn(dtoMock);
		
		MusicDTO dto = new MusicDTO(name, theme);
		Assert.assertSame( dtoMock, service.save(dto, false) );
		
		try {
			service.save(dto, true);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (AlreadyExistsException e) {
			verifyException(new AlreadyExistsException("DTO already exists."), e);
		}

		MusicDTO dtoSaved = service.save(dto, false);
		Assert.assertEquals( "1", dtoSaved.getId() );
		Assert.assertEquals( name, dtoSaved.getName() );
		Assert.assertEquals( theme, dtoSaved.getTheme() );
		Assert.assertEquals( 0, dtoSaved.getPlayed() );
	}
	
	@Test
	public void musicWasPlayed() throws SqlException, NotFoundException {
		
		String name = "name";
		Theme theme = Theme.ANNEES_80;
		
		
		try {
			service.musicWasPlayed(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
		}
		
		
		MusicDTO dto = new MusicDTO(name, theme);
		dto.setId("1");
		Mockito.when(dao.find(Mockito.any(MusicDTO.class))).thenReturn(null, dto);
		Mockito.when(dao.update(Mockito.any(MusicDTO.class))).thenReturn(dto);
		
		try {
			service.musicWasPlayed(dto);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("DTO not found."), e);
		}

		MusicDTO dtoSaved = service.musicWasPlayed(dto);
		Assert.assertEquals( "1", dtoSaved.getId() );
		Assert.assertEquals( name, dtoSaved.getName() );
		Assert.assertEquals( theme, dtoSaved.getTheme() );
		Assert.assertEquals( 1, dtoSaved.getPlayed() );
	}
	
	@Test
	public void next() throws SqlException {
		
		MusicDTO expected = new MusicDTO("60_a", Theme.ANNEES_60, 0);
		
		List<MusicDTO> allMusics = new ArrayList<>();
		allMusics.add(expected);
		allMusics.add( new MusicDTO("70_a", Theme.ANNEES_70, 1000000000) );
		Mockito.when(dao.findAll()).thenReturn(allMusics);
		
		MusicDTO dto = service.next();
		Assert.assertEquals(expected, dto);
		
		
		
		expected = new MusicDTO("70_a", Theme.ANNEES_70, 0);
		
		allMusics = new ArrayList<>();
		allMusics.add( new MusicDTO("60_a", Theme.ANNEES_60, 1000000000) );
		allMusics.add(expected);
		Mockito.when(dao.findAll()).thenReturn(allMusics);
		
		dto = service.next();
		Assert.assertEquals(expected, dto);
	}
	
}
