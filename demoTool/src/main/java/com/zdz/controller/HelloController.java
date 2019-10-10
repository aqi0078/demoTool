package com.zdz.controller;

import com.zdz.bean.*;
import com.zdz.mapper.InfoDao;
import com.zdz.service.AService;
import com.zdz.service.InfoService;
import com.zdz.service.PayOrderService;
import com.zdz.service.impl.PService;
import com.zdz.test.FundCompensatoryJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
//@EnableAutoConfiguration
public class HelloController {

    @Autowired
    PayOrderService payOrderServiceImpl;

    @Autowired
    @Qualifier("infoService")
    InfoService infoService;
    @Autowired
    FundCompensatoryJobService fundCompensatoryJobService;
    @Autowired
    Map<String,InfoService> map;

//    @Autowired
//    AService aServiceImpl;
    @Autowired
    Map<String, PService> pService;
    @RequestMapping("/serMap")
    public String ser(){
        System.out.println("=========");
        LocalDate d = LocalDate.now();
        d.plusDays(-1);



        try {
            System.out.println(payOrderServiceImpl.createSimplePayOrder("",null,null,null,null));
//            System.out.println(payOrderService.createSimplePayOrder("",null,null,null,null));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("=========");
        String str="00---";
        System.out.println(pService.get("aService").getP());
        System.out.println(pService.get("bService").getP());
        System.out.println(pService.get("cService").getP());
//        System.out.println(aServiceImpl.getA());
        return str;
    }
//    @ModelAttribute("alarmReq")
//    public BolgRequst getMessageAlarmReq() {
//        System.out.println("=================");
//
//        return new BolgRequst();
//    }
//
//    @RequestMapping("/blog")
//    public BlogResponse blog(@ModelAttribute("alarmReq")BolgRequst bolgRequst){
//        System.out.println("========="+bolgRequst);
//        BlogResponse blogResponse=new BlogResponse();
//        blogResponse.setErrorCode("11");
//        blogResponse.setErrorMsg("22");
//        blogResponse.setMerchantno("33");
//        return blogResponse;
//    }
    @RequestMapping("/blog2")
    public BlogResponse blog2(BolgRequst bolgRequst){
        System.out.println("========="+bolgRequst);
        BlogResponse blogResponse=new BlogResponse();
        blogResponse.setErrorCode("11");
        blogResponse.setErrorMsg("22");
        blogResponse.setMerchantno("33");
        return blogResponse;
    }
    @RequestMapping(value ="/blog1")//,method = RequestMethod.POST
    public BlogResponse blog1(@RequestBody BolgRequst bolgRequst){
        System.out.println("========="+bolgRequst);
        BlogResponse blogResponse=new BlogResponse();
        blogResponse.setErrorCode("11");
        blogResponse.setErrorMsg("22");
        blogResponse.setMerchantno("33");
        return blogResponse;
    }

    @RequestMapping("/hello")
    public String hello(){
//        fundCompensatoryJobService.execute();
       // infoService.printData();
        for (String str:map.keySet()) {
            map.get(str).printData();
            System.out.println("========="+map.get(str).getloanMerchantId());
        }
//        try {
//            Class<?> c = Class.forName("com.zdz.service.impl."+"Info2"+"ServiceImpl");
//            InfoService in=(InfoService)c.newInstance();
//            System.out.println("--------");
//            in.printData();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }

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
