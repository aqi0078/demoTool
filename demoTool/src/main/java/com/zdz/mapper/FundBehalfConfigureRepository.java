package com.zdz.mapper;

import com.zdz.bean.FundBehalfConfigure;
import com.zdz.bean.Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FundBehalfConfigureRepository  extends  JpaRepository<FundBehalfConfigure, Long> {
    List<FundBehalfConfigure> findAllByFundStatusEqualsAndBehalfAtLessThanEqualAndBehalfAtGreaterThan(int status,String behalfAt,String OldTime);


    @Query(value = "select * from fund_behalf_configure where  fund_id in(?1) and fund_account=?2 ", nativeQuery = true)
    List<FundBehalfConfigure> findByFailData(List<String> fundIdList ,String fundAccount);

    @Query(value = "select fund_id from fund_behalf_configure where  fund_id in(?1) and fund_account=?2 ", nativeQuery = true)
    List<String> findByFailDataId(List<String> fundIdList ,String fundAccount);
}
