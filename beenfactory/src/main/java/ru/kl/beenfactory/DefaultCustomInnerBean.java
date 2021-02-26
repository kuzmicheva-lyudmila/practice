package ru.kl.beenfactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultCustomInnerBean implements CustomInnerBean {

    public DefaultCustomInnerBean() {
        log.info("constructor: {}", this.getClass());
    }
}
