package com.myssteriion.blindtest.model.common;

import com.myssteriion.blindtest.AbstractTest;
import org.junit.Assert;
import org.junit.Test;

public class RoundTest extends AbstractTest {

    @Test
    public void getter() {
        Assert.assertEquals( 0, Round.CLASSIC.getRoundNumber() );
        Assert.assertEquals( 20, Round.CLASSIC.getNbMusics() );
        Assert.assertEquals( 100, Round.CLASSIC.getNbPointWon() );
        Assert.assertEquals( 0, Round.CLASSIC.getNbPointLost() );
    }

    @Test
    public void next() {
        Assert.assertNull( Round.CLASSIC.next() );
    }

    @Test
    public void getFirst() {
        Assert.assertEquals( Round.CLASSIC, Round.getFirst() );
    }

}