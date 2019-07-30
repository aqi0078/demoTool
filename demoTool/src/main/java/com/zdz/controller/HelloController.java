package com.zdz.controller;

import com.zdz.bean.Info;
import com.zdz.mapper.InfoDao;
import com.zdz.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
//@EnableAutoConfiguration
public class HelloController {

    @Autowired
    InfoService infoService;
    @RequestMapping("/hello")
    public String hello(){
        infoService.printData();
       // infoService.printData();
        return "hello----";
    }

    @RequestMapping("/helloTest")
    public String helloTest(@RequestBody String name){
        System.out.println("==="+name);
        System.out.println();
        return "hello----"+name;
    }



}
