package ru.kl.beenfactory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

@Slf4j
public class DefaultCustomBean implements CustomBean, InitializingBean, DisposableBean, BeanNameAware {

    private final CustomInnerBean customInnerBean;

    public DefaultCustomBean(CustomInnerBean customInnerBean) {
        this.customInnerBean = customInnerBean;
        log.info("constructor: {}", this.getClass());
    }

    @Override
    public void destroy() {
        log.info("destroy: {}", this);
    }

    @Override
    public void afterPropertiesSet() {
        log.info("afterPropertiesSet: {}", this);
    }

    public void init() {
        log.info("init: {}", this);
    }

    public void cleanup() {
        log.info("cleanup: {}", this);
    }

    @Override
    public void setBeanName(String name) {
        log.info("set bean name: {}", this);
    }

    @Override
    public void process() {
        log.info("successfully created");
    }
}
