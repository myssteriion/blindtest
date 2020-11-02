package com.myssteriion.blindtest.persistence.converter.theme;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.utils.CommonConstant;
import com.myssteriion.utils.exception.CustomRuntimeException;
import com.myssteriion.utils.test.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class ThemeIntegerConverterTest extends AbstractTest {
    
    @Test
    void convertToDatabaseColumn() throws NoSuchFieldException, IllegalAccessException, JsonProcessingException {
        
        ThemeIntegerMapConverter converter = new ThemeIntegerMapConverter();
        
        Assertions.assertEquals( CommonConstant.EMPTY_JSON, converter.convertToDatabaseColumn(null) );
        Assertions.assertEquals( CommonConstant.EMPTY_JSON, converter.convertToDatabaseColumn(new HashMap<>()) );
        
        Map<Theme, Integer> map = new HashMap<>();
        map.put(Theme.ANNEES_90, 2);
        map.put(Theme.ANNEES_80, 4);
        
        String caseOne = "{\"ANNEES_90\":2,\"ANNEES_80\":4}";
        String caseTwo = "{\"ANNEES_80\":4,\"ANNEES_90\":2}";
        String actual = converter.convertToDatabaseColumn(map);
        Assertions.assertTrue( actual.equals(caseOne) || actual.equals(caseTwo) );
        
        JsonProcessingException jpe = Mockito.mock(JsonProcessingException.class);
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(jpe);
        ReflectionTestUtils.setField(converter, "mapper", mapper);
        
        try {
            TestUtils.assertThrow( CustomRuntimeException.class, "Can't parse json.",
                    () -> converter.convertToDatabaseColumn(map) );
        }
        finally {
            ReflectionTestUtils.setField( converter, "mapper", new ObjectMapper() );
        }
    }
    
    @Test
    void convertToEntityAttributeString() {
        
        ThemeIntegerMapConverter converter = new ThemeIntegerMapConverter();
        
        Assertions.assertEquals( new HashMap<String, Integer>(), converter.convertToEntityAttribute(null) );
        Assertions.assertEquals( new HashMap<String, Integer>(), converter.convertToEntityAttribute("") );
        
        Map<Theme, Integer> actual = converter.convertToEntityAttribute("{\"ANNEES_90\":2,\"ANNEES_80\":4}");
        Assertions.assertEquals( 2, actual.size() );
        Assertions.assertEquals( Integer.valueOf(2), actual.get(Theme.ANNEES_90) );
        Assertions.assertEquals( Integer.valueOf(4), actual.get(Theme.ANNEES_80) );
        
        TestUtils.assertThrow( CustomRuntimeException.class, "Can't parse json.",
                () -> converter.convertToEntityAttribute("{\"name\":\"pouet\",\"number\":4}") );
    }
    
    @Test
    void convertToMap() throws IOException {
        
        ThemeIntegerMapConverter converter = new ThemeIntegerMapConverter();
        
        Map<Theme, Integer> actual = converter.convertToMap("{\"ANNEES_90\":2,\"ANNEES_80\":4}");
        Assertions.assertEquals( 2, actual.size() );
        Assertions.assertEquals( Integer.valueOf(2), actual.get(Theme.ANNEES_90) );
        Assertions.assertEquals( Integer.valueOf(4), actual.get(Theme.ANNEES_80) );
    }
    
}