package com.zdz.service.impl;

import com.zdz.mapper.IFundBehalfSettleSummeryRepository;
import com.zdz.service.FundBehalfSettleSummeryService;
import com.zdz.test.FundBehalfSettleSummery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("fundBehalfSettleSummeryService")
public class FundBehalfSettleSummeryServiceImpl implements FundBehalfSettleSummeryService {
    @Autowired
    IFundBehalfSettleSummeryRepository fundBehalfSettleSummeryRepository;
    @Override
    public void save(FundBehalfSettleSummery summery) {
        fundBehalfSettleSummeryRepository.save(summery);
    }

    @Override
    public List<FundBehalfSettleSummery> findAllByHisData(Long fundingCorpId, Date dateNow, Date f) {
        return fundBehalfSettleSummeryRepository.findAllByHisData(fundingCorpId, dateNow, f);
    }

    @Override
    public FundBehalfSettleSummery findTodayFailData(Long fundingCorpId, Date dateNow) {
        return fundBehalfSettleSummeryRepository.findTodayFailData(fundingCorpId, dateNow);
    }

    @Override
    public FundBehalfSettleSummery findByFundIdAndBehalfAtAndSettleStatus(Long fundingCorpId, Date dateNow, int value) {
        return fundBehalfSettleSummeryRepository.findByFundIdAndBehalfAtAndSettleStatus(fundingCorpId, dateNow, value);
    }
}
