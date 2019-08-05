package com.zdz.mapper;

import com.zdz.bean.FundBehalfConfigure;
import com.zdz.test.FundBehalfAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IFundBehalfAccountRepository  extends JpaRepository<FundBehalfAccount, Long> {

    @Query(value = "select fba.* from fund_behalf_configure fbc join fund_behalf_account fba on fbc.fund_account_id=fba.fund_account_id " +
            "where fbc.fund_id=?1", nativeQuery = true)
    FundBehalfAccount findByFundId(Long fundId);

    FundBehalfAccount findByFundAccountId(Long fundAccountId);

}
