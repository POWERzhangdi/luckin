package com.luckin.coffee.route.implement;


import com.luckin.coffee.route.annotate.GetMapping;
import com.luckin.coffee.route.annotate.PostMapping;
import com.luckin.coffee.route.stored.MappingCollection;

import java.lang.reflect.Method;

public class MappingAnnotationProcessor {

    public void processAnnotations(Class<?> clazz) {

        MappingCollection collection = MappingCollection.getInstance();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostMapping.class)) {
                PostMapping postMapping = method.getAnnotation(PostMapping.class);
                String uri = postMapping.URL();
                collection.add(uri, method);
            }
            if (method.isAnnotationPresent(GetMapping.class)) {
                GetMapping getMapping = method.getAnnotation(GetMapping.class);
                String uri = getMapping.URL();
                collection.add(uri, method);
            }
        }

        System.out.println(collection);
    }

}
