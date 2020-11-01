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
import org.powermock.reflect.Whitebox;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class ThemeStringConverterTest extends AbstractTest {
    
    @Test
    void convertToDatabaseColumn() throws NoSuchFieldException, IllegalAccessException, JsonProcessingException {
        
        ThemeStringMapConverter converter = new ThemeStringMapConverter();
        
        Assertions.assertEquals( CommonConstant.EMPTY_JSON, converter.convertToDatabaseColumn(null) );
        Assertions.assertEquals( CommonConstant.EMPTY_JSON, converter.convertToDatabaseColumn(new HashMap<>()) );
        
        Map<Theme, String> map = new HashMap<>();
        map.put(Theme.ANNEES_90, "a");
        map.put(Theme.ANNEES_80, "b");
        
        String caseOne = "{\"ANNEES_90\":\"a\",\"ANNEES_80\":\"b\"}";
        String caseTwo = "{\"ANNEES_80\":\"b\",\"ANNEES_90\":\"a\"}";
        String actual = converter.convertToDatabaseColumn(map);
        Assertions.assertTrue( actual.equals(caseOne) || actual.equals(caseTwo) );
        
        JsonProcessingException jpe = Mockito.mock(JsonProcessingException.class);
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(jpe);
        Whitebox.setInternalState(converter, "mapper", mapper);
        
        try {
            TestUtils.assertThrow( CustomRuntimeException.class, "Can't parse json.",
                    () -> converter.convertToDatabaseColumn(map) );
        }
        finally {
            Whitebox.setInternalState(converter, "mapper", new ObjectMapper() );
        }
    }
    
    @Test
    void convertToEntityAttributeString() {
        
        ThemeStringMapConverter converter = new ThemeStringMapConverter();
        
        Assertions.assertEquals( new HashMap<String, Integer>(), converter.convertToEntityAttribute(null) );
        Assertions.assertEquals( new HashMap<String, Integer>(), converter.convertToEntityAttribute("") );
        
        Map<Theme, String> actual = converter.convertToEntityAttribute("{\"ANNEES_90\":\"a\",\"ANNEES_80\":\"b\"}");
        Assertions.assertEquals( 2, actual.size() );
        Assertions.assertEquals( "a", actual.get(Theme.ANNEES_90) );
        Assertions.assertEquals( "b", actual.get(Theme.ANNEES_80) );
        
        TestUtils.assertThrow( CustomRuntimeException.class, "Can't parse json.",
                () -> converter.convertToEntityAttribute("{\"name\":\"pouet\",\"number\":\"b\"}") );
    }
    
    @Test
    void convertToMap() throws IOException {
        
        ThemeStringMapConverter converter = new ThemeStringMapConverter();
        
        Map<Theme, String> actual = converter.convertToMap("{\"ANNEES_90\":\"a\",\"ANNEES_80\":\"b\"}");
        Assertions.assertEquals( 2, actual.size() );
        Assertions.assertEquals( "a", actual.get(Theme.ANNEES_90) );
        Assertions.assertEquals( "b", actual.get(Theme.ANNEES_80) );
    }
    
}