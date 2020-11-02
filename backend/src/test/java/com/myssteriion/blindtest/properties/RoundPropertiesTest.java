package com.myssteriion.blindtest.properties;

import com.myssteriion.blindtest.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RoundPropertiesTest extends AbstractTest {
    
    @Autowired
    private RoundProperties props;
    
    
    
    @Test
    void getterSetter() {
        
        Assertions.assertEquals( Integer.valueOf(20), props.getClassicNbMusics() );
        Assertions.assertEquals( Integer.valueOf(100), props.getClassicNbPointWon() );
        
        Assertions.assertEquals( Integer.valueOf(12), props.getChoiceNbMusics() );
        Assertions.assertEquals( Integer.valueOf(100), props.getChoiceNbPointWon() );
        Assertions.assertEquals( Integer.valueOf(50), props.getChoiceNbPointBonus() );
        Assertions.assertEquals( Integer.valueOf(-50), props.getChoiceNbPointMalus() );
        
        Assertions.assertEquals( Integer.valueOf(10), props.getLuckyNbMusics() );
        Assertions.assertEquals( Integer.valueOf(150), props.getLuckyNbPointWon() );
        Assertions.assertEquals( Integer.valueOf(100), props.getLuckyNbPointBonus() );
        
        Assertions.assertEquals( Integer.valueOf(20), props.getThiefNbMusics() );
        Assertions.assertEquals( Integer.valueOf(100), props.getThiefNbPointWon() );
        Assertions.assertEquals( Integer.valueOf(-100), props.getThiefNbPointLoose() );
        
        Assertions.assertEquals( Integer.valueOf(10), props.getRecoveryNbMusics() );
        Assertions.assertEquals( Integer.valueOf(30), props.getRecoveryNbPointWon() );
    }
    
}