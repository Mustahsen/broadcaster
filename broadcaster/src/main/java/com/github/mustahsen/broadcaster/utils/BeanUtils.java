package com.github.mustahsen.broadcaster.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * The type Bean utils.
 */
@Service
public class BeanUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    /**
     * Sets application context.
     *
     * @param applicationContext the application context
     * @throws BeansException the beans exception
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * Gets bean.
     *
     * @param <T>       the type parameter
     * @param beanClass the bean class
     * @return the bean
     */
    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
}
