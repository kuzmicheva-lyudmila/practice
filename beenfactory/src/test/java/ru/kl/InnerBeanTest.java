package ru.kl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.kl.innerbean.OuterBean;

import static org.junit.jupiter.api.Assertions.assertThrows;

class InnerBeanTest {

    @Test
    void innerBeanTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("innerbean.xml");

        OuterBean outerBean = context.getBean("outerBean", OuterBean.class);
        outerBean.showInnerBean();

        assertThrows(NoSuchBeanDefinitionException.class, () -> context.getBean(OuterBean.InnerBean.class));
    }
}
