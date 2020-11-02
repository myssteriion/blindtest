package com.myssteriion.blindtest.persistence.converter.duration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
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

class DurationIntegerMapConverterTest extends AbstractTest {
    
    @Test
    void convertToDatabaseColumn() throws NoSuchFieldException, IllegalAccessException, JsonProcessingException {
        
        DurationIntegerMapConverter converter = new DurationIntegerMapConverter();
        
        Assertions.assertEquals( CommonConstant.EMPTY_JSON, converter.convertToDatabaseColumn(null) );
        Assertions.assertEquals( CommonConstant.EMPTY_JSON, converter.convertToDatabaseColumn(new HashMap<>()) );
        
        Map<Duration, Integer> map = new HashMap<>();
        map.put(Duration.SHORT, 2);
        map.put(Duration.LONG, 4);
        
        String caseOne = "{\"SHORT\":2,\"LONG\":4}";
        String caseTwo = "{\"LONG\":4,\"SHORT\":2}";
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
            ReflectionTestUtils.setField( converter, "mapper",  new ObjectMapper() );
        }
    }
    
    @Test
    void convertToEntityAttributeString() {
        
        DurationIntegerMapConverter converter = new DurationIntegerMapConverter();
        
        Assertions.assertEquals( new HashMap<String, Integer>(), converter.convertToEntityAttribute(null) );
        Assertions.assertEquals( new HashMap<String, Integer>(), converter.convertToEntityAttribute("") );
        
        Map<Duration, Integer> actual = converter.convertToEntityAttribute("{\"SHORT\":2,\"LONG\":4}");
        Assertions.assertEquals( 2, actual.size() );
        Assertions.assertEquals( Integer.valueOf(2), actual.get(Duration.SHORT) );
        Assertions.assertEquals( Integer.valueOf(4), actual.get(Duration.LONG) );
        
        TestUtils.assertThrow( CustomRuntimeException.class, "Can't parse json.",
                () -> converter.convertToEntityAttribute("{\"name\":\"pouet\",\"number\":4}") );
    }
    
    @Test
    void convertToMap() throws IOException {
        
        DurationIntegerMapConverter converter = new DurationIntegerMapConverter();
        
        Map<Duration, Integer> actual = converter.convertToMap("{\"SHORT\":2,\"LONG\":4}");
        Assertions.assertEquals( 2, actual.size() );
        Assertions.assertEquals( Integer.valueOf(2), actual.get(Duration.SHORT) );
        Assertions.assertEquals( Integer.valueOf(4), actual.get(Duration.LONG) );
    }
    
}