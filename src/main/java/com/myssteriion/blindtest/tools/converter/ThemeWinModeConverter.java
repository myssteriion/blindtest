package com.myssteriion.blindtest.tools.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.common.GoodAnswer;
import com.myssteriion.blindtest.tools.Constant;
import com.myssteriion.utils.CommonConstant;
import com.myssteriion.utils.Tools;
import com.myssteriion.utils.exception.CustomRuntimeException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Convert json to map (and reverse).
 */
@Converter()
public class ThemeWinModeConverter implements AttributeConverter< Map<Theme, Map<GoodAnswer, Integer>>, String> {


    @Override
    public String convertToDatabaseColumn(Map<Theme, Map<GoodAnswer, Integer>> map) {

        try {

            if (map == null)
                return CommonConstant.EMPTY_JSON;

            return Tools.MAPPER.writeValueAsString(map);
        }
        catch (final JsonProcessingException e) {
            throw new CustomRuntimeException("Can't parse json.", e);
        }

    }

    @Override
    public Map<Theme, Map<GoodAnswer, Integer>> convertToEntityAttribute(String json) {

        try {

            if ( Tools.isNullOrEmpty(json) )
                return new HashMap<>();

            return Tools.MAPPER.readValue(json, new TypeReference<HashMap<Theme, HashMap<GoodAnswer, Integer>>>() {});
        }
        catch (IOException e) {
            throw new CustomRuntimeException("Can't parse json.", e);
        }
    }

}
