package com.myssteriion.blindtest.persistence.converter.theme;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.GoodAnswer;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.utils.CommonConstant;
import com.myssteriion.utils.exception.CustomRuntimeException;
import com.myssteriion.utils.test.TestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ThemeGoodAnswerIntegerMapConverterTest extends AbstractTest {
    
    @Test
    public void convertToDatabaseColumn() throws NoSuchFieldException, IllegalAccessException, JsonProcessingException {
        
        ThemeGoodAnswerIntegerMapConverter converter = new ThemeGoodAnswerIntegerMapConverter();
        
        Assert.assertEquals( CommonConstant.EMPTY_JSON, converter.convertToDatabaseColumn(null) );
        Assert.assertEquals( CommonConstant.EMPTY_JSON, converter.convertToDatabaseColumn(new HashMap<>()) );
        
        Map<GoodAnswer, Integer> m = new HashMap<>();
        m.put(GoodAnswer.AUTHOR, 2);
        m.put(GoodAnswer.TITLE, 4);
        
        Map<Theme, Map<GoodAnswer, Integer>> map = new HashMap<>();
        map.put(Theme.ANNEES_90, m);
        
        String caseOne = "{\"ANNEES_90\":{\"AUTHOR\":2,\"TITLE\":4}}";
        String caseTwo = "{\"ANNEES_90\":{\"TITLE\":4,\"AUTHOR\":2}}";
        String actual = converter.convertToDatabaseColumn(map);
        Assert.assertTrue( actual.equals(caseOne) || actual.equals(caseTwo) );
        
        JsonProcessingException jpe = Mockito.mock(JsonProcessingException.class);
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(jpe);
        Whitebox.setInternalState(converter, "mapper", mapper);
        
        try {
            converter.convertToDatabaseColumn(map);
            Assert.fail("Doit lever une CustomRuntimeException car le mock throw.");
        }
        catch (CustomRuntimeException e) {
            TestUtils.verifyException(new CustomRuntimeException("Can't parse json.", e.getCause()), e);
        }
        finally {
            Whitebox.setInternalState( converter, "mapper", new ObjectMapper() );
        }
    }
    
    @Test
    public void convertToEntityAttributeString() {
        
        ThemeGoodAnswerIntegerMapConverter converter = new ThemeGoodAnswerIntegerMapConverter();
        
        Assert.assertEquals( new HashMap<String, Integer>(), converter.convertToEntityAttribute(null) );
        Assert.assertEquals( new HashMap<String, Integer>(), converter.convertToEntityAttribute("") );
        
        Map<Theme, Map<GoodAnswer, Integer>> actual = converter.convertToEntityAttribute("{\"ANNEES_90\":{\"AUTHOR\":2,\"TITLE\":4}}");
        Assert.assertEquals( 1, actual.size() );
        Assert.assertEquals( Integer.valueOf(2), actual.get(Theme.ANNEES_90).get(GoodAnswer.AUTHOR) );
        Assert.assertEquals( Integer.valueOf(4), actual.get(Theme.ANNEES_90).get(GoodAnswer.TITLE) );
        
        try {
            converter.convertToEntityAttribute("{\"name\":\"pouet\",\"number\":4}");
            Assert.fail("Doit lever une CustomRuntimeException car le json est KO.");
        }
        catch (CustomRuntimeException e) {
            TestUtils.verifyException(new CustomRuntimeException("Can't parse json.", e.getCause()), e);
        }
    }
    
    @Test
    public void convertToMap() throws IOException {
        
        ThemeGoodAnswerIntegerMapConverter converter = new ThemeGoodAnswerIntegerMapConverter();
        
        Map<Theme, Map<GoodAnswer, Integer>> actual = converter.convertToMap("{\"ANNEES_90\":{\"AUTHOR\":2,\"TITLE\":4}}");
        Assert.assertEquals( 1, actual.size() );
        Assert.assertEquals( Integer.valueOf(2), actual.get(Theme.ANNEES_90).get(GoodAnswer.AUTHOR) );
        Assert.assertEquals( Integer.valueOf(4), actual.get(Theme.ANNEES_90).get(GoodAnswer.TITLE) );
    }
    
}