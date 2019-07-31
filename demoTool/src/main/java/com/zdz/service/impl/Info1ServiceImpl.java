package com.zdz.service.impl;

import com.zdz.service.InfoService;
import org.springframework.stereotype.Service;

@Service("infoService")
public class Info1ServiceImpl  implements InfoService {

    @Override
    public void printData() {
        System.out.println("3456789");
    }
}
