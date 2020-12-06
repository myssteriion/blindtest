package com.myssteriion.blindtest;

import com.myssteriion.blindtest.properties.ConfigProperties;
import com.myssteriion.blindtest.properties.RoundProperties;
import com.myssteriion.utils.model.other.StringCipher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class AbstractTest {
    
    @Autowired
    protected StringCipher stringCipher;
    
    @Autowired
    protected ConfigProperties configProperties;
    
    @Autowired
    protected RoundProperties roundProperties;
    
}
