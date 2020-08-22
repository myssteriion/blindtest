package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Flux;
import com.myssteriion.blindtest.model.entity.AvatarEntity;
import com.myssteriion.blindtest.service.AvatarService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

public class AvatarControllerTest extends AbstractTest {
    
    @Mock
    private AvatarService avatarService;
    
    @InjectMocks
    private AvatarController avatarController;
    
    
    
    @Test
    public void findAllBySearchName() {
        
        Flux fluxMock = Mockito.mock(Flux.class);
        Mockito.when(fluxMock.isFileExists()).thenReturn(false, true);
        AvatarEntity avatar = new AvatarEntity("name").setFlux(fluxMock);
        Mockito.when(avatarService.findAllBySearchName(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenReturn( new PageImpl<>(Arrays.asList(avatar)) );
        Mockito.when(avatarService.needRefresh()).thenReturn(true, false);
        
        ResponseEntity< Page<AvatarEntity> > re = avatarController.findAllBySearchName("", 0, 1);
        Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assert.assertEquals( new PageImpl<>(Arrays.asList(avatar)), re.getBody() );
        
        re = avatarController.findAllBySearchName("", 0, 1);
        Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assert.assertEquals( new PageImpl<>(Arrays.asList(avatar)), re.getBody() );
    }
    
}