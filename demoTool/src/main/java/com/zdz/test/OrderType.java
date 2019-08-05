package com.zdz.test;

import lombok.Getter;

@Getter
public enum OrderType {

    XIAONIU_COMPENSATION("xn-compensation","XNC","小牛代偿"),
    XIAONIU_COMPENSATION2("xn-compensation-2","XNC","小牛第二次代偿"),
    XIAONIU_RETURNED("xn-returned","XNR","小牛退货"),
    ZHONGBANG_DEDUCTION("zb-deduction","ZBD","众邦代偿代发"),
    TONGYONG_DEDUCTION("ty-deduction","TYD","通用代偿代发");

    /**
     * @param code
     * @param prefix 最大3位
     * @param desc
     */
    OrderType(String code, String prefix, String desc) {
        this.code = code;
        this.desc = desc;
        this.prefix = prefix;
    }

    private String prefix;
    private String code;
    private String desc;
}
