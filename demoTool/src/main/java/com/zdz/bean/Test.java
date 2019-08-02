package com.zdz.bean;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {
//    private List names = new ArrayList();
//    public synchronized void add(String name){
//        names.add(name);
//    }
//    public synchronized void printAll(){
//        for (int i = 0; i < names.size(); i++){
//            System.out.print(names.get(i) +"");
//        }
//    }
//    public static void main(String[]args){
//        final Test sl = new Test();
//        for (int i = 0; i < 2; i++){
//            new Thread(){
//                public void run(){
//                    sl.add("A");
//                    sl.add("B");
//                    sl.add("C");
//                    sl.printAll();
//                }
//            } .start();
//        }
//    }
public static final String TMPL ="<html><head></head><body><h2>由%s日系统监测到%s的账户余额不足代偿,请留意.</h2><h3>当前余额：<font color=red>%s</font> 元</h3><h3>放款金额：%s 元</h3><p>TIPS:如果账户%s用尽,会影响代偿功能.</p></body></html>";

    public static void main(String[] args) {
        String body = String.format(TMPL, "2019-07-31", "710,790","当前余额","放款余额","账户号1111");
        System.out.println(body);


        LocalDate nowLocalDate = LocalDate.now();
        Date dateNow = Date.from(nowLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        String str=nowLocalDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        System.out.println(str);

    }
}
