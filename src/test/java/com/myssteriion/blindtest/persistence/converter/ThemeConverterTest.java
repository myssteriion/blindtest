package com.myssteriion.blindtest.persistence.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.utils.CommonConstant;
import com.myssteriion.utils.exception.CustomRuntimeException;
import com.myssteriion.utils.test.TestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ThemeConverterTest extends AbstractTest {

    @Test
    public void convertToDatabaseColumn() throws NoSuchFieldException, IllegalAccessException, JsonProcessingException {

        ThemeConverter converter = new ThemeConverter();

        Assert.assertEquals( CommonConstant.EMPTY_JSON, converter.convertToDatabaseColumn(null) );
        Assert.assertEquals( CommonConstant.EMPTY_JSON, converter.convertToDatabaseColumn(new HashMap<>()) );

        Map<Theme, Integer> map = new HashMap<>();
        map.put(Theme.ANNEES_90, 2);
        map.put(Theme.ANNEES_80, 4);

        String caseOne = "{\"ANNEES_90\":2,\"ANNEES_80\":4}";
        String caseTwo = "{\"ANNEES_80\":4,\"ANNEES_90\":2}";
        String actual = converter.convertToDatabaseColumn(map);
        Assert.assertTrue( actual.equals(caseOne) || actual.equals(caseTwo) );

        JsonProcessingException jpe = Mockito.mock(JsonProcessingException.class);
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(jpe);
        TestUtils.setMapper(mapper);

        try {
            converter.convertToDatabaseColumn(map);
            Assert.fail("Doit lever une CustomRuntimeException car le mock throw.");
        }
        catch (CustomRuntimeException e) {
            TestUtils.verifyException(new CustomRuntimeException("Can't parse json.", e.getCause()), e);
        }
        finally {
            TestUtils.setMapper( new ObjectMapper() );
        }
    }
    
    @Test
    public void convertToEntityAttributeString() {

        ThemeConverter converter = new ThemeConverter();

        Assert.assertEquals( new HashMap<String, Integer>(), converter.convertToEntityAttribute(null) );
        Assert.assertEquals( new HashMap<String, Integer>(), converter.convertToEntityAttribute("") );

        Map<Theme, Integer> actual = converter.convertToEntityAttribute("{\"ANNEES_90\":2,\"ANNEES_80\":4}");
        Assert.assertEquals( 2, actual.size() );
        Assert.assertEquals( new Integer(2), actual.get(Theme.ANNEES_90) );
        Assert.assertEquals( new Integer(4), actual.get(Theme.ANNEES_80) );

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

        ThemeConverter converter = new ThemeConverter();

        Map<Theme, Integer> actual = converter.convertToMap("{\"ANNEES_90\":2,\"ANNEES_80\":4}");
        Assert.assertEquals( 2, actual.size() );
        Assert.assertEquals( new Integer(2), actual.get(Theme.ANNEES_90) );
        Assert.assertEquals( new Integer(4), actual.get(Theme.ANNEES_80) );
    }

}