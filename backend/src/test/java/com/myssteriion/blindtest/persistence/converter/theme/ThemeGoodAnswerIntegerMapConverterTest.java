package com.myssteriion.blindtest.persistence.converter.theme;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.GoodAnswer;
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

class ThemeGoodAnswerIntegerMapConverterTest extends AbstractTest {
    
    @Test
    void convertToDatabaseColumn() throws NoSuchFieldException, IllegalAccessException, JsonProcessingException {
        
        ThemeGoodAnswerIntegerMapConverter converter = new ThemeGoodAnswerIntegerMapConverter();
        
        Assertions.assertEquals( CommonConstant.EMPTY_JSON, converter.convertToDatabaseColumn(null) );
        Assertions.assertEquals( CommonConstant.EMPTY_JSON, converter.convertToDatabaseColumn(new HashMap<>()) );
        
        Map<GoodAnswer, Integer> m = new HashMap<>();
        m.put(GoodAnswer.AUTHOR, 2);
        m.put(GoodAnswer.TITLE, 4);
        
        Map<Theme, Map<GoodAnswer, Integer>> map = new HashMap<>();
        map.put(Theme.ANNEES_90, m);
        
        String caseOne = "{\"ANNEES_90\":{\"AUTHOR\":2,\"TITLE\":4}}";
        String caseTwo = "{\"ANNEES_90\":{\"TITLE\":4,\"AUTHOR\":2}}";
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
            Whitebox.setInternalState( converter, "mapper", new ObjectMapper() );
        }
    }
    
    @Test
    void convertToEntityAttributeString() {
        
        ThemeGoodAnswerIntegerMapConverter converter = new ThemeGoodAnswerIntegerMapConverter();
        
        Assertions.assertEquals( new HashMap<String, Integer>(), converter.convertToEntityAttribute(null) );
        Assertions.assertEquals( new HashMap<String, Integer>(), converter.convertToEntityAttribute("") );
        
        Map<Theme, Map<GoodAnswer, Integer>> actual = converter.convertToEntityAttribute("{\"ANNEES_90\":{\"AUTHOR\":2,\"TITLE\":4}}");
        Assertions.assertEquals( 1, actual.size() );
        Assertions.assertEquals( Integer.valueOf(2), actual.get(Theme.ANNEES_90).get(GoodAnswer.AUTHOR) );
        Assertions.assertEquals( Integer.valueOf(4), actual.get(Theme.ANNEES_90).get(GoodAnswer.TITLE) );
        
        TestUtils.assertThrow( CustomRuntimeException.class, "Can't parse json.",
                () -> converter.convertToEntityAttribute("{\"name\":\"pouet\",\"number\":4}") );
    }
    
    @Test
    void convertToMap() throws IOException {
        
        ThemeGoodAnswerIntegerMapConverter converter = new ThemeGoodAnswerIntegerMapConverter();
        
        Map<Theme, Map<GoodAnswer, Integer>> actual = converter.convertToMap("{\"ANNEES_90\":{\"AUTHOR\":2,\"TITLE\":4}}");
        Assertions.assertEquals( 1, actual.size() );
        Assertions.assertEquals( Integer.valueOf(2), actual.get(Theme.ANNEES_90).get(GoodAnswer.AUTHOR) );
        Assertions.assertEquals( Integer.valueOf(4), actual.get(Theme.ANNEES_90).get(GoodAnswer.TITLE) );
    }
    
}