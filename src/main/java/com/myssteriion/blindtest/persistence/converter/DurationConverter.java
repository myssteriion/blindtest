package com.myssteriion.blindtest.persistence.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.utils.CommonUtils;
import com.myssteriion.utils.persistence.converter.AbstractConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A Map<Duration, Integer> - json converter.
 */
public class DurationConverter extends AbstractConverter<Duration, Integer> {

    @Override
    protected Map<Duration, Integer> convertToMap(String json) throws IOException {
        return CommonUtils.MAPPER.readValue(json, new TypeReference<HashMap<Duration, Integer>>() {});
    }

}