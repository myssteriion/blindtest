package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.model.dto.ProfileStatDTO;
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
    private ProfileStatService profileStatService;
    
    @Mock
    private AvatarService avatarService;
    
    @InjectMocks
    private ProfileService profileService;
    
    
    
    @Before
    public void before() {
        profileService = new ProfileService(profileDao, profileStatService, avatarService);
    }
    
    
    
    @Test
    public void save() throws ConflictException {
        
        String name = "name";
        String avatarName = "avatarName";
        
        
        try {
            profileService.save(null);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'profile' est obligatoire."), e);
        }
        
        profileService = Mockito.spy( new ProfileService(profileDao, profileStatService, avatarService) );
        MockitoAnnotations.initMocks(profileService);
        
        ProfileStatDTO profileStatDtoMock = new ProfileStatDTO(1);
        Mockito.when(profileStatService.save(Mockito.any(ProfileStatDTO.class))).thenReturn(profileStatDtoMock);
        
        ProfileDTO profileDtoMock = new ProfileDTO().setName(name).setAvatarName(avatarName);
        profileDtoMock.setId(1);
        Mockito.doReturn(null).doReturn(profileDtoMock).doReturn(null).when(profileService).find(Mockito.any(ProfileDTO.class));
        Mockito.when(profileDao.save(Mockito.any(ProfileDTO.class))).thenReturn(profileDtoMock);
        
        ProfileDTO profileDto = new ProfileDTO().setName(name).setAvatarName(avatarName);
        Assert.assertSame( profileDtoMock, profileService.save(profileDto) );
        
        try {
            profileService.save(profileDto);
            Assert.fail("Doit lever une DaoException car le mock throw.");
        }
        catch (ConflictException e) {
            TestUtils.verifyException(new ConflictException("profile already exists."), e);
        }
        
        ProfileDTO profileDtoSaved = profileService.save(profileDto);
        Assert.assertEquals( Integer.valueOf(1), profileDtoSaved.getId() );
        Assert.assertEquals( name, profileDtoSaved.getName() );
    }
    
    @Test
    public void update() throws NotFoundException, ConflictException {
        
        String name = "name";
        String avatarName = "avatarName";
        
        
        try {
            profileService.update(null);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'profile' est obligatoire."), e);
        }
        
        
        ProfileDTO profileDto = new ProfileDTO().setName(name).setAvatarName(avatarName);
        try {
            profileService.update(profileDto);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'profile -> id' est obligatoire."), e);
        }
        
        
        ProfileDTO profileStatDtoMockNotSame = new ProfileDTO().setName(name).setAvatarName(avatarName);
        profileStatDtoMockNotSame.setId(2);
        ProfileDTO profileStatDtoMockSame = new ProfileDTO().setName(name).setAvatarName(avatarName);
        profileStatDtoMockSame.setId(1);
        DataIntegrityViolationException dive = new DataIntegrityViolationException("dive");
        Mockito.when(profileDao.findById(Mockito.anyInt())).thenReturn(Optional.empty(), Optional.of(profileStatDtoMockNotSame),
                Optional.of(profileStatDtoMockNotSame), Optional.of(profileStatDtoMockSame));
        Mockito.when(profileDao.save(Mockito.any(ProfileDTO.class))).thenThrow(dive).thenReturn(profileDto);
        
        try {
            profileDto.setId(1);
            profileService.update(profileDto);
            Assert.fail("Doit lever une DaoException car le mock throw.");
        }
        catch (NotFoundException e) {
            TestUtils.verifyException(new NotFoundException("profile not found."), e);
        }
        
        try {
            profileDto.setId(1);
            profileService.update(profileDto);
            Assert.fail("Doit lever une DaoException car le mock throw.");
        }
        catch (ConflictException e) {
            TestUtils.verifyException(new ConflictException("profile already exists.", dive), e);
        }
        
        profileDto.setId(1);
        profileDto.setName("pouet");
        profileDto.setAvatarName("avapouet");
        ProfileDTO profileDtoSaved = profileService.update(profileDto);
        Assert.assertEquals( Integer.valueOf(1), profileDtoSaved.getId() );
        Assert.assertEquals( "pouet", profileDtoSaved.getName() );
        Assert.assertEquals( "avapouet", profileDtoSaved.getAvatar().getName() );
    }
    
    @Test
    public void find() {
        
        ProfileDTO profileDtoMock = new ProfileDTO().setName("name").setAvatarName("avatarName");
        Mockito.when(profileDao.findByName(Mockito.anyString())).thenReturn(Optional.empty(), Optional.of(profileDtoMock));
        
        
        try {
            profileService.find(null);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'profile' est obligatoire."), e);
        }
        
        ProfileDTO profileDto = new ProfileDTO().setName("name").setAvatarName("avatarName");
        Assert.assertNull( profileService.find(profileDto) );
        Assert.assertNotNull( profileService.find(profileDto) );
    }
    
    @Test
    public void findAllBySearchName() {
        
        ProfileDTO profileDto = new ProfileDTO().setName("name").setAvatarName("avatarName");
        Mockito.when(profileDao.findAllByNameContainingIgnoreCase(Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn( new PageImpl<>(Collections.singletonList(profileDto)));
        
        Assert.assertEquals( new PageImpl<>(Collections.singletonList(profileDto)),  profileService.findAllBySearchName(null, 0, 1) );
        Assert.assertEquals( new PageImpl<>(Collections.singletonList(profileDto)),  profileService.findAllBySearchName("", 0, 1) );
    }
    
    @Test
    public void delete() throws NotFoundException {
        
        ProfileStatDTO profileStatDtoMock = new ProfileStatDTO();
        Mockito.when(profileStatService.findByProfile(Mockito.any(ProfileDTO.class))).thenReturn(profileStatDtoMock);
        
        
        try {
            profileService.delete(null);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'profile' est obligatoire."), e);
        }
        
        
        ProfileDTO profileDto = new ProfileDTO().setName("name").setAvatarName("avatarName");
        
        try {
            profileService.delete(profileDto);
            Assert.fail("Doit lever une IllegalArgumentException car un param est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'profile -> id' est obligatoire."), e);
        }
        
        Mockito.when(profileDao.findById(Mockito.anyInt())).thenReturn(Optional.empty(), Optional.of(profileDto));
        profileDto.setId(1);
        
        try {
            profileService.delete(profileDto);
            Assert.fail("Doit lever une NotFoundException car le mock throw.");
        }
        catch (NotFoundException e) {
            TestUtils.verifyException(new NotFoundException("profile not found."), e);
        }
        
        profileService.delete(profileDto);
    }
    
    @Test
    public void checkDTO() {
        
        try {
            profileService.checkDTO(null);
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'profile' est obligatoire."), e);
        }
        
        try {
            profileService.checkDTO(new ProfileDTO());
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("Le champ 'profile -> name' est obligatoire."), e);
        }
        
        profileService.checkDTO(new ProfileDTO().setName("name"));
    }
    
}
