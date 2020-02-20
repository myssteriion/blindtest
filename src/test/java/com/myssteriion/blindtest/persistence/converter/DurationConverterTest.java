package com.myssteriion.blindtest.persistence.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.utils.CommonConstant;
import com.myssteriion.utils.exception.CustomRuntimeException;
import com.myssteriion.utils.test.AbstractTest;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DurationConverterTest extends AbstractTest {

    @Test
    public void convertToDatabaseColumn() throws NoSuchFieldException, IllegalAccessException, JsonProcessingException {

        DurationConverter converter = new DurationConverter();

        Assert.assertEquals( CommonConstant.EMPTY_JSON, converter.convertToDatabaseColumn(null) );
        Assert.assertEquals( CommonConstant.EMPTY_JSON, converter.convertToDatabaseColumn(new HashMap<>()) );

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
            Assert.fail("Doit lever une CustomRuntimeException car le mock throw.");
        }
        catch (CustomRuntimeException e) {
            verifyException(new CustomRuntimeException("Can't parse json.", e.getCause()), e);
        }
        finally {
            setMapper( new ObjectMapper() );
        }
    }
    
    @Test
    public void convertToEntityAttributeString() {

        DurationConverter converter = new DurationConverter();

        Assert.assertEquals( new HashMap<String, Integer>(), converter.convertToEntityAttribute(null) );
        Assert.assertEquals( new HashMap<String, Integer>(), converter.convertToEntityAttribute("") );

        Map<Duration, Integer> actual = converter.convertToEntityAttribute("{\"SHORT\":2,\"LONG\":4}");
        Assert.assertEquals( 2, actual.size() );
        Assert.assertEquals( new Integer(2), actual.get(Duration.SHORT) );
        Assert.assertEquals( new Integer(4), actual.get(Duration.LONG) );

        try {
            converter.convertToEntityAttribute("{\"name\":\"pouet\",\"number\":4}");
            Assert.fail("Doit lever une CustomRuntimeException car le json est KO.");
        }
        catch (CustomRuntimeException e) {
            verifyException(new CustomRuntimeException("Can't parse json.", e.getCause()), e);
        }
    }

    @Test
    public void convertToMap() throws IOException {

        DurationConverter converter = new DurationConverter();

        Map<Duration, Integer> actual = converter.convertToMap("{\"SHORT\":2,\"LONG\":4}");
        Assert.assertEquals( 2, actual.size() );
        Assert.assertEquals( new Integer(2), actual.get(Duration.SHORT) );
        Assert.assertEquals( new Integer(4), actual.get(Duration.LONG) );
    }

}