package com.zdz.service;

import java.math.BigDecimal;

public interface PayService {
    BigDecimal queryMerchantBalance(String cardNo);
}
