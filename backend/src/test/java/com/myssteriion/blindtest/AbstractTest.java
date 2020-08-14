package com.myssteriion.blindtest;

import com.myssteriion.blindtest.properties.ConfigProperties;
import com.myssteriion.blindtest.properties.RoundContentProperties;
import com.myssteriion.utils.cipher.StringCipher;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractTest {
    
    @Autowired
    protected StringCipher stringCipher;
    
    @Autowired
    protected ConfigProperties configProperties;
    
    @Autowired
    protected RoundContentProperties roundContentProperties;
    
}
