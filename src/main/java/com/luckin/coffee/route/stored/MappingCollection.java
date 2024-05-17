package com.luckin.coffee.route.stored;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MappingCollection {

    private final Map<String, Method> URIS = new HashMap<>(500);


    // 私有化构造方法，防止直接实例化
    private MappingCollection() {
    }

    // 静态内部类，用于持有单例实例
    private static class SingletonHelper {
        private static final MappingCollection INSTANCE = new MappingCollection();
    }

    // 提供全局访问点来获取单例实例
    public static MappingCollection getInstance() {
        return MappingCollection.SingletonHelper.INSTANCE;
    }


    public void add(String uri, Method method) {

        if (URIS.containsKey(uri)) {
            //TODO 异常需要合理的定义
            throw new NullPointerException("相同的URL不能存在");
        }

        URIS.put(uri, method);

    }

    public Method get(String uri) {

        if (!URIS.containsKey(uri)) {
            throw new NullPointerException("路径不存在");
        }

        return URIS.get(uri);

    }

    @Override
    public String toString() {
        return "MappingCollection{" +
                "URI=" + URIS +
                '}';
    }
}
