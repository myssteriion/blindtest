package com.myssteriion.blindtest.persistence.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.myssteriion.blindtest.model.common.Rank;
import com.myssteriion.utils.CommonUtils;
import com.myssteriion.utils.persistence.converter.AbstractConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A Map<Rank, Integer> - json converter.
 */
public class RankConverter extends AbstractConverter<Rank, Integer> {

    @Override
    protected Map<Rank, Integer> convertToMap(String json) throws IOException {
        return CommonUtils.MAPPER.readValue(json, new TypeReference<HashMap<Rank, Integer>>() {});
    }

}