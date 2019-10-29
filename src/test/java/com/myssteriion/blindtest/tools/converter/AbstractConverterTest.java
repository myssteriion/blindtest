package com.myssteriion.blindtest.tools.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Rank;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.blindtest.tools.exception.CustomRuntimeException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

public class AbstractConverterTest extends AbstractTest {

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
    public void convertToEntityAttributeDuration() {

        DurationConverter converter = new DurationConverter();

        Assert.assertEquals( new HashMap<Duration, Integer>(), converter.convertToEntityAttribute(null) );
        Assert.assertEquals( new HashMap<Duration, Integer>(), converter.convertToEntityAttribute("") );

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
    public void convertToEntityAttributeTheme() {

        ThemeConverter converter = new ThemeConverter();

        Assert.assertEquals( new HashMap<Duration, Integer>(), converter.convertToEntityAttribute(null) );
        Assert.assertEquals( new HashMap<Duration, Integer>(), converter.convertToEntityAttribute("") );

        Map<Theme, Integer> actual = converter.convertToEntityAttribute("{\"ANNEES_80\":2,\"ANNEES_90\":4}");
        Assert.assertEquals( 2, actual.size() );
        Assert.assertEquals( new Integer(2), actual.get(Theme.ANNEES_80) );
        Assert.assertEquals( new Integer(4), actual.get(Theme.ANNEES_90) );

        try {
            converter.convertToEntityAttribute("{\"name\":\"pouet\",\"number\":4}");
            Assert.fail("Doit lever une CustomRuntimeException car le json est KO.");
        }
        catch (CustomRuntimeException e) {
            verifyException(new CustomRuntimeException("Can't parse json.", e.getCause()), e);
        }
    }

    @Test
    public void convertToEntityAttributeRank() {

        RankConverter converter = new RankConverter();

        Assert.assertEquals( new HashMap<Duration, Integer>(), converter.convertToEntityAttribute(null) );
        Assert.assertEquals( new HashMap<Duration, Integer>(), converter.convertToEntityAttribute("") );

        Map<Rank, Integer> actual = converter.convertToEntityAttribute("{\"FIRST\":2,\"SECOND\":4}");
        Assert.assertEquals( 2, actual.size() );
        Assert.assertEquals( new Integer(2), actual.get(Rank.FIRST) );
        Assert.assertEquals( new Integer(4), actual.get(Rank.SECOND) );

        try {
            converter.convertToEntityAttribute("{\"name\":\"pouet\",\"number\":4}");
            Assert.fail("Doit lever une CustomRuntimeException car le json est KO.");
        }
        catch (CustomRuntimeException e) {
            verifyException(new CustomRuntimeException("Can't parse json.", e.getCause()), e);
        }
    }

}