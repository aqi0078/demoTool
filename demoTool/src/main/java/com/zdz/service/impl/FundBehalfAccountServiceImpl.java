package com.zdz.service.impl;

import com.zdz.mapper.IFundBehalfAccountRepository;
import com.zdz.service.FundBehalfAccountService;
import com.zdz.test.FundBehalfAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("fundBehalfAccountService")
public class FundBehalfAccountServiceImpl implements FundBehalfAccountService {
    @Autowired
    IFundBehalfAccountRepository fundBehalfAccountRepository;
    @Override
    public FundBehalfAccount findByFundAccountId(Long accountId) {
        return fundBehalfAccountRepository.findByFundAccountId(accountId);
    }
}
