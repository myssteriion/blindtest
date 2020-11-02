package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.entity.AvatarEntity;
import com.myssteriion.blindtest.persistence.dao.AvatarDAO;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.NotFoundException;
import com.myssteriion.utils.test.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

class AvatarServiceTest extends AbstractTest {
    
    @Mock
    private AvatarDAO dao;
    
    private AvatarService avatarService;
    
    
    
    @BeforeEach
    void before() {
        avatarService = Mockito.spy( new AvatarService(dao, configProperties) );
    }
    
    
    
    @Test
    void save() throws ConflictException {
        
        TestUtils.assertThrowMandatoryField( "avatar", () -> avatarService.save(null) );
        TestUtils.assertThrowMandatoryField( "avatar -> name", () -> avatarService.save(new AvatarEntity()) );
        
        String name = "name";
        AvatarEntity avatarMock = new AvatarEntity(name);
        avatarMock.setId(1);
        Mockito.doReturn(null).doReturn(avatarMock).doReturn(null).when(avatarService).find(Mockito.any(AvatarEntity.class));
        Mockito.when(dao.save(Mockito.any(AvatarEntity.class))).thenReturn(avatarMock);
        
        AvatarEntity avatar = new AvatarEntity(name);
        Assertions.assertSame(avatarMock, avatarService.save(avatar) );
        
        TestUtils.assertThrow( ConflictException.class, "avatar already exists.", () -> avatarService.save(avatar) );
        
        AvatarEntity avatarSaved = avatarService.save(avatar);
        Assertions.assertEquals( Integer.valueOf(1), avatarSaved.getId() );
        Assertions.assertEquals( name, avatarSaved.getName() );
    }
    
    @Test
    void update() throws NotFoundException, ConflictException {
        
        TestUtils.assertThrowMandatoryField( "avatar", () -> avatarService.update(null) );
        TestUtils.assertThrowMandatoryField( "avatar -> name", () -> avatarService.update(new AvatarEntity()) );
        
        String name = "name";
        AvatarEntity avatar = new AvatarEntity(name);
        TestUtils.assertThrowMandatoryField( "avatar -> id", () -> avatarService.update(avatar) );
        
        AvatarEntity avatarStatMockNotSame = new AvatarEntity(name);
        avatarStatMockNotSame.setId(2);
        AvatarEntity avatarStatMockSame = new AvatarEntity(name);
        avatarStatMockSame.setId(1);
        Mockito.when(dao.findById(Mockito.anyInt())).thenReturn(Optional.empty(), Optional.of(avatarStatMockNotSame),
                Optional.of(avatarStatMockNotSame), Optional.of(avatarStatMockSame));
        Mockito.when(dao.save(Mockito.any(AvatarEntity.class))).thenReturn(avatar);
        
        avatar.setId(1);
        TestUtils.assertThrow( NotFoundException.class, "avatar not found.", () -> avatarService.update(avatar) );
        
        AvatarEntity avatarSaved = avatarService.update(avatar);
        Assertions.assertEquals( Integer.valueOf(1), avatarSaved.getId() );
        Assertions.assertEquals( "name", avatarSaved.getName() );
    }
    
    @Test
    void find() {
        
        TestUtils.assertThrowMandatoryField( "avatar", () -> avatarService.find(null) );
        TestUtils.assertThrowMandatoryField( "avatar -> name", () -> avatarService.find(new AvatarEntity()) );
        
        AvatarEntity avatarMock = new AvatarEntity("name");
        Mockito.when(dao.findByName(Mockito.anyString())).thenReturn(Optional.empty(), Optional.of(avatarMock));
        Mockito.when(dao.findById(Mockito.anyInt())).thenReturn(Optional.of(avatarMock));
        
        AvatarEntity avatar = new AvatarEntity("name");
        Assertions.assertNull( avatarService.find(avatar) );
        Assertions.assertEquals(avatarMock, avatarService.find(avatar) );
        Mockito.verify(dao, Mockito.times(2)).findByName(Mockito.anyString());
        
        avatar = new AvatarEntity().setId(1);
        Assertions.assertEquals(avatarMock, avatarService.find(avatar) );
        Mockito.verify(dao, Mockito.times(1)).findById(Mockito.anyInt());
    }
    
    @Test
    void findAllByName() {
        
        AvatarEntity avatar = new AvatarEntity("name");
        Mockito.when(dao.findAllByNameContainingIgnoreCase(Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn( new PageImpl<>(Collections.singletonList(avatar)));
        
        Assertions.assertEquals( new PageImpl<>(Collections.singletonList(avatar)), avatarService.findAllByName(null, 0, 1) );
        Assertions.assertEquals( new PageImpl<>(Collections.singletonList(avatar)), avatarService.findAllByName("", 0, 1) );
    }
    
    @Test
    void checkEntity() {
        
        TestUtils.assertThrowMandatoryField( "avatar", () -> avatarService.checkEntity(null) );
        TestUtils.assertThrowMandatoryField( "avatar -> name", () -> avatarService.checkEntity(new AvatarEntity()) );
        
        avatarService.checkEntity(new AvatarEntity("name"));
    }
    
}