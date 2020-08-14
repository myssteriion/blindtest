package com.myssteriion.blindtest.persistence.converter;

import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.utils.CommonConstant;
import com.myssteriion.utils.CommonUtils;

import javax.persistence.AttributeConverter;

public class ThemeConverter implements AttributeConverter<Theme, String> {
    
    @Override
    public String convertToDatabaseColumn(Theme theme) {
        return (theme == null) ? CommonConstant.EMPTY : theme.toString();
    }
    
    @Override
    public Theme convertToEntityAttribute(String str) {
        return ( CommonUtils.isNullOrEmpty(str) ) ? null : Theme.valueOf(str);
    }
    
}
