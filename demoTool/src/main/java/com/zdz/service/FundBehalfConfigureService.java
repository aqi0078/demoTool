package com.zdz.service;

import com.zdz.test.FundBehalfConfigure;

import java.util.List;

public interface FundBehalfConfigureService {
    List<FundBehalfConfigure> findAllByFundStatus(int i);
}
