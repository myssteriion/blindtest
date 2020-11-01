package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.entity.ProfileEntity;
import com.myssteriion.blindtest.service.ProfileService;
import com.myssteriion.utils.exception.ConflictException;
import com.myssteriion.utils.exception.NotFoundException;
import com.myssteriion.utils.model.Empty;
import com.myssteriion.utils.test.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

class ProfileControllerTest extends AbstractTest {
    
    @Mock
    private ProfileService profileService;
    
    @InjectMocks
    private ProfileController profileController;
    
    
    
    @Test
    void save() throws ConflictException {
        
        ProfileEntity profile = new ProfileEntity().setName("name");
        Mockito.when(profileService.save(Mockito.any(ProfileEntity.class))).thenReturn(profile);
        
        ResponseEntity<ProfileEntity> actual = profileController.save(profile);
        Assertions.assertEquals( HttpStatus.CREATED, actual.getStatusCode() );
        Assertions.assertEquals( "name", actual.getBody().getName() );
    }
    
    @Test
    void update() throws NotFoundException, ConflictException {
        
        ProfileEntity profile = new ProfileEntity().setName("name");
        Mockito.when(profileService.update(Mockito.any(ProfileEntity.class))).thenReturn(profile);
        
        ResponseEntity<ProfileEntity> actual = profileController.update(1, profile);
        Assertions.assertEquals( HttpStatus.OK, actual.getStatusCode() );
        Assertions.assertEquals( "name", actual.getBody().getName() );
    }
    
    @Test
    void findAllByName() {
        
        IllegalArgumentException iae = new IllegalArgumentException("iae");
        Page<ProfileEntity> pageMock = Mockito.mock(Page.class);
        Mockito.when(pageMock.getContent()).thenReturn(Collections.singletonList(new ProfileEntity().setName("name")));
        Mockito.when(profileService.findAllByName(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenThrow(iae).thenReturn(pageMock);
        
        TestUtils.assertThrow( iae, () -> profileController.findAllByName("", 0, 1) );
        
        ResponseEntity< Page<ProfileEntity> > re = profileController.findAllByName("", 0, 1);
        Assertions.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Page<ProfileEntity> actual = re.getBody();
        Assertions.assertEquals( 1, actual.getContent().size() );
    }
    
    @Test
    void delete() throws NotFoundException {
        
        Mockito.doNothing().when(profileService).delete(Mockito.any(ProfileEntity.class));
        
        ResponseEntity<Empty> actual = profileController.delete(1);
        Assertions.assertEquals( HttpStatus.NO_CONTENT, actual.getStatusCode() );
    }
    
}
