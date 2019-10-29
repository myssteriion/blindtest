package com.myssteriion.blindtest.tools.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.blindtest.tools.Tool;
import com.myssteriion.blindtest.tools.exception.CustomRuntimeException;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Convert json to map (and reverse).
 */
public abstract class AbstractConverter<T> implements AttributeConverter<Map<T, Integer>, String> {

    @Override
    public String convertToDatabaseColumn(Map<T, Integer> map) {

        try {

            if  (map == null)
                return Constant.EMPTY_JSON;

            return Tool.MAPPER.writeValueAsString(map);
        }
        catch (final JsonProcessingException e) {
            throw new CustomRuntimeException("Can't parse json.", e);
        }
    }

    @Override
    public Map<T, Integer> convertToEntityAttribute(String json) {

        try {

            if ( Tool.isNullOrEmpty(json) )
                return new HashMap<>();

            return convertToMap(json);
        }
        catch (IOException e) {
            throw new CustomRuntimeException("Can't parse json.", e);
        }
    }

    /**
     * Convert json to map (T type doesn't work for read value).
     *
     * @param json the json
     * @return map
     */
    protected abstract Map<T, Integer> convertToMap(String json) throws IOException;

}
