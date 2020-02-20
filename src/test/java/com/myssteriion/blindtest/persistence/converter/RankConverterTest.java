package com.myssteriion.blindtest.persistence.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myssteriion.blindtest.model.common.Rank;
import com.myssteriion.utils.CommonConstant;
import com.myssteriion.utils.exception.CustomRuntimeException;
import com.myssteriion.utils.test.AbstractTest;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RankConverterTest extends AbstractTest {

    @Test
    public void convertToDatabaseColumn() throws NoSuchFieldException, IllegalAccessException, JsonProcessingException {

        RankConverter converter = new RankConverter();

        Assert.assertEquals( CommonConstant.EMPTY_JSON, converter.convertToDatabaseColumn(null) );
        Assert.assertEquals( CommonConstant.EMPTY_JSON, converter.convertToDatabaseColumn(new HashMap<>()) );

        Map<Rank, Integer> map = new HashMap<>();
        map.put(Rank.SECOND, 2);
        map.put(Rank.FOURTH, 4);

        String caseOne = "{\"SECOND\":2,\"FOURTH\":4}";
        String caseTwo = "{\"FOURTH\":4,\"SECOND\":2}";
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

        RankConverter converter = new RankConverter();

        Assert.assertEquals( new HashMap<String, Integer>(), converter.convertToEntityAttribute(null) );
        Assert.assertEquals( new HashMap<String, Integer>(), converter.convertToEntityAttribute("") );

        Map<Rank, Integer> actual = converter.convertToEntityAttribute("{\"SECOND\":2,\"FOURTH\":4}");
        Assert.assertEquals( 2, actual.size() );
        Assert.assertEquals( new Integer(2), actual.get(Rank.SECOND) );
        Assert.assertEquals( new Integer(4), actual.get(Rank.FOURTH) );

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

        RankConverter converter = new RankConverter();

        Map<Rank, Integer> actual = converter.convertToMap("{\"SECOND\":2,\"FOURTH\":4}");
        Assert.assertEquals( 2, actual.size() );
        Assert.assertEquals( new Integer(2), actual.get(Rank.SECOND) );
        Assert.assertEquals( new Integer(4), actual.get(Rank.FOURTH) );
    }

}