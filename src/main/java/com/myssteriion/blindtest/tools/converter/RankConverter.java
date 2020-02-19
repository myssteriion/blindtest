package com.myssteriion.blindtest.tools.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.myssteriion.blindtest.model.common.Rank;
import com.myssteriion.utils.Tools;
import com.myssteriion.utils.persistence.converter.AbstractConverter;

import javax.persistence.Converter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Convert json to map (and reverse).
 */
@Converter()
public class RankConverter extends AbstractConverter<Rank> {

    @Override
    protected Map<Rank, Integer> convertToMap(String json) throws IOException {
        return Tools.MAPPER.readValue(json, new TypeReference<HashMap<Rank, Integer>>() {});
    }

}
