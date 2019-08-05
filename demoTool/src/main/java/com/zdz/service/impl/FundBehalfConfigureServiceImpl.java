package com.zdz.service.impl;

import com.zdz.mapper.IFundBehalfConfigureRepository;
import com.zdz.service.FundBehalfConfigureService;
import com.zdz.test.FundBehalfConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("fundBehalfConfigureService")
public class FundBehalfConfigureServiceImpl implements FundBehalfConfigureService {
    @Autowired
    IFundBehalfConfigureRepository fundBehalfConfigureRepository;
    @Override
    public List<FundBehalfConfigure> findAllByFundStatus(int i) {
        return fundBehalfConfigureRepository.findAllByFundStatus(i);
    }
}
