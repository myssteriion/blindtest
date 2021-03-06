package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Flux;
import com.myssteriion.blindtest.model.dto.AvatarDTO;
import com.myssteriion.blindtest.persistence.dao.AvatarDAO;
import com.myssteriion.utils.CommonUtils;
import com.myssteriion.utils.exception.CustomRuntimeException;
import com.myssteriion.utils.rest.exception.ConflictException;
import com.myssteriion.utils.rest.exception.NotFoundException;
import com.myssteriion.utils.test.TestUtils;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ AvatarService.class, CommonUtils.class })
public class AvatarServiceTest extends AbstractTest {
    
    @Mock
    private AvatarDAO dao;
    
    @InjectMocks
    private AvatarService avatarService;
    
    
    
    @Before
    public void before() {
        avatarService = new AvatarService(dao);
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
        
        
        avatarService = Mockito.spy( new AvatarService(dao) );
        MockitoAnnotations.initMocks(avatarService);
        Mockito.doReturn(null).when(avatarService).save(Mockito.any(AvatarDTO.class));
        
        AvatarDTO avatarMock = new AvatarDTO();
        Mockito.when(dao.findByName(Mockito.anyString())).thenReturn(Optional.empty(), Optional.of(avatarMock));
        
        avatarService.refresh();
        Mockito.verify(dao, Mockito.times(1)).save(Mockito.any(AvatarDTO.class));
    }
    
    @Test
    public void needRefresh() {
        
        File mockFile = Mockito.mock(File.class);
        
        PowerMockito.mockStatic(CommonUtils.class);
        PowerMockito.when(CommonUtils.getChildren(Mockito.any(File.class))).thenReturn(Arrays.asList(mockFile));
        
        Mockito.when(dao.count()).thenReturn(0l, 1l);
        
        Flux fluxMock = Mockito.mock(Flux.class);
        Mockito.when(fluxMock.isFileExists()).thenReturn(false, true);
        AvatarDTO avatarMock = Mockito.mock(AvatarDTO.class);
        Mockito.when(avatarMock.getFlux()).thenReturn(fluxMock);
        
        Page<AvatarDTO> pageMock = new PageImpl<>( Arrays.asList(avatarMock));
        Mockito.when(dao.findAll(Mockito.any(Pageable.class))).thenReturn(pageMock);
        
        avatarService = Mockito.spy( new AvatarService(dao));
        Mockito.doNothing().when(avatarService).createAvatarFlux(Mockito.any(AvatarDTO.class));
        
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
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'entity' est obligatoire."), e);
        }
        
        
        avatarService = Mockito.spy( new AvatarService(dao) );
        MockitoAnnotations.initMocks(avatarService);
        
        AvatarDTO avatarDtoMock = new AvatarDTO(name);
        avatarDtoMock.setId(1);
        Mockito.doReturn(null).doReturn(avatarDtoMock).doReturn(null).when(avatarService).find(Mockito.any(AvatarDTO.class));
        Mockito.when(dao.save(Mockito.any(AvatarDTO.class))).thenReturn(avatarDtoMock);
        
        AvatarDTO avatarDto = new AvatarDTO(name);
        Assert.assertSame( avatarDtoMock, avatarService.save(avatarDto) );
        
        try {
            avatarService.save(avatarDto);
            Assert.fail("Doit lever une DaoException car le mock throw.");
        }
        catch (ConflictException e) {
            TestUtils.verifyException(new ConflictException("Entity already exists."), e);
        }
        
        AvatarDTO avatarDtoSaved = avatarService.save(avatarDto);
        Assert.assertEquals( new Integer(1), avatarDtoSaved.getId() );
        Assert.assertEquals( name, avatarDtoSaved.getName() );
    }
    
    @Test
    public void update() throws NotFoundException, ConflictException {
        
        String name = "name";
        
        try {
            avatarService.update(null);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'entity' est obligatoire."), e);
        }
        
        
        AvatarDTO avatarDto = new AvatarDTO(name);
        try {
            avatarService.update(avatarDto);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'entity -> id' est obligatoire."), e);
        }
        
        
        AvatarDTO avatarStatDtoMockNotSame = new AvatarDTO(name);
        avatarStatDtoMockNotSame.setId(2);
        AvatarDTO avatarStatDtoMockSame = new AvatarDTO(name);
        avatarStatDtoMockSame.setId(1);
        Mockito.when(dao.findById(Mockito.anyInt())).thenReturn(Optional.empty(), Optional.of(avatarStatDtoMockNotSame),
                Optional.of(avatarStatDtoMockNotSame), Optional.of(avatarStatDtoMockSame));
        Mockito.when(dao.save(Mockito.any(AvatarDTO.class))).thenReturn(avatarDto);
        
        try {
            avatarDto.setId(1);
            avatarService.update(avatarDto);
            Assert.fail("Doit lever une DaoException car le mock throw.");
        }
        catch (NotFoundException e) {
            TestUtils.verifyException(new NotFoundException("Entity not found."), e);
        }
        
        avatarDto.setId(1);
        AvatarDTO avatarDtoSaved = avatarService.update(avatarDto);
        Assert.assertEquals( new Integer(1), avatarDtoSaved.getId() );
        Assert.assertEquals( "name", avatarDtoSaved.getName() );
    }
    
    @Test
    public void find() {
        
        AvatarDTO avatarDtoMock = new AvatarDTO("name");
        Mockito.when(dao.findByName(Mockito.anyString())).thenReturn(Optional.empty(), Optional.of(avatarDtoMock));
        Mockito.when(dao.findById(Mockito.anyInt())).thenReturn(Optional.of(avatarDtoMock));
        
        try {
            avatarService.find(null);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'entity' est obligatoire."), e);
        }
        
        AvatarDTO avatarDto = new AvatarDTO("name");
        Assert.assertNull( avatarService.find(avatarDto) );
        Assert.assertEquals( avatarDtoMock, avatarService.find(avatarDto) );
        
        avatarDto = (AvatarDTO) new AvatarDTO("name").setId(1);
        Assert.assertEquals( avatarDtoMock, avatarService.find(avatarDto) );
    }
    
    @Test
    public void findAllByNameStartingWith() {
        
        AvatarDTO avatarDto = new AvatarDTO("name");
        Mockito.when(dao.findAllByNameStartingWithIgnoreCase(Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn( new PageImpl<>(Collections.singletonList(avatarDto)));
        
        Assert.assertEquals( new PageImpl<>(Collections.singletonList(avatarDto)), avatarService.findAllByNameStartingWith(null, 0, 1) );
        Assert.assertEquals( new PageImpl<>(Collections.singletonList(avatarDto)), avatarService.findAllByNameStartingWith("", 0, 1) );
    }
    
    @Test
    public void createFlux() {
        
        try {
            avatarService.createAvatarFlux(null);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'entity' est obligatoire."), e);
        }
        
        AvatarDTO avatarDtoMock = new AvatarDTO("name");
        avatarService.createAvatarFlux(avatarDtoMock);
        Assert.assertNotNull( avatarDtoMock.getFlux() );
    }
    
}