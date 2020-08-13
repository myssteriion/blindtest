package com.myssteriion.blindtest;

import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@PowerMockIgnore({
        "javax.crypto.*",
        "javax.xml.*",
        "com.sun.org.apache.*"
})
public abstract class AbstractPowerMockTest extends AbstractTest {

}
