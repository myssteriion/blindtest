package com.myssteriion.blindtest.controller;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.base.Empty;
import com.myssteriion.blindtest.model.dto.AvatarDTO;
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
    public void refresh() {

        Mockito.doNothing().when(avatarService).refresh();

        ResponseEntity<Empty> re = avatarController.refresh();
        Assert.assertEquals( HttpStatus.NO_CONTENT, re.getStatusCode() );
    }

    @Test
    public void findAllByNameStartingWith() {

        AvatarDTO avatar = new AvatarDTO("name");
        Mockito.when(avatarService.findAllByNameStartingWith(Mockito.anyString(), Mockito.anyInt())).thenReturn( new PageImpl<>(Arrays.asList(avatar)) );

        ResponseEntity< Page<AvatarDTO> > re = avatarController.findAllByNameStartingWith("", 0);
        Assert.assertEquals( HttpStatus.OK, re.getStatusCode() );
        Assert.assertEquals( new PageImpl<>(Arrays.asList(avatar)), re.getBody() );
    }

}