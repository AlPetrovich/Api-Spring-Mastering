package com.codmain.order.controllers;

import com.codmain.order.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@Slf4j
@RestController
public class HelloWorldController {



    @GetMapping("/hello")
    public String hello(){
        return "Hello world";
    }


}
