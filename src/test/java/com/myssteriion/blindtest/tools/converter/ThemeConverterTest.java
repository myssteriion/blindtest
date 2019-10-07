package com.myssteriion.blindtest.tools.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.blindtest.tools.exception.CustomRuntimeException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

public class ThemeConverterTest extends AbstractTest {

    @Test
    public void convertToDatabaseColumn() throws JsonProcessingException, NoSuchFieldException, IllegalAccessException {

        ThemeConverter converter = new ThemeConverter();

        Assert.assertEquals( Constant.EMPTY_JSON, converter.convertToDatabaseColumn(null) );
        Assert.assertEquals( Constant.EMPTY_JSON, converter.convertToDatabaseColumn(new HashMap<>()) );

        Map<Theme, Integer> map = new HashMap<>();
        map.put(Theme.ANNEES_60, 2);
        map.put(Theme.ANNEES_70, 4);

        String caseOne = "{\"ANNEES_60\":2,\"ANNEES_70\":4}";
        String caseTwo = "{\"ANNEES_70\":4,\"ANNEES_60\":2}";
        String actual = converter.convertToDatabaseColumn(map);
        Assert.assertTrue( actual.equals(caseOne) || actual.equals(caseTwo) );

        JsonProcessingException jpe = Mockito.mock(JsonProcessingException.class);
        ObjectMapper mapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mapper.writeValueAsString(Mockito.any())).thenThrow(jpe);
        setMapper(mapper);

        try {
            converter.convertToDatabaseColumn(map);
//            Assert.fail("Doit lever une CustomRuntimeException car le mock throw.");
        }
        catch (CustomRuntimeException e) {
            verifyException(new CustomRuntimeException("Can't parse json.", e.getCause()), e);
        }
        finally {
            setMapper( new ObjectMapper() );
        }
    }

    @Test
    public void convertToEntityAttribute() {

        ThemeConverter converter = new ThemeConverter();

        Assert.assertEquals( new HashMap<Theme, Integer>(), converter.convertToEntityAttribute(null) );
        Assert.assertEquals( new HashMap<Theme, Integer>(), converter.convertToEntityAttribute("") );

        Map<Theme, Integer> map = new HashMap<>();
        map.put(Theme.ANNEES_60, 2);
        map.put(Theme.ANNEES_70, 4);

        Assert.assertEquals( map, converter.convertToEntityAttribute("{\"ANNEES_60\":2,\"ANNEES_70\":4}") );

        try {
            converter.convertToEntityAttribute("{\"name\":\"pouet\",\"number\":4}");
            Assert.fail("Doit lever une CustomRuntimeException car le json est KO.");
        }
        catch (CustomRuntimeException e) {
            verifyException(new CustomRuntimeException("Can't parse json.", e.getCause()), e);
        }
    }
}