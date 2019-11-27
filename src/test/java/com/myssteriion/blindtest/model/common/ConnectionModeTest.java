package com.myssteriion.blindtest.model.common;

import com.myssteriion.blindtest.AbstractTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class ConnectionModeTest extends AbstractTest {

    @Test
    public void isNeedConnection() {

        Assert.assertFalse( ConnectionMode.OFFLINE.isNeedConnection() );
        Assert.assertTrue( ConnectionMode.ONLINE.isNeedConnection() );
        Assert.assertTrue( ConnectionMode.BOTH.isNeedConnection() );
    }

    @Test
    public void transformForSearchMusic() {

        Assert.assertEquals( Collections.singletonList(ConnectionMode.OFFLINE), ConnectionMode.OFFLINE.transformForSearchMusic() );
        Assert.assertEquals( Collections.singletonList(ConnectionMode.ONLINE), ConnectionMode.ONLINE.transformForSearchMusic() );
        Assert.assertEquals( Arrays.asList(ConnectionMode.OFFLINE, ConnectionMode.ONLINE), ConnectionMode.BOTH.transformForSearchMusic() );
    }

}