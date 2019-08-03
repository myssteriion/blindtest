package com.myssteriion.blindtest.service;

import java.util.ArrayList;
import java.util.List;

import com.myssteriion.blindtest.model.dto.MusicDTO;
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
	private MusicDAO musicDao;
	
	@InjectMocks
	private MusicService musicService;
	
	
	
	@Test
	public void refresh() throws SqlException, AlreadyExistsException {
		musicService.refresh();
	}
	
	@Test
	public void save() throws SqlException, AlreadyExistsException {
		
		String name = "name";
		Theme theme = Theme.ANNEES_80;
		
		
		try {
			musicService.save(null, false);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'musicDto' est obligatoire."), e);
		}
		
		
		MusicDTO musicDtoMock = new MusicDTO(name, theme);
		musicDtoMock.setId(1);
		Mockito.when(musicDao.find(Mockito.any(MusicDTO.class))).thenReturn(null, musicDtoMock);
		Mockito.when(musicDao.save(Mockito.any(MusicDTO.class))).thenReturn(musicDtoMock);
		
		MusicDTO musicDto = new MusicDTO(name, theme);
		Assert.assertSame( musicDtoMock, musicService.save(musicDto, false) );
		
		try {
			musicService.save(musicDto, true);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (AlreadyExistsException e) {
			verifyException(new AlreadyExistsException("musicDto already exists."), e);
		}

		MusicDTO musicDtoSaved = musicService.save(musicDto, false);
		Assert.assertEquals( new Integer(1), musicDtoSaved.getId() );
		Assert.assertEquals( name, musicDtoSaved.getName() );
		Assert.assertEquals( theme, musicDtoSaved.getTheme() );
		Assert.assertEquals( 0, musicDtoSaved.getPlayed() );
	}

	@Test
	public void update() throws SqlException, NotFoundException, AlreadyExistsException {

		String name = "name";
		Theme theme = Theme.ANNEES_80;


		try {
			musicService.update(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'musicDto' est obligatoire."), e);
		}


		MusicDTO musicDto = new MusicDTO(name, theme);
		try {
			musicService.update(musicDto);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'musicDto -> id' est obligatoire."), e);
		}


		MusicDTO musicStatDtoMockNotSame = new MusicDTO(name, theme);
		musicStatDtoMockNotSame.setId(2);
		MusicDTO musicStatDtoMockSame = new MusicDTO(name, theme);
		musicStatDtoMockSame.setId(1);
		Mockito.when(musicDao.find(Mockito.any(MusicDTO.class))).thenReturn(null, musicStatDtoMockNotSame, musicStatDtoMockNotSame, musicStatDtoMockSame);
		Mockito.when(musicDao.update(Mockito.any(MusicDTO.class))).thenReturn(musicDto);

		try {
			musicDto.setId(1);
			musicService.update(musicDto);
			Assert.fail("Doit lever une SqlException car le mock throw.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("musicDto not found."), e);
		}

		musicDto.setId(1);
		MusicDTO musicDtoSaved = musicService.update(musicDto);
		Assert.assertEquals( new Integer(1), musicDtoSaved.getId() );
		Assert.assertEquals( "name", musicDtoSaved.getName() );
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void random() throws SqlException, NotFoundException {
		
		MusicDTO expected = new MusicDTO("60_a", Theme.ANNEES_60, 0);
		
		List<MusicDTO> allMusics = new ArrayList<>();
		allMusics.add(expected);
		allMusics.add( new MusicDTO("70_a", Theme.ANNEES_70, 1000000000) );
		Mockito.when(musicDao.findAll()).thenReturn(new ArrayList<>(), allMusics);

		try {
			musicService.random();
			Assert.fail("Doit lever une v car le mock ne retrourne une liste vide.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("no music found"), e);
		}


		MusicDTO musicDto = musicService.random();
		Assert.assertEquals(expected, musicDto);
		

		expected = new MusicDTO("70_a", Theme.ANNEES_70, 0);
		
		allMusics = new ArrayList<>();
		allMusics.add( new MusicDTO("60_a", Theme.ANNEES_60, 1000000000) );
		allMusics.add(expected);
		Mockito.when(musicDao.findAll()).thenReturn(allMusics);
		
		musicDto = musicService.random();
		Assert.assertEquals(expected, musicDto);
	}
	
}
