package com.myssteriion.blindtest.model.common.roundcontent.impl;

import com.myssteriion.blindtest.AbstractTest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class LuckyContentTest extends AbstractTest {

    @Test
    public void constructors() {

        LuckyContent luckyContent = new LuckyContent(-1,  -2, -1, -1);
        Assert.assertEquals( 0, luckyContent.getNbMusics() );
        Assert.assertEquals( 0, luckyContent.getNbPointWon() );
        Assert.assertEquals( 0, luckyContent.getNbPointBonus() );
        Assert.assertEquals( 1, luckyContent.getNbPlayers() );
    }

    @Test
    public void getterSetter() {

        LuckyContent luckyContent = new LuckyContent(-1,  -2, -1, -1);
        Assert.assertEquals( 0, luckyContent.getNbMusics() );
        Assert.assertEquals( 0, luckyContent.getNbPointWon() );
        Assert.assertEquals( 0, luckyContent.getNbPointBonus() );
        Assert.assertEquals( 1, luckyContent.getNbPlayers() );

        luckyContent = new LuckyContent(5,  100, 100, 2);
        Assert.assertEquals( 5, luckyContent.getNbMusics() );
        Assert.assertEquals( 100, luckyContent.getNbPointWon() );
        Assert.assertEquals( 100, luckyContent.getNbPointBonus() );
        Assert.assertEquals( 2, luckyContent.getNbPlayers() );
    }

    @Ignore
    @Test
    public void apply() {

    }

    @Test
    public void toStringAndEquals() {

        LuckyContent luckyContent = new LuckyContent(5,  150, 100, 1);
        Assert.assertEquals( "nbMusics=5, nbPointWon=150, nbPointBonus=100, nbPlayers=1", luckyContent.toString() );
    }

}