package com.myssteriion.blindtest.tools;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.properties.RoundContentProperties;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BeanFactoryTest extends AbstractTest {

    @Autowired
    private RoundContentProperties roundContentProperties;



    @Test
    public void bean() {

        try {
            BeanFactory.getBean(null);
            Assert.fail("Doit lever une IllegalArgumentException car le paramètre est null.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'clazz' est obligatoire."), e);
        }

        try {
            BeanFactory.getBean(BeanFactoryTest.class);
            Assert.fail("Doit lever une IllegalArgumentException car le paramètre est null.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("La class '" + BeanFactoryTest.class.getSimpleName() + "' n'est pas un beanSpring."), e);
        }

        Assert.assertSame(roundContentProperties, BeanFactory.getBean(RoundContentProperties.class));
    }

    @Test
    public void bean2() {

        String name = "name";

        try {
            BeanFactory.getBean(null, BeanFactoryTest.class);
            Assert.fail("Doit lever une IllegalArgumentException car le paramètre est null.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'name' est obligatoire."), e);
        }

        try {
            BeanFactory.getBean("", BeanFactoryTest.class);
            Assert.fail("Doit lever une IllegalArgumentException car le paramètre est null.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'name' est obligatoire."), e);
        }

        try {
            BeanFactory.getBean(name, null);
            Assert.fail("Doit lever une IllegalArgumentException car le paramètre est null.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("Le champ 'clazz' est obligatoire."), e);
        }

        try {
            BeanFactory.getBean(name, BeanFactoryTest.class);
            Assert.fail("Doit lever une IllegalArgumentException car le paramètre est null.");
        }
        catch (IllegalArgumentException e) {
            verifyException(new IllegalArgumentException("La class '" + BeanFactoryTest.class.getSimpleName() + "' avec le nom 'name' n'est pas un beanSpring."), e);
        }

        Assert.assertSame(roundContentProperties, BeanFactory.getBean("roundContentProperties", RoundContentProperties.class));
    }

}