package com.shenhua.sendroid.annotation.view;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenhua on 2017-07-21.
 * Email shenhuanet@126.com
 */
public class MethodIntercepter implements InvocationHandler {

    private Map<String, Method> map = new HashMap<>();
    private Object target;

    public MethodIntercepter(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (target != null) {
            String name = method.getName();
            Method m = map.get(name);
            if (m != null) {
                return m.invoke(target, args);
            }
        }
        return null;
    }

    public void add(String name, Method method) {
        map.put(name, method);
    }
}
