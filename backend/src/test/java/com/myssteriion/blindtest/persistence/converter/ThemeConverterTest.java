package com.myssteriion.blindtest.persistence.converter;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.utils.CommonConstant;
import com.myssteriion.utils.test.TestUtils;
import org.junit.Assert;
import org.junit.Test;

public class ThemeConverterTest extends AbstractTest {
    
    @Test
    public void convertToDatabaseColumn() {
        
        ThemeConverter converter = new ThemeConverter();
        
        Assert.assertEquals( CommonConstant.EMPTY, converter.convertToDatabaseColumn(null) );
        Assert.assertEquals( Theme.ANNEES_60.toString(), converter.convertToDatabaseColumn(Theme.ANNEES_60) );
    }
    
    @Test
    public void convertToEntityAttribute() {
        
        ThemeConverter converter = new ThemeConverter();
        
        Assert.assertNull( converter.convertToEntityAttribute(null) );
        Assert.assertNull( converter.convertToEntityAttribute("") );
        
        try {
            converter.convertToEntityAttribute("pouet");
            Assert.fail("Doit lever une IllegalArgumentException car un champ est KO.");
        }
        catch (IllegalArgumentException e) {
            TestUtils.verifyException(new IllegalArgumentException("No enum constant com.myssteriion.blindtest.model.common.Theme.pouet"), e);
        }
        
        Assert.assertEquals( Theme.ANNEES_60, converter.convertToEntityAttribute(Theme.ANNEES_60.toString()) );
    }
    
}