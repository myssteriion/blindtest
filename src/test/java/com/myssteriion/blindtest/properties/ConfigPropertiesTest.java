package com.myssteriion.blindtest.properties;

import com.myssteriion.blindtest.AbstractTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ConfigPropertiesTest extends AbstractTest {

    @Autowired
    private ConfigProperties props;



    @Test
    public void getterSetter() {

        Assert.assertEquals( new Integer(12), props.getPaginationElementsPerPageAvatars() );
        Assert.assertEquals( new Integer(24), props.getPaginationElementsPerPageProfiles() );
    }

}