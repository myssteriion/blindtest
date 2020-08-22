package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.AbstractPowerMockTest;
import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.blindtest.model.common.Flux;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.entity.MusicEntity;
import com.myssteriion.blindtest.model.music.ThemeInfo;
import com.myssteriion.blindtest.persistence.dao.MusicDAO;
import com.myssteriion.blindtest.spotify.SpotifyException;
import com.myssteriion.blindtest.spotify.SpotifyService;
import com.myssteriion.utils.CommonUtils;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.NotFoundException;
import com.myssteriion.utils.test.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@PrepareForTest({ MusicService.class, CommonUtils.class })
public class MusicServiceTest extends AbstractPowerMockTest {
    
    @Mock
    private MusicDAO dao;
    
    @Mock
    private SpotifyService spotifyService;
    
    private MusicService musicService;
    
    
    
    @Before
    public void before() {
        musicService = new MusicService(dao, spotifyService, configProperties);
    }
    
    
    
    @Test
    public void getMusicNumber() {
        
        Mockito.when(dao.countByThemeAndConnectionMode(Mockito.any(Theme.class), Mockito.any(ConnectionMode.class))).thenReturn(2, 3, 5, 7);
        
        Theme theme = Theme.ANNEES_80;
        
        try {
            musicService.getMusicNumber(null, ConnectionMode.OFFLINE);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'theme' est obligatoire."), e);
        }
        
        try {
            musicService.getMusicNumber(theme, null);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'connectionMode' est obligatoire."), e);
        }
        
        Assert.assertEquals( Integer.valueOf(2), musicService.getMusicNumber(theme, ConnectionMode.OFFLINE) );
        Assert.assertEquals( Integer.valueOf(3), musicService.getMusicNumber(theme, ConnectionMode.ONLINE) );
        Assert.assertEquals( Integer.valueOf(5+7), musicService.getMusicNumber(theme, ConnectionMode.BOTH) );
    }
    
    @Test
    public void computeThemesInfo() {
        
        musicService = Mockito.spy( new MusicService(dao, spotifyService, configProperties) );
        MockitoAnnotations.initMocks(musicService);
        Mockito.doReturn(2, 3, 5, 7, 11).when(musicService).getMusicNumber(Mockito.any(Theme.class), Mockito.any(ConnectionMode.class));
        
        List<ThemeInfo> actual = musicService.computeThemesInfo();
        Assert.assertEquals( new ThemeInfo(Theme.ANNEES_60, 2, 3), actual.get(0) );
        Assert.assertEquals( new ThemeInfo(Theme.ANNEES_70, 5, 7), actual.get(1) );
        Assert.assertEquals( new ThemeInfo(Theme.ANNEES_80, 11, 11), actual.get(2) );
        Assert.assertEquals( new ThemeInfo(Theme.ANNEES_90, 11, 11), actual.get(3) );
    }
    
    @Test
    public void refresh() throws ConflictException {
        
        File mockFile = Mockito.mock(File.class);
        Mockito.when(mockFile.isFile()).thenReturn(true);
        Mockito.when(mockFile.getName()).thenReturn("file.mp3");
        
        File mockDirectory = Mockito.mock(File.class);
        Mockito.when(mockDirectory.isFile()).thenReturn(false);
        
        PowerMockito.mockStatic(CommonUtils.class);
        PowerMockito.when(CommonUtils.getChildren(Mockito.any(File.class))).thenReturn(Arrays.asList(mockFile, mockDirectory));
        PowerMockito.when(CommonUtils.hadAudioExtension(Mockito.anyString())).thenReturn(true);
        
        
        musicService = Mockito.spy( new MusicService(dao, spotifyService, configProperties) );
        MockitoAnnotations.initMocks(musicService);
        Mockito.doReturn(null).when(musicService).save(Mockito.any(MusicEntity.class));
        
        MusicEntity musicMock = new MusicEntity();
        Mockito.when(dao.findByNameAndThemeAndConnectionMode(Mockito.anyString(), Mockito.any(Theme.class), Mockito.any(ConnectionMode.class))).thenReturn(Optional.empty(), Optional.of(musicMock));
        
        musicService.refresh();
        Mockito.verify(dao, Mockito.times(1)).save(Mockito.any(MusicEntity.class));
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
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'music' est obligatoire."), e);
        }
        
        
        musicService = Mockito.spy( new MusicService(dao, spotifyService, configProperties) );
        MockitoAnnotations.initMocks(musicService);
        
        MusicEntity musicMock = new MusicEntity(name, theme, ConnectionMode.OFFLINE);
        musicMock.setId(1);
        Mockito.doReturn(null).doReturn(musicMock).doReturn(null).when(musicService).find(Mockito.any(MusicEntity.class));
        Mockito.when(dao.save(Mockito.any(MusicEntity.class))).thenReturn(musicMock);
        
        MusicEntity music = new MusicEntity(name, theme, ConnectionMode.OFFLINE);
        Assert.assertSame(musicMock, musicService.save(music) );
        
        try {
            musicService.save(music);
            Assert.fail("Doit lever une DaoException car le mock throw.");
        }
        catch (ConflictException e) {
            TestUtils.verifyException(new ConflictException("music already exists."), e);
        }
        
        MusicEntity musicSaved = musicService.save(music);
        Assert.assertEquals( Integer.valueOf(1), musicSaved.getId() );
        Assert.assertEquals( name, musicSaved.getName() );
        Assert.assertEquals( theme, musicSaved.getTheme() );
        Assert.assertEquals( 0, musicSaved.getPlayed() );
    }
    
    @Test
    public void update() throws NotFoundException, ConflictException {
        
        String name = "name";
        Theme theme = Theme.ANNEES_80;
        
        
        try {
            musicService.update(null);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'music' est obligatoire."), e);
        }
        
        
        MusicEntity music = new MusicEntity(name, theme, ConnectionMode.OFFLINE);
        try {
            musicService.update(music);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'music -> id' est obligatoire."), e);
        }
        
        
        MusicEntity musicStatMockNotSame = new MusicEntity(name, theme, ConnectionMode.OFFLINE);
        musicStatMockNotSame.setId(2);
        MusicEntity musicStatMockSame = new MusicEntity(name, theme, ConnectionMode.OFFLINE);
        musicStatMockSame.setId(1);
        Mockito.when(dao.findById(Mockito.anyInt())).thenReturn(Optional.empty(), Optional.of(musicStatMockNotSame),
                Optional.of(musicStatMockNotSame), Optional.of(musicStatMockSame));
        Mockito.when(dao.save(Mockito.any(MusicEntity.class))).thenReturn(music);
        
        try {
            music.setId(1);
            musicService.update(music);
            Assert.fail("Doit lever une DaoException car le mock throw.");
        }
        catch (NotFoundException e) {
            TestUtils.verifyException(new NotFoundException("music not found."), e);
        }
        
        music.setId(1);
        MusicEntity musicSaved = musicService.update(music);
        Assert.assertEquals( Integer.valueOf(1), musicSaved.getId() );
        Assert.assertEquals( "name", musicSaved.getName() );
    }
    
    @Test
    public void find() {
        
        MusicEntity musicMock = new MusicEntity("name", Theme.ANNEES_80, ConnectionMode.OFFLINE);
        Mockito.when(dao.findByNameAndThemeAndConnectionMode(Mockito.anyString(), Mockito.any(Theme.class), Mockito.any(ConnectionMode.class))).thenReturn(Optional.empty(), Optional.of(musicMock));
        
        
        try {
            musicService.find(null);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'music' est obligatoire."), e);
        }
        
        MusicEntity music = new MusicEntity("name", Theme.ANNEES_80, ConnectionMode.OFFLINE);
        Assert.assertNull( musicService.find(music) );
        Assert.assertNotNull( musicService.find(music) );
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void random() throws Exception {
        
        MusicEntity expected = new MusicEntity("60_a", Theme.ANNEES_60, ConnectionMode.OFFLINE);
        
        List<MusicEntity> allMusics = new ArrayList<>();
        allMusics.add(expected);
        allMusics.add( new MusicEntity("70_a", Theme.ANNEES_70, ConnectionMode.OFFLINE) );
        
        try {
            musicService.random(null, null, ConnectionMode.OFFLINE);
            Assert.fail("Doit lever une NotFoundException car le mock ne retrourne une liste vide.");
        }
        catch (NotFoundException e) {
            TestUtils.verifyException(new NotFoundException("No music found for themes ([ANNEES_60, ANNEES_70, ANNEES_80, ANNEES_90, ANNEES_2000, ANNEES_2010, SERIES_CINEMAS, DISNEY])."), e);
        }
        
        try {
            musicService.random(Arrays.asList(Theme.ANNEES_60, Theme.ANNEES_70), null, ConnectionMode.OFFLINE);
            Assert.fail("Doit lever une NotFoundException car le mock ne retrourne une liste vide.");
        }
        catch (NotFoundException e) {
            TestUtils.verifyException(new NotFoundException("No music found for themes ([ANNEES_60, ANNEES_70])."), e);
        }
        
        
        MusicEntity expected2 = new MusicEntity("60_a", Theme.ANNEES_60, ConnectionMode.OFFLINE);
        expected = new MusicEntity("70_a", Theme.ANNEES_70, ConnectionMode.OFFLINE);
        
        allMusics = new ArrayList<>();
        allMusics.add(expected2);
        allMusics.add(expected);
        Mockito.when(dao.findByThemeInAndConnectionModeIn(Mockito.anyList(), Mockito.anyList())).thenReturn(allMusics);
        
        
        musicService = PowerMockito.spy( new MusicService(dao, spotifyService, configProperties) );
        PowerMockito.doReturn(true).when(musicService, "offlineMusicExists", Mockito.any(MusicEntity.class));
        PowerMockito.doReturn(true).when(musicService, "onlineMusicExists", Mockito.any(MusicEntity.class));
        
        Flux fluxMock = Mockito.mock(Flux.class);
        PowerMockito.whenNew(Flux.class).withArguments(File.class).thenReturn(fluxMock);
        
        MusicEntity music = musicService.random(null, null, ConnectionMode.OFFLINE);
        Assert.assertTrue( music.equals(expected) || music.equals(expected2) );
        
        music = musicService.random(Collections.singletonList(Theme.ANNEES_70), null, ConnectionMode.OFFLINE);
        Assert.assertTrue( music.equals(expected) || music.equals(expected2) );
        
        
        
        expected = new MusicEntity("70_a", Theme.ANNEES_70, ConnectionMode.ONLINE);
        allMusics = new ArrayList<>();
        allMusics.add(expected);
        
        Mockito.when(dao.findByThemeInAndConnectionModeIn(Mockito.anyList(), Mockito.anyList())).thenReturn(allMusics);
        Mockito.doThrow(new SpotifyException("se")).doNothing().when(spotifyService).testConnection();
        
        try {
            musicService.random(null, null, ConnectionMode.ONLINE);
            Assert.fail("Doit lever une SpotifyException car le mock throw.");
        }
        catch (SpotifyException e) {
            TestUtils.verifyException(new SpotifyException("se"), e);
        }
        
        music = musicService.random(null, null, ConnectionMode.ONLINE);
        Assert.assertTrue( music.equals(expected) || music.equals(expected2) );
        
        music = musicService.random(null, null, ConnectionMode.ONLINE);
        Assert.assertTrue( music.equals(expected) || music.equals(expected2) );
    }
    
    @Test
    public void checkEntity() {
        
        try {
            musicService.checkEntity(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'music' est obligatoire."), e);
        }
        
        try {
            musicService.checkEntity(new MusicEntity());
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'music -> name' est obligatoire."), e);
        }
    
        try {
            musicService.checkEntity(new MusicEntity("name", null, null));
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'music -> theme' est obligatoire."), e);
        }
    
        try {
            musicService.checkEntity(new MusicEntity("name", Theme.ANNEES_60, null));
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'music -> connectionMode' est obligatoire."), e);
        }
        
        musicService.checkEntity(new MusicEntity("name", Theme.ANNEES_60, ConnectionMode.OFFLINE));
    }
    
}
