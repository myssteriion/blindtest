package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.AbstractPowerMockTest;
import com.myssteriion.blindtest.model.common.Flux;
import com.myssteriion.blindtest.model.entity.AvatarEntity;
import com.myssteriion.blindtest.persistence.dao.AvatarDAO;
import com.myssteriion.utils.CommonUtils;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.NotFoundException;
import com.myssteriion.utils.test.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@PrepareForTest({ AvatarService.class, CommonUtils.class })
public class AvatarServiceTest extends AbstractPowerMockTest {
    
    @Mock
    private AvatarDAO dao;
    
    private AvatarService avatarService;
    
    
    
    @Before
    public void before() {
        avatarService = Mockito.spy( new AvatarService(dao, configProperties) );
    }
    
    
    
    @Test
    public void refresh() throws Exception {
        
        File mockFile = Mockito.mock(File.class);
        Mockito.when(mockFile.isFile()).thenReturn(true);
        Mockito.when(mockFile.getName()).thenReturn("file.png");
        
        File mockDirectory = Mockito.mock(File.class);
        Mockito.when(mockDirectory.isFile()).thenReturn(false);
        
        PowerMockito.mockStatic(CommonUtils.class);
        PowerMockito.when(CommonUtils.getChildren(Mockito.any(File.class))).thenReturn(Arrays.asList(mockFile, mockDirectory));
        PowerMockito.when(CommonUtils.hadImageExtension(Mockito.anyString())).thenReturn(true);
        
        
        Mockito.doReturn(null).when(avatarService).save(Mockito.any(AvatarEntity.class));
        
        AvatarEntity avatarMock = new AvatarEntity();
        Mockito.when(dao.findByName(Mockito.anyString())).thenReturn(Optional.empty(), Optional.of(avatarMock));
        
        avatarService.refresh();
        Mockito.verify(dao, Mockito.times(1)).save(Mockito.any(AvatarEntity.class));
    }
    
    @Test
    public void needRefresh() throws Exception {
        
        File mockFile = Mockito.mock(File.class);
        
        PowerMockito.mockStatic(CommonUtils.class);
        PowerMockito.when(CommonUtils.getChildren(Mockito.any(File.class))).thenReturn(Arrays.asList(mockFile));
        
        Mockito.when(dao.count()).thenReturn(0l, 1l);
        
        Flux fluxMock = Mockito.mock(Flux.class);
        Mockito.when(fluxMock.isFileExists()).thenReturn(false, true);
        AvatarEntity avatarMock = Mockito.mock(AvatarEntity.class);
        Mockito.when(avatarMock.getFlux()).thenReturn(fluxMock);
        
        Page<AvatarEntity> pageMock = new PageImpl<>( Arrays.asList(avatarMock));
        Mockito.when(dao.findAll(Mockito.any(Pageable.class))).thenReturn(pageMock);
        
        avatarService = PowerMockito.spy( new AvatarService(dao, configProperties));
        PowerMockito.doNothing().when(avatarService, "createFlux", Mockito.any(AvatarEntity.class));
        
        Assert.assertTrue( avatarService.needRefresh() );
        Assert.assertTrue( avatarService.needRefresh() );
        Assert.assertFalse( avatarService.needRefresh() );
    }
    
    @Test
    public void save() throws ConflictException {
        
        String name = "name";
        
        try {
            avatarService.save(null);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'avatar' est obligatoire."), e);
        }
        
        try {
            avatarService.save(new AvatarEntity());
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'avatar -> name' est obligatoire."), e);
        }
        
        
        AvatarEntity avatarMock = new AvatarEntity(name);
        avatarMock.setId(1);
        Mockito.doReturn(null).doReturn(avatarMock).doReturn(null).when(avatarService).find(Mockito.any(AvatarEntity.class));
        Mockito.when(dao.save(Mockito.any(AvatarEntity.class))).thenReturn(avatarMock);
        
        AvatarEntity avatar = new AvatarEntity(name);
        Assert.assertSame(avatarMock, avatarService.save(avatar) );
        
        try {
            avatarService.save(avatar);
            Assert.fail("Doit lever une DaoException car le mock throw.");
        }
        catch (ConflictException e) {
            TestUtils.verifyException(new ConflictException("avatar already exists."), e);
        }
        
        AvatarEntity avatarSaved = avatarService.save(avatar);
        Assert.assertEquals( Integer.valueOf(1), avatarSaved.getId() );
        Assert.assertEquals( name, avatarSaved.getName() );
    }
    
    @Test
    public void update() throws NotFoundException, ConflictException {
        
        String name = "name";
        
        try {
            avatarService.update(null);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'avatar' est obligatoire."), e);
        }
        
        try {
            avatarService.update(new AvatarEntity());
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'avatar -> name' est obligatoire."), e);
        }
        
        
        AvatarEntity avatar = new AvatarEntity(name);
        try {
            avatarService.update(avatar);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'avatar -> id' est obligatoire."), e);
        }
        
        
        AvatarEntity avatarStatMockNotSame = new AvatarEntity(name);
        avatarStatMockNotSame.setId(2);
        AvatarEntity avatarStatMockSame = new AvatarEntity(name);
        avatarStatMockSame.setId(1);
        Mockito.when(dao.findById(Mockito.anyInt())).thenReturn(Optional.empty(), Optional.of(avatarStatMockNotSame),
                Optional.of(avatarStatMockNotSame), Optional.of(avatarStatMockSame));
        Mockito.when(dao.save(Mockito.any(AvatarEntity.class))).thenReturn(avatar);
        
        try {
            avatar.setId(1);
            avatarService.update(avatar);
            Assert.fail("Doit lever une DaoException car le mock throw.");
        }
        catch (NotFoundException e) {
            TestUtils.verifyException(new NotFoundException("avatar not found."), e);
        }
        
        avatar.setId(1);
        AvatarEntity avatarSaved = avatarService.update(avatar);
        Assert.assertEquals( Integer.valueOf(1), avatarSaved.getId() );
        Assert.assertEquals( "name", avatarSaved.getName() );
    }
    
    @Test
    public void find() {
        
        AvatarEntity avatarMock = new AvatarEntity("name");
        Mockito.when(dao.findByName(Mockito.anyString())).thenReturn(Optional.empty(), Optional.of(avatarMock));
        Mockito.when(dao.findById(Mockito.anyInt())).thenReturn(Optional.of(avatarMock));
        
        try {
            avatarService.find(null);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'avatar' est obligatoire."), e);
        }
        
        try {
            avatarService.find(new AvatarEntity());
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'avatar -> name' est obligatoire."), e);
        }
        
        
        AvatarEntity avatar = new AvatarEntity("name");
        Assert.assertNull( avatarService.find(avatar) );
        Assert.assertEquals(avatarMock, avatarService.find(avatar) );
        Mockito.verify(dao, Mockito.times(2)).findByName(Mockito.anyString());
        
        avatar = new AvatarEntity().setId(1);
        Assert.assertEquals(avatarMock, avatarService.find(avatar) );
        Mockito.verify(dao, Mockito.times(1)).findById(Mockito.anyInt());
    }
    
    @Test
    public void findAllBySearchName() {
        
        AvatarEntity avatar = new AvatarEntity("name");
        Mockito.when(dao.findAllByNameContainingIgnoreCase(Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn( new PageImpl<>(Collections.singletonList(avatar)));
        
        Assert.assertEquals( new PageImpl<>(Collections.singletonList(avatar)), avatarService.findAllBySearchName(null, 0, 1) );
        Assert.assertEquals( new PageImpl<>(Collections.singletonList(avatar)), avatarService.findAllBySearchName("", 0, 1) );
    }
    
    @Test
    public void checkEntity() {
        
        try {
            avatarService.checkEntity(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'avatar' est obligatoire."), e);
        }
        
        try {
            avatarService.checkEntity(new AvatarEntity());
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'avatar -> name' est obligatoire."), e);
        }
        
        avatarService.checkEntity(new AvatarEntity("name"));
    }
    
}