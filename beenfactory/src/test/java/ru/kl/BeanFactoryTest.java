package ru.kl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import ru.kl.beenfactory.CustomBean;
import ru.kl.beenfactory.CustomBeanFactoryPostProcessor;
import ru.kl.beenfactory.CustomBeanPostProcessor;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BeanFactoryTest {

    @Test
    void beanFactoryTest() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        factory.addBeanPostProcessor(new CustomBeanPostProcessor());

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("beanfactory.xml"));

        CustomBeanFactoryPostProcessor beanFactoryPostProcessor = new CustomBeanFactoryPostProcessor();
        beanFactoryPostProcessor.postProcessBeanFactory(factory);

        CustomBean customBean = factory.getBean("customBean", CustomBean.class);
        assertNotNull(customBean);

        customBean.process();

        factory.destroySingletons();
    }
}
