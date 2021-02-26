package ru.kl.innerbean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OuterBean {

    private final InnerBean innerBean;

    public OuterBean(InnerBean innerBean) {
        log.info("This is outerBean");
        this.innerBean = innerBean;
    }

    public void showInnerBean() {
        log.info("InnerBean: {}", innerBean);
    }

    public static class InnerBean {

        public InnerBean() {
            log.info("This is innerBean");
        }
    }
}
