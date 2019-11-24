package com.myssteriion.blindtest.tools.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.common.GoodAnswer;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.blindtest.tools.exception.CustomRuntimeException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

public class ThemeGoodAnswerConverterTest extends AbstractTest {

    @Test
    public void convertToDatabaseColumn() throws JsonProcessingException, NoSuchFieldException, IllegalAccessException {

        ThemeWinModeConverter converter = new ThemeWinModeConverter();

        Assert.assertEquals( Constant.EMPTY_JSON, converter.convertToDatabaseColumn(null) );
        Assert.assertEquals( Constant.EMPTY_JSON, converter.convertToDatabaseColumn(new HashMap<>()) );


        Map<Theme, Map<GoodAnswer, Integer>> map = new HashMap<>();

        Map<GoodAnswer, Integer> tmp = new HashMap<>();
        tmp.put(GoodAnswer.BOTH, 5);
        map.put(Theme.ANNEES_60, tmp);

        tmp = new HashMap<>();
        tmp.put(GoodAnswer.TITLE, 10);
        map.put(Theme.ANNEES_70, tmp);

        String expected1 = "{\"ANNEES_60\":{\"BOTH\":5},\"ANNEES_70\":{\"TITLE\":10}}";
        String expected2 = "{\"ANNEES_70\":{\"TITLE\":10},\"ANNEES_60\":{\"BOTH\":5}}";
        String actual = converter.convertToDatabaseColumn(map);
        Assert.assertTrue( actual.equals(expected1) || actual.equals(expected2) );



        map = new HashMap<>();

        map.put(Theme.ANNEES_90, new HashMap<>());

        tmp = new HashMap<>();
        tmp.put(GoodAnswer.BOTH, 5);
        map.put(Theme.ANNEES_80, tmp);

        expected1 = "{\"ANNEES_90\":{},\"ANNEES_80\":{\"BOTH\":5}}";
        expected2 = "{\"ANNEES_80\":{\"BOTH\":5},\"ANNEES_90\":{}}";
        actual = converter.convertToDatabaseColumn(map);
        Assert.assertTrue( actual.equals(expected1) || actual.equals(expected2) );



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

        ThemeWinModeConverter converter = new ThemeWinModeConverter();

        Assert.assertEquals( new HashMap<Duration, Integer>(), converter.convertToEntityAttribute(null) );
        Assert.assertEquals( new HashMap<Duration, Integer>(), converter.convertToEntityAttribute("") );

        String json = "{\"ANNEES_90\":{},\"ANNEES_80\":{\"BOTH\":5,\"TITLE\":10},\"ANNEES_60\":{\"AUTHOR\":0,\"BOTH\":15,\"TITLE\":100}}";

        Map<Theme, Map<GoodAnswer, Integer>> actual = converter.convertToEntityAttribute(json);
        Assert.assertEquals( new HashMap<>(), actual.get(Theme.ANNEES_90) );
        Assert.assertEquals( new Integer(5), actual.get(Theme.ANNEES_80).get(GoodAnswer.BOTH) );
        Assert.assertEquals( new Integer(10), actual.get(Theme.ANNEES_80).get(GoodAnswer.TITLE) );
        Assert.assertEquals( new Integer(15), actual.get(Theme.ANNEES_60).get(GoodAnswer.BOTH) );
        Assert.assertEquals( new Integer(100), actual.get(Theme.ANNEES_60).get(GoodAnswer.TITLE) );
        Assert.assertEquals( new Integer(0), actual.get(Theme.ANNEES_60).get(GoodAnswer.AUTHOR) );


        try {
            converter.convertToEntityAttribute("{\"name\":\"pouet\",\"number\":4}");
            Assert.fail("Doit lever une CustomRuntimeException car le json est KO.");
        }
        catch (CustomRuntimeException e) {
            verifyException(new CustomRuntimeException("Can't parse json.", e.getCause()), e);
        }
    }

}