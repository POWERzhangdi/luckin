package com.luckin.coffee.controller;

import com.luckin.coffee.route.annotate.GetMapping;
import com.luckin.coffee.route.annotate.PostMapping;

public class TestController {

    @GetMapping(URL = "/test/getMapping")
    public String testGet() {
        return "hello/getMapping";
    }


    @PostMapping(URL = "/test/postMapping")
    public String testPost() {
        return "hello/postMapping";
    }
}
