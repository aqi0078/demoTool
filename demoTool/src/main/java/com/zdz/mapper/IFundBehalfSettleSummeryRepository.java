package com.zdz.mapper;

import com.zdz.test.FundBehalfAccount;
import com.zdz.test.FundBehalfSettleSummery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IFundBehalfSettleSummeryRepository extends JpaRepository<FundBehalfSettleSummery, Long> {

    FundBehalfSettleSummery findByFundIdAndBehalfAt(Long fundId, Date behalfAt);

    @Query(value = "select * from fund_behalf_settle_summery " +
            "    where fund_id=?1 and behalf_at=?2 and settle_status=?3", nativeQuery = true)
    FundBehalfSettleSummery findByFundIdAndBehalfAtAndSettleStatus(Long fundId, Date behalfAt, int settleStatus);


    //List<FundBehalfSettleSummery> findAllByFundIdAAndBehalfAtLessThanAndBehalfAtGreaterThanEqualAndSettleStatusAndPayOrderNoNotNull(Long fundId, Date lessBehalfAt,Date greaterBehalfAt,int settleStatus);
    @Query(value = "select fbss.* from fund_behalf_settle_summery fbss " +
            "    join third_party_pay_order tppo on fbss.mer_order_no=tppo.mer_order_no " +
            "    where tppo.pay_status=4 and fbss.settle_status=0 and fbss.fund_id=?1 and fbss.behalf_at<?2 and fbss.behalf_at>=?3", nativeQuery = true)
    List<FundBehalfSettleSummery> findAllByHisData(Long fundId, Date lessBehalfAt, Date greaterBehalfAt);
    @Query(value = "select fbss.* from fund_behalf_settle_summery fbss " +
            "    join third_party_pay_order tppo on fbss.mer_order_no=tppo.mer_order_no " +
            "    where tppo.pay_order_no=?1 ", nativeQuery = true)
    FundBehalfSettleSummery findByPayOrderNo(String payOrderNo);

    @Query(value = "select fbss.* from fund_behalf_settle_summery fbss " +
            " join third_party_pay_order tppo on fbss.mer_order_no=tppo.mer_order_no  " +
            " where tppo.pay_status=4 and fbss.settle_status=0 and fbss.fund_id in(?1) and fbss.behalf_at=?2 order by fbss.fund_id ", nativeQuery = true)
    List<FundBehalfSettleSummery> findByFailData(List<Long> fundIdList, Date behalfAt);

    @Query(value = "select fbss.* from fund_behalf_settle_summery fbss " +
            " join third_party_pay_order tppo on fbss.mer_order_no=tppo.mer_order_no  " +
            " where fbss.settle_status=0 and fbss.fund_id  in(?1) and fbss.behalf_at=?2 and tppo.pay_status!=4  order by fbss.fund_id  ", nativeQuery = true)
    List<FundBehalfSettleSummery> findAllByRunData(List<Long> fundIdList, Date behalfAt);


    @Query(value = "select fbss.* from fund_behalf_settle_summery fbss " +
            "    join third_party_pay_order tppo on fbss.mer_order_no=tppo.mer_order_no " +
            "    where tppo.pay_status=4 and fbss.settle_status=0 and fbss.fund_id=?1 and fbss.behalf_at=?2", nativeQuery = true)
    FundBehalfSettleSummery findTodayFailData(Long fundId,Date behalfAt);
}
