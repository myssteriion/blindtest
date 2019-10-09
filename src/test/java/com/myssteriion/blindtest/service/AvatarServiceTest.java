package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.dao.AvatarDAO;
import com.myssteriion.blindtest.model.dto.AvatarDTO;
import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ AvatarService.class, Tool.class })
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
    public void refresh() throws ConflictException {

        File mockFile = Mockito.mock(File.class);
        Mockito.when(mockFile.isFile()).thenReturn(true);
        Mockito.when(mockFile.getName()).thenReturn("file");

        File mockDirectory = Mockito.mock(File.class);
        Mockito.when(mockDirectory.isFile()).thenReturn(false);

        PowerMockito.mockStatic(Tool.class);
        PowerMockito.when(Tool.getChildren(Mockito.any(File.class))).thenReturn(Arrays.asList(mockFile, mockDirectory));


        avatarService = Mockito.spy( new AvatarService(dao) );
        MockitoAnnotations.initMocks(avatarService);
        Mockito.doReturn(null).when(avatarService).save(Mockito.any(AvatarDTO.class));

        AvatarDTO avatarMock = new AvatarDTO();
        Mockito.when(dao.findByName(Mockito.anyString())).thenReturn(Optional.empty(), Optional.of(avatarMock));

        avatarService.refresh();
        Mockito.verify(dao, Mockito.times(1)).save(Mockito.any(AvatarDTO.class));
    }

    @Test
    public void save() throws ConflictException {

        String name = "name";

        try {
            avatarService.save(null);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
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
            verifyException(new ConflictException("Dto already exists."), e);
        }

        AvatarDTO avatarDtoSaved = avatarService.save(avatarDto);
        Assert.assertEquals( new Integer(1), avatarDtoSaved.getId() );
        Assert.assertEquals( name, avatarDtoSaved.getName() );
    }

    @Test
    public void update() throws NotFoundException {

        String name = "name";

        try {
            avatarService.update(null);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
        }


        AvatarDTO avatarDto = new AvatarDTO(name);
        try {
            avatarService.update(avatarDto);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'dto -> id' est obligatoire."), e);
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
            verifyException(new NotFoundException("Dto not found."), e);
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
            verifyException(new IllegalArgumentException("Le champ 'dto' est obligatoire."), e);
        }

        AvatarDTO avatarDto = new AvatarDTO("name");
        Assert.assertNull( avatarService.find(avatarDto) );
        Assert.assertEquals( avatarDtoMock, avatarService.find(avatarDto) );

        avatarDto = new AvatarDTO("name").setId(1);
        Assert.assertEquals( avatarDtoMock, avatarService.find(avatarDto) );
    }

    @Test
    public void findAllByNameStartingWith() {

        AvatarDTO avatarDto = new AvatarDTO("name");
        Mockito.when(dao.findAllByNameStartingWithIgnoreCase(Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn( new PageImpl<>(Collections.singletonList(avatarDto)));

        Assert.assertEquals( new PageImpl<>(Collections.singletonList(avatarDto)), avatarService.findAllByNameStartingWith(null, 0) );
        Assert.assertEquals( new PageImpl<>(Collections.singletonList(avatarDto)), avatarService.findAllByNameStartingWith("", 0) );
    }
    
}