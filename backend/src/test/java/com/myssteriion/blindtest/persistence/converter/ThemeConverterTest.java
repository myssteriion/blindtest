package com.myssteriion.blindtest.persistence.converter;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.utils.CommonConstant;
import com.myssteriion.utils.test.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ThemeConverterTest extends AbstractTest {
    
    @Test
    void convertToDatabaseColumn() {
        
        ThemeConverter converter = new ThemeConverter();
        
        Assertions.assertEquals( CommonConstant.EMPTY, converter.convertToDatabaseColumn(null) );
        Assertions.assertEquals( Theme.ANNEES_60.toString(), converter.convertToDatabaseColumn(Theme.ANNEES_60) );
    }
    
    @Test
    void convertToEntityAttribute() {
        
        ThemeConverter converter = new ThemeConverter();
        
        Assertions.assertNull( converter.convertToEntityAttribute(null) );
        Assertions.assertNull( converter.convertToEntityAttribute("") );
        
        TestUtils.assertThrow( IllegalArgumentException.class,
                "No enum constant com.myssteriion.blindtest.model.common.Theme.pouet",
                () -> converter.convertToEntityAttribute("pouet") );
        
        Assertions.assertEquals( Theme.ANNEES_60, converter.convertToEntityAttribute(Theme.ANNEES_60.toString()) );
    }
    
}