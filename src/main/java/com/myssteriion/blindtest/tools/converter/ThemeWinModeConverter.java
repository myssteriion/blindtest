package com.myssteriion.blindtest.tools.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.common.WinMode;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.blindtest.tools.Tool;
import com.myssteriion.blindtest.tools.exception.CustomRuntimeException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Convert json to map (and reverse).
 */
@Converter()
public class ThemeWinModeConverter implements AttributeConverter< Map<Theme, Map<WinMode, Integer>>, String> {


    @Override
    public String convertToDatabaseColumn(Map<Theme, Map<WinMode, Integer>> map) {

        try {

            if (map == null)
                return Constant.EMPTY_JSON;

            return Tool.MAPPER.writeValueAsString(map);
        }
        catch (final JsonProcessingException e) {
            throw new CustomRuntimeException("Can't parse json.", e);
        }

    }

    @Override
    public Map<Theme, Map<WinMode, Integer>> convertToEntityAttribute(String json) {

        try {

            if ( Tool.isNullOrEmpty(json) )
                return new HashMap<>();

            return Tool.MAPPER.readValue(json, new TypeReference<HashMap<Theme, HashMap<WinMode, Integer>>>() {});
        }
        catch (IOException e) {
            throw new CustomRuntimeException("Can't parse json.", e);
        }
    }

}
