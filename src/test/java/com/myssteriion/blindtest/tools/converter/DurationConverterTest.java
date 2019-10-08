package com.myssteriion.blindtest.tools.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.blindtest.tools.exception.CustomRuntimeException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

public class DurationConverterTest extends AbstractTest {

    @Test
    public void convertToDatabaseColumn() throws NoSuchFieldException, IllegalAccessException, JsonProcessingException {

        DurationConverter converter = new DurationConverter();

        Assert.assertEquals( Constant.EMPTY_JSON, converter.convertToDatabaseColumn(null) );
        Assert.assertEquals( Constant.EMPTY_JSON, converter.convertToDatabaseColumn(new HashMap<>()) );

        Map<Duration, Integer> map = new HashMap<>();
        map.put(Duration.SHORT, 2);
        map.put(Duration.LONG, 4);

        String caseOne = "{\"SHORT\":2,\"LONG\":4}";
        String caseTwo = "{\"LONG\":4,\"SHORT\":2}";
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

        DurationConverter converter = new DurationConverter();

        Assert.assertEquals( new HashMap<Duration, Integer>(), converter.convertToEntityAttribute(null) );
        Assert.assertEquals( new HashMap<Duration, Integer>(), converter.convertToEntityAttribute("") );

        Map<Duration, Integer> map = new HashMap<>();
        map.put(Duration.SHORT, 2);
        map.put(Duration.LONG, 4);

        Assert.assertEquals( map, converter.convertToEntityAttribute("{\"SHORT\":2,\"LONG\":4}") );

        try {
            converter.convertToEntityAttribute("{\"name\":\"pouet\",\"number\":4}");
            Assert.fail("Doit lever une CustomRuntimeException car le json est KO.");
        }
        catch (CustomRuntimeException e) {
            verifyException(new CustomRuntimeException("Can't parse json.", e.getCause()), e);
        }
    }

}