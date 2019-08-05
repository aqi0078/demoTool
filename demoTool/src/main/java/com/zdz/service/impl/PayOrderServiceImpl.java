package com.zdz.service.impl;

import com.zdz.mapper.IThirdPartyPayOrderRepository;
import com.zdz.service.PayOrderService;
import com.zdz.test.OrderType;
import com.zdz.test.ThirdPartyPayOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("payOrderService")
public class PayOrderServiceImpl implements PayOrderService {
    @Autowired
    IThirdPartyPayOrderRepository thirdPartyPayOrderRepository;
    @Override
    public String createSimplePayOrder(String merOrderNo, OrderType type, BigDecimal orderAmount, String describe, String extData) throws Exception {
        return "11";
    }

    @Override
    public ThirdPartyPayOrder findByMerOrderNo(String merchantOrderNo, String code) {
        return thirdPartyPayOrderRepository.findByMerOrderNo(merchantOrderNo, code);
    }
}
