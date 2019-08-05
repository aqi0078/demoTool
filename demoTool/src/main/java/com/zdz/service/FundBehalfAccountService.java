package com.zdz.service;

import com.zdz.test.FundBehalfAccount;

public interface FundBehalfAccountService {


    FundBehalfAccount findByFundAccountId(Long accountId);
}
