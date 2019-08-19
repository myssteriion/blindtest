package com.myssteriion.blindtest.model.common;

import com.myssteriion.blindtest.AbstractTest;
import org.junit.Assert;
import org.junit.Test;

public class RoundTest extends AbstractTest {

    @Test
    public void getter() {
        Assert.assertEquals( 0, Round.getFirst().getRoundNumber() );
    }

    @Test
    public void nextRound() {
        Assert.assertEquals( Round.CHOICE, Round.CLASSIC.nextRound() );
        Assert.assertEquals( Round.THIEF, Round.CHOICE.nextRound() );
        Assert.assertNull( Round.THIEF.nextRound() );
    }

    @Test
    public void getFirst() {
        Assert.assertEquals( Round.CLASSIC, Round.getFirst() );
    }

}