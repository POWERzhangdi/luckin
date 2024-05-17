package com.luckin.coffee.config;

import javax.json.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ApplicationJsonLoader {

    public final static class ApplicationJson {

        /**
         * 端口
         */
        private final Integer port;

        /**
         * 应用名称
         */
        private final String name;

        public Integer getPort() {
            return port;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "ApplicationJson{" +
                    "port=" + port +
                    ", name='" + name + '\'' +
                    '}';
        }

        public ApplicationJson(Integer port, String name) {
            this.port = port;
            this.name = name;
        }
    }

    public static ApplicationJson getApplication() {
        Map<String, Object> objectMap = systemLoaderResource();
        System.out.println(objectMap.get("server"));
        Map<String, Object> serverMap = (Map<String, Object>) objectMap.get("server");

        System.out.println(serverMap.get("name"));

        return new ApplicationJson((Integer) serverMap.get("port"), (String) serverMap.get("name"));
    }


    /**
     * 加载资源
     */
    private static Map<String, Object> systemLoaderResource() {

        //获取当前类的 ClassLoader
        ClassLoader classLoader = ApplicationJsonLoader.class.getClassLoader();

        Map<String, Object> resultMap = null;

        try (InputStream inputStream = classLoader.getResourceAsStream("application.json")) {

            if (null == inputStream) {
                throw new NullPointerException("application.json资源加载失败");
            }

            JsonReader jsonReader = Json.createReader(inputStream);
            JsonObject jsonObject = jsonReader.readObject();
            resultMap = jsonObjectToMap(jsonObject);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultMap;
    }


    /**
     * json解析
     *
     * @param jsonObject
     * @return
     */
    private static Map<String, Object> jsonObjectToMap(JsonObject jsonObject) {
        Map<String, Object> map = new HashMap<>();
        Set<String> keys = jsonObject.keySet();

        for (String key : keys) {
            Object value = jsonObject.get(key);

            if (value instanceof JsonObject) {
                map.put(key, jsonObjectToMap((JsonObject) value));
            } else if (value instanceof JsonString) {
                map.put(key, ((JsonString) value).getString());
            } else if (value instanceof JsonNumber) {
                //将JsonNumber 转换为相应的数值类型
                JsonNumber jsonNumber = (JsonNumber) value;
                if (jsonNumber.isIntegral()) {
                    //如果是整数
                    map.put(key, jsonNumber.intValue());
                } else {
                    //如果是浮点数
                    map.put(key, jsonNumber.doubleValue());
                }
            } else {
                map.put(key, value);
            }
        }

        return map;
    }

}
