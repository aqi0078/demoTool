//package com.zdz.bean;
//
//import java.io.*;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//public class Test {
////    private List names = new ArrayList();
////    public synchronized void add(String name){
////        names.add(name);
////    }
////    public synchronized void printAll(){
////        for (int i = 0; i < names.size(); i++){
////            System.out.print(names.get(i) +"");
////        }
////    }
////    public static void main(String[]args){
////        final Test sl = new Test();
////        for (int i = 0; i < 2; i++){
////            new Thread(){
////                public void run(){
////                    sl.add("A");
////                    sl.add("B");
////                    sl.add("C");
////                    sl.printAll()
////                }
////            } .start();
////        }
////    }
//public static final String TMPL ="<html><head></head><body><h2>由%s日系统监测到%s的账户余额不足代偿,请留意.</h2><h3>当前余额：<font color=red>%s</font> 元</h3><h3>放款金额：%s 元</h3><p>TIPS:如果账户%s用尽,会影响代偿功能.</p></body></html>";
//
//    public static void main(String[] args) {
//
//            Map<String,List<FundBehalfConfigure>> dataMap=new HashMap<>();
//            List<FundBehalfConfigure> list =fundBehalfConfigureService.findAllByFundStatus(1);
////            log.info(LOG_PREFIX + "：list：{}",list);
//            Map<Long,String> map=new HashMap<>();
//            for (int i = 0; i < list.size(); i++) {
//                FundBehalfConfigure fc=list.get(i);
//                String[] behalfAt=fc.getBehalfAt().split("\\|");
//                for (int j = 0; j < behalfAt.length; j++) {
//                    if (map.get(fc.getFundId())==null){
//                        map.put(fc.getFundId(),behalfAt[j]);
//                    }else{
//                        List<FundBehalfConfigure> tmp=dataMap.get(map.get(fc.getFundId()));
//                        for (int k = 0; k < tmp.size(); k++) {
//                            if (fc.getFundId()==tmp.get(k).getFundId()){
//                                tmp.remove(tmp.get(k));
//                            }
//                        }
//                    }
//                    if (dataMap.get(behalfAt[j])==null){
//                        List<FundBehalfConfigure> tmp=new ArrayList<>();
//                        tmp.add(fc);
//                        dataMap.put(behalfAt[j],tmp);
//                    }else {
//                        dataMap.get(behalfAt[j]).add(fc);
//                    }
//                }
//            }
//            for (String str: dataMap.keySet()) {
////                log.info(LOG_PREFIX + "：dataMap s ：{}", str+"--0--"+dataMap.get(str).get(0).getFundId());
//            }
//        }
////        String body = String.format(TMPL, "2019-07-31", "710,790","当前余额","放款余额","账户号1111");
////        System.out.println(body);
////
////
////        LocalDate nowLocalDate = LocalDate.now();
////        Date dateNow = Date.from(nowLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
////        String str=nowLocalDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
////        System.out.println(str);
//
////        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
////        try {
////            System.out.println(simpleDateFormat.parse("23:59").compareTo(simpleDateFormat.parse("23:59"))); ;
////            System.out.println(simpleDateFormat.parse("23:59").compareTo(simpleDateFormat.parse("23:58"))); ;
////            System.out.println(simpleDateFormat.parse("23:58").compareTo(simpleDateFormat.parse("23:59"))); ;
////            System.out.println(simpleDateFormat.parse("23:59").compareTo(simpleDateFormat.parse("23:59")));
////        } catch (ParseException e) {
//
//
//
//
//    }
//}
