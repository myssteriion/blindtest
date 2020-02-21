package com.myssteriion.blindtest;

import com.myssteriion.utils.BeanFactory;
import com.myssteriion.utils.rest.RestUtils;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ComponentScan( basePackages = {"com.myssteriion.blindtest", "com.myssteriion.utils"} )
public abstract class AbstractTest {
}
