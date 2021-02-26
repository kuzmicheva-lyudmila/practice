package ru.kl.beenfactory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

@Slf4j
public class CustomBeanPostProcessor implements BeanPostProcessor {

    private static final String BEAN_NAME = "customBean";

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals(BEAN_NAME)) {
            log.info("postProcessBeforeInitialization: {} {}", beanName, bean);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals(BEAN_NAME)) {
            log.info("postProcessAfterInitialization: {} {}", beanName, bean);
        }

        return bean;
    }
}
