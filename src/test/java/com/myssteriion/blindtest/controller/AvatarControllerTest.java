package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.model.common.Flux;
import com.myssteriion.blindtest.model.dto.AvatarDTO;
import com.myssteriion.blindtest.service.AvatarService;
import com.myssteriion.utils.test.AbstractTest;
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
    public void findAllByNameStartingWith() {

        Flux fluxMock = Mockito.mock(Flux.class);
        Mockito.when(fluxMock.isFileExists()).thenReturn(false, true);
        AvatarDTO avatar = new AvatarDTO("name").setFlux(fluxMock);
        Mockito.when(avatarService.findAllByNameStartingWith(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenReturn( new PageImpl<>(Arrays.asList(avatar)) );
        Mockito.when(avatarService.needRefresh()).thenReturn(true, false);

        ResponseEntity< Page<AvatarDTO> > re = avatarController.findAllByNameStartingWith("", 0, 1);
        Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assert.assertEquals( new PageImpl<>(Arrays.asList(avatar)), re.getBody() );

        re = avatarController.findAllByNameStartingWith("", 0, 1);
        Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assert.assertEquals( new PageImpl<>(Arrays.asList(avatar)), re.getBody() );
    }

}