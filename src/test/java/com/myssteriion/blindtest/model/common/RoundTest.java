package com.myssteriion.blindtest.model.common;

import com.myssteriion.blindtest.AbstractTest;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoundTest extends AbstractTest {

    @Test
    public void next() {
        Assert.assertNull( Round.CLASSIC.next() );
    }

    @Test
    public void getNbMusicTotalForNormalParty() {
        Assert.assertEquals( 20, Round.getNbMusicTotalForNormalParty() );
    }

    @Test
    public void getFirst() {
        Assert.assertEquals( Round.CLASSIC, Round.getFirst() );
    }

}