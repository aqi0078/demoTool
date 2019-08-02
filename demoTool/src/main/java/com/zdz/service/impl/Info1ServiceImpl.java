package com.zdz.service.impl;

import com.zdz.service.InfoService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service("infoService")
public class Info1ServiceImpl  implements InfoService {

    public static final Integer loanMerchantId=60;
    @Override
    public void printData() {
        System.out.println("3456789");


        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");

        String str=null;
        str=simpleDateFormat.format(new Date());

        System.out.println("===="+str);
    }

    @Override
    public Integer getloanMerchantId() {
        return loanMerchantId;
    }
}
