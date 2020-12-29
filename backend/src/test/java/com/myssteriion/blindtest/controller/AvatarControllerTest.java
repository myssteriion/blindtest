package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.entity.AvatarEntity;
import com.myssteriion.blindtest.service.AvatarService;
import com.myssteriion.utils.model.impl.Empty;
import com.myssteriion.utils.model.other.Flux;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

class AvatarControllerTest extends AbstractTest {
    
    @Mock
    private AvatarService avatarService;
    
    @InjectMocks
    private AvatarController avatarController;
    
    
    
    @Test
    void refresh() {
        
        Mockito.doNothing().when(avatarService).init();
        
        ResponseEntity<Empty> re = avatarController.refresh();
        Assertions.assertEquals( HttpStatus.NO_CONTENT, re.getStatusCode() );
    }
    
    @Test
    void findAllByName() {
        
        Flux fluxMock = Mockito.mock(Flux.class);
        Mockito.when(fluxMock.isFileExists()).thenReturn(false, true);
        AvatarEntity avatar = new AvatarEntity("name").setFlux(fluxMock);
        Mockito.when(avatarService.findAllByName(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenReturn( new PageImpl<>(Collections.singletonList(avatar)) );
        
        ResponseEntity< Page<AvatarEntity> > re = avatarController.findAllByName("", 0, 1);
        Assertions.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assertions.assertEquals( new PageImpl<>(Collections.singletonList(avatar)), re.getBody() );
        
        re = avatarController.findAllByName("", 0, 1);
        Assertions.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assertions.assertEquals( new PageImpl<>(Collections.singletonList(avatar)), re.getBody() );
    }
    
}
