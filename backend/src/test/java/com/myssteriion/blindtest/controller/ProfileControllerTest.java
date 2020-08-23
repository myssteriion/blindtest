package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.entity.ProfileEntity;
import com.myssteriion.blindtest.service.ProfileService;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.NotFoundException;
import com.myssteriion.utils.model.Empty;
import com.myssteriion.utils.test.TestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;

public class ProfileControllerTest extends AbstractTest {
    
    @Mock
    private ProfileService profileService;
    
    @InjectMocks
    private ProfileController profileController;
    
    
    
    @Test
    public void save() throws ConflictException {
        
        ProfileEntity profile = new ProfileEntity().setName("name");
        Mockito.when(profileService.save(Mockito.any(ProfileEntity.class))).thenReturn(profile);
        
        ResponseEntity<ProfileEntity> actual = profileController.save(profile);
        Assert.assertEquals( HttpStatus.CREATED, actual.getStatusCode() );
        Assert.assertEquals( "name", actual.getBody().getName() );
    }
    
    @Test
    public void update() throws NotFoundException, ConflictException {
        
        ProfileEntity profile = new ProfileEntity().setName("name");
        Mockito.when(profileService.update(Mockito.any(ProfileEntity.class))).thenReturn(profile);
        
        ResponseEntity<ProfileEntity> actual = profileController.update(1, profile);
        Assert.assertEquals( HttpStatus.OK, actual.getStatusCode() );
        Assert.assertEquals( "name", actual.getBody().getName() );
    }
    
    @Test
    public void findAllByName() {
        
        IllegalArgumentException iae = new IllegalArgumentException("iae");
        Page<ProfileEntity> pageMock = Mockito.mock(Page.class);
        Mockito.when(pageMock.getContent()).thenReturn(Collections.singletonList(new ProfileEntity().setName("name")));
        Mockito.when(profileService.findAllByName(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenThrow(iae).thenReturn(pageMock);
        
        try {
            profileController.findAllByName("", 0, 1);
            Assert.fail("Doit lever une IllegalArgumentException car le mock throw.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(iae, e);
        }
        
        ResponseEntity< Page<ProfileEntity> > re = profileController.findAllByName("", 0, 1);
        Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Page<ProfileEntity> actual = re.getBody();
        Assert.assertEquals( 1, actual.getContent().size() );
    }
    
    @Test
    public void delete() throws NotFoundException {
        
        Mockito.doNothing().when(profileService).delete(Mockito.any(ProfileEntity.class));
        
        ResponseEntity<Empty> actual = profileController.delete(1);
        Assert.assertEquals( HttpStatus.NO_CONTENT, actual.getStatusCode() );
    }
    
}
