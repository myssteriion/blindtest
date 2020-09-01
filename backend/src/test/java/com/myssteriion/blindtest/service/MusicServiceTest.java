package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.AbstractPowerMockTest;
import com.myssteriion.blindtest.model.common.Flux;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.entity.MusicEntity;
import com.myssteriion.blindtest.model.music.MusicFilter;
import com.myssteriion.blindtest.model.music.ThemeInfo;
import com.myssteriion.blindtest.persistence.dao.MusicDAO;
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
    
    private MusicService musicService;
    
    
    
    @Before
    public void before() {
        musicService = new MusicService(dao, configProperties);
    }
    
    
    
    @Test
    public void getMusicNumber() {
        
        Mockito.when(dao.countByTheme(Mockito.any(Theme.class))).thenReturn(2, 3);
        
        Theme theme = Theme.ANNEES_80;
        
        try {
            musicService.getMusicNumber(null);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'theme' est obligatoire."), e);
        }
        
        Assert.assertEquals( Integer.valueOf(2), musicService.getMusicNumber(theme) );
        Assert.assertEquals( Integer.valueOf(3), musicService.getMusicNumber(theme) );
    }
    
    @Test
    public void computeThemesInfo() {
        
        musicService = Mockito.spy( new MusicService(dao, configProperties) );
        MockitoAnnotations.initMocks(musicService);
        Mockito.doReturn(2, 3).when(musicService).getMusicNumber(Mockito.any(Theme.class));
        
        List<ThemeInfo> actual = musicService.computeThemesInfo();
        Assert.assertEquals( Theme.ANNEES_60, actual.get(0).getTheme() );
        Assert.assertEquals( Integer.valueOf(2), actual.get(0).getNbMusics() );
    
        Assert.assertEquals( Theme.ANNEES_70, actual.get(1).getTheme() );
        Assert.assertEquals( Integer.valueOf(3), actual.get(1).getNbMusics() );
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
        
        
        musicService = Mockito.spy( new MusicService(dao, configProperties) );
        MockitoAnnotations.initMocks(musicService);
        
        MusicEntity musicMock = new MusicEntity(name, theme);
        musicMock.setId(1);
        Mockito.doReturn(null).doReturn(musicMock).doReturn(null).when(musicService).find(Mockito.any(MusicEntity.class));
        Mockito.when(dao.save(Mockito.any(MusicEntity.class))).thenReturn(musicMock);
        
        MusicEntity music = new MusicEntity(name, theme);
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
        
        
        MusicEntity music = new MusicEntity(name, theme);
        try {
            musicService.update(music);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'music -> id' est obligatoire."), e);
        }
        
        
        MusicEntity musicStatMockNotSame = new MusicEntity(name, theme);
        musicStatMockNotSame.setId(2);
        MusicEntity musicStatMockSame = new MusicEntity(name, theme);
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
        
        MusicEntity musicMock = new MusicEntity("name", Theme.ANNEES_80);
        Mockito.when(dao.findByNameAndTheme(Mockito.anyString(), Mockito.any(Theme.class))).thenReturn(Optional.empty(), Optional.of(musicMock));
        
        
        try {
            musicService.find(null);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'music' est obligatoire."), e);
        }
        
        MusicEntity music = new MusicEntity("name", Theme.ANNEES_80);
        Assert.assertNull( musicService.find(music) );
        Assert.assertNotNull( musicService.find(music) );
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void random() throws Exception {
        
        MusicEntity expected = new MusicEntity("60_a", Theme.ANNEES_60);
        
        List<MusicEntity> allMusics = new ArrayList<>();
        allMusics.add(expected);
        allMusics.add( new MusicEntity("70_a", Theme.ANNEES_70) );
        
        try {
            musicService.random(null);
            Assert.fail("Doit lever une NotFoundException car le mock ne retrourne une liste vide.");
        }
        catch (NotFoundException e) {
            TestUtils.verifyException(new NotFoundException("No music found for themes ([ANNEES_60, ANNEES_70, ANNEES_80, ANNEES_90, ANNEES_2000, ANNEES_2010, SERIES_CINEMAS, DISNEY])."), e);
        }
        
        MusicFilter musicFilter = new MusicFilter().setThemes( Arrays.asList(Theme.ANNEES_60, Theme.ANNEES_70) );
        
        try {
            musicService.random(musicFilter);
            Assert.fail("Doit lever une NotFoundException car le mock ne retrourne une liste vide.");
        }
        catch (NotFoundException e) {
            TestUtils.verifyException(new NotFoundException("No music found for themes ([ANNEES_60, ANNEES_70])."), e);
        }
        
        
        MusicEntity expected2 = new MusicEntity("60_a", Theme.ANNEES_60);
        expected = new MusicEntity("70_a", Theme.ANNEES_70);
        
        allMusics = new ArrayList<>();
        allMusics.add(expected2);
        allMusics.add(expected);
        Mockito.when(dao.findByThemeIn(Mockito.anyList())).thenReturn(allMusics);
        
        Flux fluxMock = Mockito.mock(Flux.class);
        PowerMockito.whenNew(Flux.class).withArguments(File.class).thenReturn(fluxMock);
        
        MusicEntity music = musicService.random(null);
        Assert.assertTrue( music.equals(expected) || music.equals(expected2) );
        
        musicFilter = new MusicFilter().setThemes( Collections.singletonList(Theme.ANNEES_70) );
        music = musicService.random(musicFilter);
        Assert.assertTrue( music.equals(expected) || music.equals(expected2) );
        
        
        
        expected = new MusicEntity("70_a", Theme.ANNEES_70);
        allMusics = new ArrayList<>();
        allMusics.add(expected);
        
        Mockito.when(dao.findByThemeIn(Mockito.anyList())).thenReturn(allMusics);
        
        music = musicService.random(null);
        Assert.assertTrue( music.equals(expected) || music.equals(expected2) );
        
        music = musicService.random(null);
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
            musicService.checkEntity(new MusicEntity("name", null));
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'music -> theme' est obligatoire."), e);
        }
        
        musicService.checkEntity(new MusicEntity("name", Theme.ANNEES_60));
    }
    
}
