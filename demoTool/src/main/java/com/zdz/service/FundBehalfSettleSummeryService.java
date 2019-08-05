package com.zdz.service;

import com.zdz.test.FundBehalfSettleSummery;

import java.util.Date;
import java.util.List;

public interface FundBehalfSettleSummeryService {
    void save(FundBehalfSettleSummery summery);

    List<FundBehalfSettleSummery> findAllByHisData(Long fundingCorpId, Date dateNow, Date f);

    FundBehalfSettleSummery findTodayFailData(Long fundingCorpId, Date dateNow);

    FundBehalfSettleSummery findByFundIdAndBehalfAtAndSettleStatus(Long fundingCorpId, Date dateNow, int value);
}
