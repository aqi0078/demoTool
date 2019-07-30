package com.zdz.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){
        return "hello----";
    }

    @RequestMapping("/helloTest")
    public String helloTest(@RequestBody String name){
        System.out.println("==="+name);
        System.out.println();
        return "hello----"+name;
    }



}
