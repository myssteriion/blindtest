package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.entity.AvatarEntity;
import com.myssteriion.blindtest.model.entity.ProfileEntity;
import com.myssteriion.blindtest.persistence.dao.ProfileDAO;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.NotFoundException;
import com.myssteriion.utils.test.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

public class ProfileServiceTest extends AbstractTest {
    
    @Mock
    private ProfileDAO profileDao;
    
    @Mock
    private AvatarService avatarService;
    
    @InjectMocks
    private ProfileService profileService;
    
    
    
    @Before
    public void before() {
        profileService = new ProfileService(profileDao, avatarService);
    }
    
    
    
    @Test
    public void save() throws ConflictException {
        
        String name = "name";
        
        
        try {
            profileService.save(null);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'profile' est obligatoire."), e);
        }
        
        profileService = Mockito.spy( new ProfileService(profileDao, avatarService) );
        MockitoAnnotations.initMocks(profileService);
        
        ProfileEntity profileMock = new ProfileEntity().setName(name);
        profileMock.setId(1);
        Mockito.doReturn(null).doReturn(profileMock).doReturn(null).when(profileService).find(Mockito.any(ProfileEntity.class));
        Mockito.when(profileDao.save(Mockito.any(ProfileEntity.class))).thenReturn(profileMock);
        
        ProfileEntity profile = new ProfileEntity().setName(name);
        Assert.assertSame(profileMock, profileService.save(profile) );
        
        try {
            profileService.save(profile);
            Assert.fail("Doit lever une DaoException car le mock throw.");
        }
        catch (ConflictException e) {
            TestUtils.verifyException(new ConflictException("profile already exists."), e);
        }
        
        ProfileEntity profileSaved = profileService.save(profile);
        Assert.assertEquals( Integer.valueOf(1), profileSaved.getId() );
        Assert.assertEquals( name, profileSaved.getName() );
    }
    
    @Test
    public void update() throws NotFoundException, ConflictException {
        
        String name = "name";
        
        
        try {
            profileService.update(null);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'profile' est obligatoire."), e);
        }
        
        
        ProfileEntity profile = new ProfileEntity().setName(name);
        try {
            profileService.update(profile);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'profile -> id' est obligatoire."), e);
        }
        
        
        ProfileEntity profileStatMockNotSame = new ProfileEntity().setName(name);
        profileStatMockNotSame.setId(2);
        ProfileEntity profileStatMockSame = new ProfileEntity().setName(name);
        profileStatMockSame.setId(1);
        DataIntegrityViolationException dive = new DataIntegrityViolationException("dive");
        Mockito.when(profileDao.findById(Mockito.anyInt())).thenReturn(Optional.empty(), Optional.of(profileStatMockNotSame),
                Optional.of(profileStatMockNotSame), Optional.of(profileStatMockSame));
        Mockito.when(profileDao.save(Mockito.any(ProfileEntity.class))).thenThrow(dive).thenReturn(profile);
        
        try {
            profile.setId(1);
            profileService.update(profile);
            Assert.fail("Doit lever une DaoException car le mock throw.");
        }
        catch (NotFoundException e) {
            TestUtils.verifyException(new NotFoundException("profile not found."), e);
        }
        
        try {
            profile.setId(1);
            profileService.update(profile);
            Assert.fail("Doit lever une DaoException car le mock throw.");
        }
        catch (ConflictException e) {
            TestUtils.verifyException(new ConflictException("profile already exists.", dive), e);
        }
        
        profile.setId(1);
        profile.setName("pouet");
        ProfileEntity profileSaved = profileService.update(profile);
        Assert.assertEquals( Integer.valueOf(1), profileSaved.getId() );
        Assert.assertEquals( "pouet", profileSaved.getName() );
    }
    
    @Test
    public void find() {
        
        ProfileEntity profileMock = new ProfileEntity().setName("name");
        Mockito.when(profileDao.findByName(Mockito.anyString())).thenReturn(Optional.empty(), Optional.of(profileMock));
        
        
        try {
            profileService.find(null);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'profile' est obligatoire."), e);
        }
        
        ProfileEntity profile = new ProfileEntity().setName("name");
        Assert.assertNull( profileService.find(profile) );
        Assert.assertNotNull( profileService.find(profile) );
    }
    
    @Test
    public void findAllByName() {
        
        ProfileEntity profile = new ProfileEntity().setName("name");
        Mockito.when(profileDao.findAllByNameContainingIgnoreCase(Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn( new PageImpl<>(Collections.singletonList(profile)));
        
        Assert.assertEquals( new PageImpl<>(Collections.singletonList(profile)),  profileService.findAllByName(null, 0, 1) );
        Assert.assertEquals( new PageImpl<>(Collections.singletonList(profile)),  profileService.findAllByName("", 0, 1) );
    }
    
    @Test
    public void delete() throws NotFoundException {
        
        try {
            profileService.delete(null);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'profile' est obligatoire."), e);
        }
        
        
        ProfileEntity profile = new ProfileEntity().setName("name");
        
        try {
            profileService.delete(profile);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'profile -> id' est obligatoire."), e);
        }
        
        Mockito.when(profileDao.findById(Mockito.anyInt())).thenReturn(Optional.empty(), Optional.of(profile));
        profile.setId(1);
        
        try {
            profileService.delete(profile);
            Assert.fail("Doit lever une NotFoundException car le mock throw.");
        }
        catch (NotFoundException e) {
            TestUtils.verifyException(new NotFoundException("profile not found."), e);
        }
        
        profileService.delete(profile);
    }
    
    @Test
    public void checkEntity() {
        
        try {
            profileService.checkEntity(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'profile' est obligatoire."), e);
        }
        
        try {
            profileService.checkEntity(new ProfileEntity());
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'profile -> name' est obligatoire."), e);
        }
        
        profileService.checkEntity(new ProfileEntity().setName("name"));
    }
    
}
