package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.entity.AvatarEntity;
import com.myssteriion.blindtest.model.entity.ProfileEntity;
import com.myssteriion.blindtest.persistence.dao.ProfileDAO;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.NotFoundException;
import com.myssteriion.utils.test.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

class ProfileServiceTest extends AbstractTest {
    
    @Mock
    private ProfileDAO profileDao;
    
    @Mock
    private AvatarService avatarService;
    
    @InjectMocks
    private ProfileService profileService;
    
    
    
    @BeforeEach
    void before() {
        profileService = new ProfileService(profileDao, avatarService);
    }
    
    
    
    @Test
    void save() throws ConflictException {
        
        TestUtils.assertThrowMandatoryField("profile", () -> profileService.save(null) );
        
        String name = "name";
        
        profileService = Mockito.spy( new ProfileService(profileDao, avatarService) );
        MockitoAnnotations.initMocks(profileService);
        
        ProfileEntity profileMock = new ProfileEntity().setName(name).setId(1).setAvatar(new AvatarEntity());
        Mockito.doReturn(null).doReturn(profileMock).doReturn(null).when(profileService).find(Mockito.any(ProfileEntity.class));
        Mockito.when(profileDao.save(Mockito.any(ProfileEntity.class))).thenReturn(profileMock);
        
        ProfileEntity profile = new ProfileEntity().setName(name);
        Assertions.assertSame(profileMock, profileService.save(profile) );
        
        TestUtils.assertThrow( ConflictException.class, "profile already exists.", () -> profileService.save(profile) );
        
        ProfileEntity profileSaved = profileService.save(profile);
        Assertions.assertEquals( Integer.valueOf(1), profileSaved.getId() );
        Assertions.assertEquals( name, profileSaved.getName() );
    }
    
    @Test
    void update() throws NotFoundException, ConflictException {
        
        TestUtils.assertThrowMandatoryField("profile", () -> profileService.update(null) );
        
        String name = "name";
        
        ProfileEntity profile = new ProfileEntity().setName(name);
        TestUtils.assertThrowMandatoryField("profile -> id", () -> profileService.update(profile) );
        
        ProfileEntity profileStatMockNotSame = new ProfileEntity().setName(name);
        profileStatMockNotSame.setId(2);
        ProfileEntity profileStatMockSame = new ProfileEntity().setName(name);
        profileStatMockSame.setId(1);
        DataIntegrityViolationException dive = new DataIntegrityViolationException("dive");
        Mockito.when(profileDao.findById(Mockito.anyInt())).thenReturn(Optional.empty(), Optional.of(profileStatMockNotSame),
                Optional.of(profileStatMockNotSame), Optional.of(profileStatMockSame));
        Mockito.when(profileDao.save(Mockito.any(ProfileEntity.class))).thenThrow(dive).thenReturn(profile);
        
        profile.setId(1);
        TestUtils.assertThrow( NotFoundException.class, "profile not found.", () -> profileService.save(profile) );
        TestUtils.assertThrow( ConflictException.class, "profile already exists.", () -> profileService.save(profile) );
        
        profile.setId(1);
        profile.setName("pouet");
        ProfileEntity profileSaved = profileService.update(profile);
        Assertions.assertEquals( Integer.valueOf(1), profileSaved.getId() );
        Assertions.assertEquals( "pouet", profileSaved.getName() );
    }
    
    @Test
    void find() {
        
        TestUtils.assertThrowMandatoryField("profile", () -> profileService.find(null) );
        
        ProfileEntity profileMock = new ProfileEntity().setName("name");
        Mockito.when(profileDao.findByName(Mockito.anyString())).thenReturn(Optional.empty(), Optional.of(profileMock));
        
        ProfileEntity profile = new ProfileEntity().setName("name");
        Assertions.assertNull( profileService.find(profile) );
        Assertions.assertNotNull( profileService.find(profile) );
    }
    
    @Test
    void findAllByName() {
        
        ProfileEntity profile = new ProfileEntity().setName("name");
        Mockito.when(profileDao.findAllByNameContainingIgnoreCase(Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn( new PageImpl<>(Collections.singletonList(profile)));
        
        Assertions.assertEquals( new PageImpl<>(Collections.singletonList(profile)),  profileService.findAllByName(null, 0, 1) );
        Assertions.assertEquals( new PageImpl<>(Collections.singletonList(profile)),  profileService.findAllByName("", 0, 1) );
    }
    
    @Test
    void delete() throws NotFoundException {
        
        TestUtils.assertThrowMandatoryField("profile", () -> profileService.delete(null) );
        
        ProfileEntity profile = new ProfileEntity().setName("name");
        TestUtils.assertThrowMandatoryField("profile -> id", () -> profileService.delete(profile) );
        
        Mockito.when(profileDao.findById(Mockito.anyInt())).thenReturn(Optional.empty(), Optional.of(profile));
        
        profile.setId(1);
        TestUtils.assertThrow( NotFoundException.class, "profile not found.", () -> profileService.delete(profile) );
        
        profileService.delete(profile);
    }
    
    @Test
    void checkEntity() {
        
        TestUtils.assertThrowMandatoryField("profile", () -> profileService.checkEntity(null) );
        TestUtils.assertThrowMandatoryField("profile -> name", () -> profileService.checkEntity(new ProfileEntity()) );
        
        profileService.checkEntity(new ProfileEntity().setName("name"));
    }
    
}
