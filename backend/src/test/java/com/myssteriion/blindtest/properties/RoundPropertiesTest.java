package com.myssteriion.blindtest.properties;

import com.myssteriion.blindtest.AbstractTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RoundPropertiesTest extends AbstractTest {
    
    @Autowired
    private RoundProperties props;
    
    
    
    @Test
    public void getterSetter() {
        
        Assert.assertEquals( Integer.valueOf(20), props.getClassicNbMusics() );
        Assert.assertEquals( Integer.valueOf(100), props.getClassicNbPointWon() );
        
        Assert.assertEquals( Integer.valueOf(12), props.getChoiceNbMusics() );
        Assert.assertEquals( Integer.valueOf(100), props.getChoiceNbPointWon() );
        Assert.assertEquals( Integer.valueOf(50), props.getChoiceNbPointBonus() );
        Assert.assertEquals( Integer.valueOf(-50), props.getChoiceNbPointMalus() );
        
        Assert.assertEquals( Integer.valueOf(10), props.getLuckyNbMusics() );
        Assert.assertEquals( Integer.valueOf(150), props.getLuckyNbPointWon() );
        Assert.assertEquals( Integer.valueOf(100), props.getLuckyNbPointBonus() );
        
        Assert.assertEquals( Integer.valueOf(20), props.getThiefNbMusics() );
        Assert.assertEquals( Integer.valueOf(100), props.getThiefNbPointWon() );
        Assert.assertEquals( Integer.valueOf(-100), props.getThiefNbPointLoose() );
        
        Assert.assertEquals( Integer.valueOf(10), props.getRecoveryNbMusics() );
        Assert.assertEquals( Integer.valueOf(30), props.getRecoveryNbPointWon() );
    }
    
}