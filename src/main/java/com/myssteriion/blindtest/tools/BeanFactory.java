package com.myssteriion.blindtest.tools;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * The Bean factory.
 */
@Component
public class BeanFactory {

    /**
     * The ApplicationContext.
     */
    private static ApplicationContext applicationContext;

    /**
     * Sets application context.
     *
     * @param applicationContext the application context
     */
    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        BeanFactory.applicationContext = applicationContext;
    }



    /**
     * Gets bean by class.
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return the bean
     */
    public static <T> T getBean(Class<T> clazz) {

        try {

            Tool.verifyValue("clazz", clazz);
            return applicationContext.getBean(clazz);
        }
        catch (BeansException e) {
            throw new IllegalArgumentException("La class '" + clazz.getSimpleName() + "' n'est pas un beanSpring.");
        }
    }

    /**
     * Gets bean by name and class.
     *
     * @param <T>   the type parameter
     * @param name  the name
     * @param clazz the clazz
     * @return the bean
     */
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
