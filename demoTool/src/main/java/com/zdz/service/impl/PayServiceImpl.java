package com.zdz.service.impl;

import com.zdz.service.PayService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("payService")
public class PayServiceImpl implements PayService {
    @Override
    public BigDecimal queryMerchantBalance(String cardNo) {
        return null;
    }
}
