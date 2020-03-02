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
        
        Assert.assertEquals( new Integer(20), props.getClassicNbMusics() );
        Assert.assertEquals( new Integer(100), props.getClassicNbPointWon() );
        
        Assert.assertEquals( new Integer(12), props.getChoiceNbMusics() );
        Assert.assertEquals( new Integer(100), props.getChoiceNbPointWon() );
        Assert.assertEquals( new Integer(50), props.getChoiceNbPointBonus() );
        Assert.assertEquals( new Integer(-50), props.getChoiceNbPointMalus() );
        
        Assert.assertEquals( new Integer(10), props.getLuckyNbMusics() );
        Assert.assertEquals( new Integer(150), props.getLuckyNbPointWon() );
        Assert.assertEquals( new Integer(100), props.getLuckyNbPointBonus() );
        
        Assert.assertEquals( new Integer(20), props.getThiefNbMusics() );
        Assert.assertEquals( new Integer(100), props.getThiefNbPointWon() );
        Assert.assertEquals( new Integer(-100), props.getThiefNbPointLoose() );
        
        Assert.assertEquals( new Integer(10), props.getRecoveryNbMusics() );
        Assert.assertEquals( new Integer(30), props.getRecoveryNbPointWon() );
    }
    
}