package com.myssteriion.blindtest.properties;

import com.myssteriion.blindtest.AbstractTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RoundContentPropertiesTest extends AbstractTest {

    @Autowired
    private RoundContentProperties props;



    @Test
    public void getterSetter() {
        
        Assert.assertEquals( new Integer(12), props.getClassicNbMusics() );
        Assert.assertEquals( new Integer(100), props.getClassicNbPointWon() );

        Assert.assertEquals( new Integer(4), props.getChoiceNbMusics() );
        Assert.assertEquals( new Integer(100), props.getChoiceNbPointWon() );
        Assert.assertEquals( new Integer(50), props.getChoiceNbPointBonusWon() );
        Assert.assertEquals( new Integer(-50), props.getChoicenNPointMalusLoose() );

        Assert.assertEquals( new Integer(12), props.getThiefNbMusics() );
        Assert.assertEquals( new Integer(100), props.getThiefNbPointWon() );
        Assert.assertEquals( new Integer(-100), props.getThiefNbPointLoose() );
    }

}