package com.myssteriion.blindtest.persistence.converter.theme;

import com.fasterxml.jackson.core.type.TypeReference;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.utils.persistence.converter.AbstractMapConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A Map<Theme, String> - json converter.
 */
public class ThemeStringMapConverter extends AbstractMapConverter<Theme, String> {
    
    @Override
    protected Map<Theme, String> convertToMap(String json) throws IOException {
        return mapper.readValue(json, new TypeReference<HashMap<Theme, String>>() {});
    }
    
}
