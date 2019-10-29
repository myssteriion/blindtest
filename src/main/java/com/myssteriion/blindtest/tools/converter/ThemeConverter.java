package com.myssteriion.blindtest.tools.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.tools.Tool;

import javax.persistence.Converter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Convert json to map (and reverse).
 */
@Converter()
public class ThemeConverter extends AbstractConverter<Theme> {

    @Override
    protected Map<Theme, Integer> convertToMap(String json) throws IOException {
        return Tool.MAPPER.readValue(json, new TypeReference<HashMap<Theme, Integer>>() {});
    }

}
