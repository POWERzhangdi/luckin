package com.luckin.coffee.route.annotate;


import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PostMapping {

    String URL();

}
