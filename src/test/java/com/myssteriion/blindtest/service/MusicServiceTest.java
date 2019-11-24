package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.dao.MusicDAO;
import com.myssteriion.blindtest.model.common.Flux;
import com.myssteriion.blindtest.model.common.GameMode;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.spotify.SpotifyService;
import com.myssteriion.blindtest.tools.Tool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ MusicService.class, Tool.class })
public class MusicServiceTest extends AbstractTest {

	@Mock
	private MusicDAO dao;

	@Mock
	private SpotifyService spotifyService;

	@InjectMocks
	private MusicService musicService;



	@Before
	public void before() {
		musicService = new MusicService(dao, configProperties, spotifyService);
	}



	@Test
	public void refresh() throws ConflictException {

		File mockFile = Mockito.mock(File.class);
		Mockito.when(mockFile.isFile()).thenReturn(true);
		Mockito.when(mockFile.getName()).thenReturn("file.mp3");

		File mockDirectory = Mockito.mock(File.class);
		Mockito.when(mockDirectory.isFile()).thenReturn(false);

		PowerMockito.mockStatic(Tool.class);
		PowerMockito.when(Tool.getChildren(Mockito.any(File.class))).thenReturn(Arrays.asList(mockFile, mockDirectory));
		PowerMockito.when(Tool.hadAudioExtension(Mockito.anyString())).thenReturn(true);


		musicService = Mockito.spy( new MusicService(dao, configProperties, spotifyService) );
		MockitoAnnotations.initMocks(musicService);
		Mockito.doReturn(null).when(musicService).save(Mockito.any(MusicDTO.class));

		MusicDTO musicMock = new MusicDTO();
		Mockito.when(dao.findByNameAndThemeAndGameMode(Mockito.anyString(), Mockito.any(Theme.class), Mockito.any(GameMode.class))).thenReturn(Optional.empty(), Optional.of(musicMock));

		musicService.refresh();
		Mockito.verify(dao, Mockito.times(1)).save(Mockito.any(MusicDTO.class));
	}

	@Test
	public void save() throws ConflictException {

		String name = "name";
		Theme theme = Theme.ANNEES_80;


		try {
			musicService.save(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
		}


        musicService = Mockito.spy( new MusicService(dao, configProperties, spotifyService) );
		MockitoAnnotations.initMocks(musicService);

		MusicDTO musicDtoMock = new MusicDTO(name, theme);
		musicDtoMock.setId(1);
		Mockito.doReturn(null).doReturn(musicDtoMock).doReturn(null).when(musicService).find(Mockito.any(MusicDTO.class));
		Mockito.when(dao.save(Mockito.any(MusicDTO.class))).thenReturn(musicDtoMock);

		MusicDTO musicDto = new MusicDTO(name, theme);
		Assert.assertSame( musicDtoMock, musicService.save(musicDto) );

		try {
			musicService.save(musicDto);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (ConflictException e) {
			verifyException(new ConflictException("Dto already exists."), e);
		}

		MusicDTO musicSaved = musicService.save(musicDto);
		Assert.assertEquals( new Integer(1), musicSaved.getId() );
		Assert.assertEquals( name, musicSaved.getName() );
		Assert.assertEquals( theme, musicSaved.getTheme() );
		Assert.assertEquals( 0, musicSaved.getPlayed() );
	}

	@Test
	public void update() throws NotFoundException {

		String name = "name";
		Theme theme = Theme.ANNEES_80;


		try {
			musicService.update(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
		}


		MusicDTO music = new MusicDTO(name, theme);
		try {
			musicService.update(music);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto -> id' est obligatoire."), e);
		}


		MusicDTO musicStatMockNotSame = new MusicDTO(name, theme);
		musicStatMockNotSame.setId(2);
		MusicDTO musicStatMockSame = new MusicDTO(name, theme);
		musicStatMockSame.setId(1);
		Mockito.when(dao.findById(Mockito.anyInt())).thenReturn(Optional.empty(), Optional.of(musicStatMockNotSame),
                Optional.of(musicStatMockNotSame), Optional.of(musicStatMockSame));
		Mockito.when(dao.save(Mockito.any(MusicDTO.class))).thenReturn(music);

		try {
			music.setId(1);
			musicService.update(music);
			Assert.fail("Doit lever une DaoException car le mock throw.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("Dto not found."), e);
		}

		music.setId(1);
		MusicDTO musicDtoSaved = musicService.update(music);
		Assert.assertEquals( new Integer(1), musicDtoSaved.getId() );
		Assert.assertEquals( "name", musicDtoSaved.getName() );
	}

	@Test
	public void find() {

		MusicDTO musicMock = new MusicDTO("name", Theme.ANNEES_80);
		Mockito.when(dao.findByNameAndThemeAndGameMode(Mockito.anyString(), Mockito.any(Theme.class), Mockito.any(GameMode.class))).thenReturn(Optional.empty(), Optional.of(musicMock));


		try {
			musicService.find(null);
			Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
		}
		catch (IllegalArgumentException e) {
			verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
		}

		MusicDTO musicDto = new MusicDTO("name", Theme.ANNEES_80);
		Assert.assertNull( musicService.find(musicDto) );
		Assert.assertNotNull( musicService.find(musicDto) );
	}

	@SuppressWarnings("unchecked")
	@Test
	public void random() throws Exception {

		MusicDTO expected = new MusicDTO("60_a", Theme.ANNEES_60, 0);

		List<MusicDTO> allMusics = new ArrayList<>();
		allMusics.add(expected);
		allMusics.add( new MusicDTO("70_a", Theme.ANNEES_70, 1000000000) );
		Mockito.when(dao.findAll()).thenReturn(new ArrayList<>(), new ArrayList<>(), allMusics);

		try {
			musicService.random(null, GameMode.OFFLINE);
			Assert.fail("Doit lever une NotFoundException car le mock ne retrourne une liste vide.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("No music found for themes ([ANNEES_60, ANNEES_70, ANNEES_80, ANNEES_90, ANNEES_2000, ANNEES_2010, SERIES_CINEMAS, DISNEY, JEUX, CLASSIQUES])."), e);
		}

		try {
			musicService.random(Arrays.asList(Theme.ANNEES_60, Theme.ANNEES_70), GameMode.OFFLINE);
			Assert.fail("Doit lever une NotFoundException car le mock ne retrourne une liste vide.");
		}
		catch (NotFoundException e) {
			verifyException(new NotFoundException("No music found for themes ([ANNEES_60, ANNEES_70])."), e);
		}


		expected = new MusicDTO("70_a", Theme.ANNEES_70, 0);

		allMusics = new ArrayList<>();
		allMusics.add( new MusicDTO("60_a", Theme.ANNEES_60, 1000000000) );
		allMusics.add(expected);
		Mockito.when(dao.findByThemeInAndGameMode(Mockito.anyList(), Mockito.any(GameMode.class))).thenReturn(allMusics);


		musicService = PowerMockito.spy( new MusicService(dao, configProperties, spotifyService) );
		PowerMockito.doReturn(true).when(musicService, "offlineMusicExists", Mockito.any(MusicDTO.class));
		PowerMockito.doReturn(true).when(musicService, "onlineMusicExists", Mockito.any(MusicDTO.class));

		Flux fluxMock = Mockito.mock(Flux.class);
		PowerMockito.whenNew(Flux.class).withArguments(File.class).thenReturn(fluxMock);

		MusicDTO music = musicService.random(null, GameMode.OFFLINE);
		Assert.assertEquals(expected, music);

		music = musicService.random(Collections.singletonList(Theme.ANNEES_70), GameMode.OFFLINE);
		Assert.assertEquals(expected, music);
	}

}
