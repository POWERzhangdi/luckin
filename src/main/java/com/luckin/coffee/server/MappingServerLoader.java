package com.luckin.coffee.server;


import com.luckin.coffee.route.implement.MappingAnnotationProcessor;

import java.io.IOException;
import java.util.List;

public class MappingServerLoader {

    public static void loadAnnotation(List<String> scanPackages) throws IOException, ClassNotFoundException {

        ClassScanner classLoad = ClassScanner.getInstance();

        for (String scanPackage : scanPackages) {
            List<Class<?>> classes = classLoad.scanClasses(scanPackage);
            MappingAnnotationProcessor processor = new MappingAnnotationProcessor();
            for (Class<?> clazz : classes) {
                processor.processAnnotations(clazz);
            }
        }

    }

}
