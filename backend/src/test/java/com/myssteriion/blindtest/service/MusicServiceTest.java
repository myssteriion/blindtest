package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Flux;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.entity.MusicEntity;
import com.myssteriion.blindtest.model.music.MusicFilter;
import com.myssteriion.blindtest.model.music.ThemeInfo;
import com.myssteriion.blindtest.persistence.dao.MusicDAO;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.NotFoundException;
import com.myssteriion.utils.test.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class MusicServiceTest extends AbstractTest {
    
    @Mock
    private MusicDAO dao;
    
    private MusicService musicService;
    
    
    
    @BeforeEach
    void before() {
        musicService = new MusicService(dao, configProperties);
    }
    
    
    
    @Test
    void getMusicNumber() {
        
        TestUtils.assertThrowMandatoryField("theme", () -> musicService.getMusicNumber(null) );
        
        Mockito.when(dao.countByTheme(Mockito.any(Theme.class))).thenReturn(2, 3);
        
        Theme theme = Theme.ANNEES_80;
        Assertions.assertEquals( Integer.valueOf(2), musicService.getMusicNumber(theme) );
        Assertions.assertEquals( Integer.valueOf(3), musicService.getMusicNumber(theme) );
    }
    
    @Test
    void computeThemesInfo() {
        
        musicService = Mockito.spy( new MusicService(dao, configProperties) );
        MockitoAnnotations.initMocks(musicService);
        Mockito.doReturn(2, 3).when(musicService).getMusicNumber(Mockito.any(Theme.class));
        
        List<ThemeInfo> actual = musicService.computeThemesInfo();
        Assertions.assertEquals( Theme.ANNEES_60, actual.get(0).getTheme() );
        Assertions.assertEquals( Integer.valueOf(2), actual.get(0).getNbMusics() );
        
        Assertions.assertEquals( Theme.ANNEES_70, actual.get(1).getTheme() );
        Assertions.assertEquals( Integer.valueOf(3), actual.get(1).getNbMusics() );
    }
    
    @Test
    void save() throws ConflictException {
        
        TestUtils.assertThrowMandatoryField("music", () -> musicService.save(null) );
        
        String name = "name";
        Theme theme = Theme.ANNEES_80;
        
        musicService = Mockito.spy( new MusicService(dao, configProperties) );
        MockitoAnnotations.initMocks(musicService);
        
        MusicEntity musicMock = new MusicEntity(name, theme);
        musicMock.setId(1);
        Mockito.doReturn(null).doReturn(musicMock).doReturn(null).when(musicService).find(Mockito.any(MusicEntity.class));
        Mockito.when(dao.save(Mockito.any(MusicEntity.class))).thenReturn(musicMock);
        
        MusicEntity music = new MusicEntity(name, theme);
        Assertions.assertSame(musicMock, musicService.save(music) );
        
        TestUtils.assertThrow( ConflictException.class, "music already exists.", () -> musicService.save(music) );
        
        MusicEntity musicSaved = musicService.save(music);
        Assertions.assertEquals( Integer.valueOf(1), musicSaved.getId() );
        Assertions.assertEquals( name, musicSaved.getName() );
        Assertions.assertEquals( theme, musicSaved.getTheme() );
        Assertions.assertEquals( 0, musicSaved.getPlayed() );
    }
    
    @Test
    void update() throws NotFoundException, ConflictException {
        
        TestUtils.assertThrowMandatoryField("music", () -> musicService.update(null) );
        
        String name = "name";
        Theme theme = Theme.ANNEES_80;
        
        MusicEntity music = new MusicEntity(name, theme);
        TestUtils.assertThrowMandatoryField("music -> id", () -> musicService.update(music) );
        
        MusicEntity musicStatMockNotSame = new MusicEntity(name, theme);
        musicStatMockNotSame.setId(2);
        MusicEntity musicStatMockSame = new MusicEntity(name, theme);
        musicStatMockSame.setId(1);
        Mockito.when(dao.findById(Mockito.anyInt())).thenReturn(Optional.empty(), Optional.of(musicStatMockNotSame),
                Optional.of(musicStatMockNotSame), Optional.of(musicStatMockSame));
        Mockito.when(dao.save(Mockito.any(MusicEntity.class))).thenReturn(music);
        
        music.setId(1);
        TestUtils.assertThrow( NotFoundException.class, "music not found.", () -> musicService.update(music) );
        
        music.setId(1);
        MusicEntity musicSaved = musicService.update(music);
        Assertions.assertEquals( Integer.valueOf(1), musicSaved.getId() );
        Assertions.assertEquals( "name", musicSaved.getName() );
    }
    
    @Test
    void find() {
        
        TestUtils.assertThrowMandatoryField("music", () -> musicService.find(null) );
        
        MusicEntity musicMock = new MusicEntity("name", Theme.ANNEES_80);
        Mockito.when(dao.findByNameAndTheme(Mockito.anyString(), Mockito.any(Theme.class))).thenReturn(Optional.empty(), Optional.of(musicMock));
        
        MusicEntity music = new MusicEntity("name", Theme.ANNEES_80);
        Assertions.assertNull( musicService.find(music) );
        Assertions.assertNotNull( musicService.find(music) );
    }
    
    @SuppressWarnings("unchecked")
    @Test
    void random() throws Exception {
        
        TestUtils.assertThrow( NotFoundException.class, "No music found for themes ([ANNEES_60, ANNEES_70, " +
                        "ANNEES_80, ANNEES_90, ANNEES_2000, ANNEES_2010, SERIES_CINEMAS, DISNEY]).",
                () -> musicService.random(null) );
        
        
        MusicEntity expected = new MusicEntity("60_a", Theme.ANNEES_60);
        
        List<MusicEntity> allMusics = new ArrayList<>();
        allMusics.add(expected);
        allMusics.add( new MusicEntity("70_a", Theme.ANNEES_70) );
        
        MusicFilter musicFilter = new MusicFilter().setThemes( Arrays.asList(Theme.ANNEES_60, Theme.ANNEES_70) );
        TestUtils.assertThrow( NotFoundException.class, "No music found for themes ([ANNEES_60, ANNEES_70]).",
                () -> musicService.random(musicFilter) );
        
        
        MusicEntity expected2 = new MusicEntity("60_a", Theme.ANNEES_60);
        expected = new MusicEntity("70_a", Theme.ANNEES_70);
        
        allMusics = new ArrayList<>();
        allMusics.add(expected2);
        allMusics.add(expected);
        Mockito.when(dao.findByThemeIn(Mockito.anyList())).thenReturn(allMusics);
        
        Flux fluxMock = Mockito.mock(Flux.class);
        PowerMockito.whenNew(Flux.class).withArguments(File.class).thenReturn(fluxMock);
        
        MusicEntity music = musicService.random(null);
        Assertions.assertTrue( music.equals(expected) || music.equals(expected2) );
        
        musicFilter.setThemes( Collections.singletonList(Theme.ANNEES_70) );
        music = musicService.random(musicFilter);
        Assertions.assertTrue( music.equals(expected) || music.equals(expected2) );
        
        
        
        expected = new MusicEntity("70_a", Theme.ANNEES_70);
        allMusics = new ArrayList<>();
        allMusics.add(expected);
        
        Mockito.when(dao.findByThemeIn(Mockito.anyList())).thenReturn(allMusics);
        
        music = musicService.random(null);
        Assertions.assertTrue( music.equals(expected) || music.equals(expected2) );
        
        music = musicService.random(null);
        Assertions.assertTrue( music.equals(expected) || music.equals(expected2) );
    }
    
    @Test
    void checkEntity() {
        
        TestUtils.assertThrowMandatoryField("music", () -> musicService.checkEntity(null) );
        TestUtils.assertThrowMandatoryField("music -> name", () -> musicService.checkEntity(new MusicEntity()) );
        TestUtils.assertThrowMandatoryField("music -> theme", () -> musicService.checkEntity(new MusicEntity("name", null)) );
        
        musicService.checkEntity(new MusicEntity("name", Theme.ANNEES_60));
    }
    
}
