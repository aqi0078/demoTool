package com.zdz.service;

import com.zdz.test.OrderType;
import com.zdz.test.ThirdPartyPayOrder;

import java.math.BigDecimal;

public interface PayOrderService {

    String createSimplePayOrder(String merOrderNo, OrderType type, BigDecimal orderAmount, String describe, String extData) throws Exception;

    ThirdPartyPayOrder findByMerOrderNo(String merchantOrderNo, String code);
}
