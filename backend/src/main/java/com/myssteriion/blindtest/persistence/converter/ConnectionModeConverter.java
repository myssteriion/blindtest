package com.myssteriion.blindtest.persistence.converter;

import com.myssteriion.blindtest.model.common.ConnectionMode;
import com.myssteriion.utils.CommonConstant;
import com.myssteriion.utils.CommonUtils;

import javax.persistence.AttributeConverter;

public class ConnectionModeConverter implements AttributeConverter<ConnectionMode, String> {
    
    @Override
    public String convertToDatabaseColumn(ConnectionMode connectionMode) {
        return (connectionMode == null) ? CommonConstant.EMPTY : connectionMode.toString();
    }
    
    @Override
    public ConnectionMode convertToEntityAttribute(String str) {
        return ( CommonUtils.isNullOrEmpty(str) ) ? null : ConnectionMode.valueOf(str);
    }
    
}
