package com.luckin.coffee;


import com.luckin.coffee.config.ApplicationJsonLoader;
import com.luckin.coffee.server.BootServer;
import com.luckin.coffee.server.MappingServerLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LuckinApplication {

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {

        //配置文件加载
        ApplicationJsonLoader.ApplicationJson serverResource = ApplicationJsonLoader.getApplication();
        //URL加载
        List<String> scanPackages = new ArrayList<>();
        scanPackages.add("com.luckin.coffee.controller");
        MappingServerLoader.loadAnnotation(scanPackages);
        new BootServer(serverResource.getPort()).start();

    }


}
