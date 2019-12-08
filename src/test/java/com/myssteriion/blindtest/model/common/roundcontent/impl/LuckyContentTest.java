package com.myssteriion.blindtest.model.common.roundcontent.impl;

import com.myssteriion.blindtest.AbstractTest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class LuckyContentTest extends AbstractTest {

    @Test
    public void constructors() {

        LuckyContent luckyContent = new LuckyContent(-1,  -2, -1);
        Assert.assertEquals( 0, luckyContent.getNbMusics() );
        Assert.assertEquals( 0, luckyContent.getNbPointWon() );
        Assert.assertEquals( 0, luckyContent.getNbPointBonus() );
    }

    @Test
    public void getterSetter() {

        LuckyContent luckyContent = new LuckyContent(-1,  -2, -1);
        Assert.assertEquals( 0, luckyContent.getNbMusics() );
        Assert.assertEquals( 0, luckyContent.getNbPointWon() );
        Assert.assertEquals( 0, luckyContent.getNbPointBonus() );

        luckyContent = new LuckyContent(5,  100, 100);
        Assert.assertEquals( 5, luckyContent.getNbMusics() );
        Assert.assertEquals( 100, luckyContent.getNbPointWon() );
        Assert.assertEquals( 100, luckyContent.getNbPointBonus() );
    }

    @Ignore
    @Test
    public void apply() {

    }

    @Test
    public void toStringAndEquals() {

        LuckyContent luckyContent = new LuckyContent(5,  150, 100);
        Assert.assertEquals( "nbMusics=5, nbPointWon=150, nbPointBonus=100", luckyContent.toString() );
    }

}