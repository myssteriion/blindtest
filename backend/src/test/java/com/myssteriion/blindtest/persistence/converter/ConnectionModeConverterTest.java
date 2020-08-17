package com.myssteriion.blindtest.persistence.converter;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.utils.CommonConstant;
import com.myssteriion.utils.test.TestUtils;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionModeConverterTest extends AbstractTest {
    
    @Test
    public void convertToDatabaseColumn() {
        
        ConnectionModeConverter converter = new ConnectionModeConverter();
        
        Assert.assertEquals( CommonConstant.EMPTY, converter.convertToDatabaseColumn(null) );
        Assert.assertEquals( ConnectionMode.OFFLINE.toString(), converter.convertToDatabaseColumn(ConnectionMode.OFFLINE) );
    }
    
    @Test
    public void convertToEntityAttribute() {
        
        ConnectionModeConverter converter = new ConnectionModeConverter();
        
        Assert.assertNull( converter.convertToEntityAttribute(null) );
        Assert.assertNull( converter.convertToEntityAttribute("") );
        
        try {
            converter.convertToEntityAttribute("pouet");
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("No enum constant com.myssteriion.blindtest.model.common.ConnectionMode.pouet"), e);
        }
        
        Assert.assertEquals( ConnectionMode.OFFLINE, converter.convertToEntityAttribute(ConnectionMode.OFFLINE.toString()) );
    }
    
}