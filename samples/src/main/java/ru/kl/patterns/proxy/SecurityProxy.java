package ru.kl.patterns.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SecurityProxy implements InvocationHandler {

    private final Object object;

    public SecurityProxy(Object object) {
        this.object = object;
    }

    public static TwitterService newInstance(Object object) {
        return (TwitterService) Proxy.newProxyInstance(
                object.getClass().getClassLoader(),
                object.getClass().getInterfaces(),
                new SecurityProxy(object)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        try {
            if (method.getName().contains("post")) {
                throw new IllegalAccessException(method.getName() + " method is currently not allowed");
            }
            result = method.invoke(object, args);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (RuntimeException e) {
            throw new RuntimeException("unexpected invocation exception: " + e.getMessage());
        }

        return result;
    }
}
