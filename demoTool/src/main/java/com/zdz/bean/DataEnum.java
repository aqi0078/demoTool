package com.zdz.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum DataEnum {
    data;

    public static Map<Integer, List<Long>> getData(){

        Map<Integer, List<Long>> map=new HashMap<>();
        List<Long> list30=new ArrayList<>();
        list30.add(710L);
        list30.add(790L);
        map.put(60,list30);
        System.out.println("map,,crate");
        return map;
    }


}
