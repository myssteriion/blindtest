package com.myssteriion.blindtest.service;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Avatar;
import com.myssteriion.blindtest.tools.Tool;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ AvatarService.class, Tool.class })
public class AvatarServiceTest extends AbstractTest {

    @InjectMocks
    private AvatarService avatarService;



    @Test
    public void refresh() {

        File mockFile = Mockito.mock(File.class);
        Mockito.when(mockFile.isFile()).thenReturn(true);
        Mockito.when(mockFile.getName()).thenReturn("file");

        File mockDirectory = Mockito.mock(File.class);
        Mockito.when(mockDirectory.isFile()).thenReturn(false);

        PowerMockito.mockStatic(Tool.class);
        PowerMockito.when(Tool.getChildren(Mockito.any(File.class))).thenReturn(Arrays.asList(mockFile, mockDirectory));


        Assert.assertEquals( Page.empty(), avatarService.getAll(0) );

        avatarService.refresh();
        Assert.assertEquals( new PageImpl<>(Arrays.asList(new Avatar("file"))), avatarService.getAll(0) );
    }

    @Test
    public void getAll() {
        Assert.assertEquals( new PageImpl<>(new ArrayList<>()), avatarService.getAll(0) );
    }

}