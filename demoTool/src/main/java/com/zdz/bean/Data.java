package com.zdz.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Data {

    public Data(){}
    public static class Tmp{

        static Map<String,Map<Integer,List<Long>>> data=new HashMap<>();
        static Long TIME=100000L;
        static{

            List<Map<String,Object>> list=new ArrayList<>();
            Map<String,Object> map710=new HashMap<>();
            map710.put("time","13:00|15:00");
            map710.put("account",60);
            map710.put("fundingPrty",710L);
            list.add(map710);

            Map<String,Object> map790=new HashMap<>();
            map790.put("time","13:00|15:00");
            map790.put("account",60);
            map790.put("fundingPrty",790L);
            list.add(map790);

            Map<String,Object> map990=new HashMap<>();
            map990.put("time","13:00|16:06");
            map990.put("account",60);
            map990.put("fundingPrty",790L);
            list.add(map990);
            thData(list);
            System.out.println("7890------");
        }

        /**
         * 转换为时间：数据格式
         * @param list
         */
        public static void thData(List<Map<String,Object>> list){
            Map<String, List<Map<String,Object>>> timeMap=new HashMap<>();
            for (int i = 0; i < list.size(); i++) {
                Map<String,Object> map=list.get(i);
                String[] str=map.get("time").toString().split("\\|");
                for (int j = 0; j < str.length; j++) {
                    if (timeMap.get(str[j])==null) {
                        List<Map<String,Object>> listTmp=new ArrayList<>();
                        listTmp.add(map);
                        timeMap.put(str[j],listTmp);
                    }else{
                        timeMap.get(str[j]).add(map);
                    }
                }
            }
            getTimeData(timeMap);
        }


        /**
         * 转换为时间：（账户：资金方格式）
         * @param map
         */
        public static void getTimeData(Map<String, List<Map<String, Object>>> map){
            for (String str:map.keySet()){
                List<Map<String,Object>> list=map.get(str);
                Map<Integer,List<Long>> mapTmp=new HashMap<>();
                for (int i = 0; i < list.size(); i++) {
                    if (mapTmp.get(list.get(i).get("account"))==null){
                        List<Long> listTmp=new ArrayList();
                        listTmp.add(Long.valueOf(list.get(i).get("fundingPrty").toString()));
                        mapTmp.put(Integer.valueOf(list.get(i).get("account").toString()),listTmp);
                    }else{
                        mapTmp.get(list.get(i).get("account")).add(Long.valueOf(list.get(i).get("fundingPrty").toString()));
                    }
                }
                data.put(str,mapTmp);
            }

        }

        /**
         * 返回1秒内需要执行的账户：资金方
         * @return
         */
        public static Map<Integer,List<Long>> getData(){
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
            for (String str:data.keySet()){
                try {
                    if(simpleDateFormat.parse(simpleDateFormat.format(new Date())).getTime()-simpleDateFormat.parse(str).getTime()<TIME){
                        return data.get(str);
                    }
                } catch (ParseException e) {
                    System.out.println("日期计算错误");
                }
            }
            return null;
        }



    }



    public static void main(String[] args) {
       // Tmp tmp=new Tmp();

        System.out.println(Tmp.getData());

        System.out.println(Tmp.data);
    }
}
