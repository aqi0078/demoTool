package com.zdz.mapper;

import com.zdz.test.FundBehalfConfigure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFundBehalfConfigureRepository extends JpaRepository<FundBehalfConfigure, Long> {


    List<FundBehalfConfigure> findAllByFundStatusEqualsAndBehalfAtLessThanEqualAndBehalfAtGreaterThan(int status, String behalfAt, String oldTime);

    FundBehalfConfigure findAllByFundId(Long fundId);

    @Query(value = "select * from fund_behalf_configure where fund_status=1 and fail_at<=?1 and fail_at>?2", nativeQuery = true)
    List<FundBehalfConfigure>  findAllByFialData(String behalfAt,String oldTime);

    List<FundBehalfConfigure> findAllByFundStatus(int status);
}
