package com.myssteriion.blindtest.persistence.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.utils.CommonUtils;
import com.myssteriion.utils.persistence.converter.AbstractConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A Map<Theme, Integer> - json converter.
 */
public class ThemeConverter extends AbstractConverter<Theme, Integer> {

    @Override
    protected Map<Theme, Integer> convertToMap(String json) throws IOException {
        return CommonUtils.MAPPER.readValue(json, new TypeReference<HashMap<Theme, Integer>>() {});
    }

}
