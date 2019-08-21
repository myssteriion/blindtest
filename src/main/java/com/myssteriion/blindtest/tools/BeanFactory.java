package com.myssteriion.blindtest.tools;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BeanFactory {

    private static ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        BeanFactory.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {

        try {

            Tool.verifyValue("clazz", clazz);
            return applicationContext.getBean(clazz);
        }
        catch (BeansException e) {
            throw new IllegalArgumentException("La class '" + clazz.getSimpleName() + "' n'est pas un beanSpring.");
        }
    }

    public static <T> T getBean(String name, Class<T> clazz) {

        try {

            Tool.verifyValue("name", name);
            Tool.verifyValue("clazz", clazz);
            return applicationContext.getBean(name, clazz);
        }
        catch (BeansException e) {
            throw new IllegalArgumentException("La class '" + clazz.getSimpleName() + "' avec le nom '" + name + "' n'est pas un beanSpring.");
        }
    }

}
