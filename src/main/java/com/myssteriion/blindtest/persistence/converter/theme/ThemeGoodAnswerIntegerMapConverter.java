package com.myssteriion.blindtest.persistence.converter.theme;

import com.fasterxml.jackson.core.type.TypeReference;
import com.myssteriion.blindtest.model.common.GoodAnswer;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.utils.CommonUtils;
import com.myssteriion.utils.persistence.converter.AbstractMapConverter;

import java.io.IOException;
import java.util.Map;

/**
 * A Map< Theme, Map<GoodAnswer, Integer> > - json converter.
 */
public class ThemeGoodAnswerIntegerMapConverter extends AbstractMapConverter< Theme, Map<GoodAnswer, Integer> > {
    
    @Override
    protected Map< Theme, Map<GoodAnswer, Integer> > convertToMap(String json) throws IOException {
        return CommonUtils.MAPPER.readValue(json, new TypeReference< Map<Theme, Map<GoodAnswer, Integer>> >() {});
    }
    
}
