package com.zdz.controller;

import com.zdz.bean.Data;
import com.zdz.bean.DataEnum;
import com.zdz.bean.Info;
import com.zdz.mapper.InfoDao;
import com.zdz.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
//@EnableAutoConfiguration
public class HelloController {

//    @Autowired
//    InfoService infoService;

    @Autowired
    Map<String,InfoService> map;
    @RequestMapping("/hello")
    public String hello(){
       // infoService.printData();
        for (String str:map.keySet()) {
            map.get(str).printData();

        }
        try {
            Class<?> c = Class.forName("com.zdz.service.impl."+"Info1"+"ServiceImpl");
            InfoService in=(InfoService)c.newInstance();
            System.out.println("--------");
            in.printData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        // infoService.printData();
        return "hello----";
    }

    @RequestMapping("/hello1")
    public String hello1(){

//        Map<Integer, List<Long>> map1= new Data().getData();
//        Map<Integer, List<Long>> map2=new Data().getData();
//        System.out.println(map1==map2);

//        Map<String, List<Map<String,Object>>> map=new Data().getTimeData();
//        System.out.println(map);

        return "hello----";
    }
    @RequestMapping("/helloTest")
    public String helloTest(@RequestBody String name){
        System.out.println("==="+name);
        System.out.println();
        return "hello----"+name;
    }



}
