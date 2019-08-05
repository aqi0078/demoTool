package com.zdz.mapper;

import com.zdz.test.FundBehalfSettleSummery;
import com.zdz.test.ThirdPartyPayOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface IThirdPartyPayOrderRepository extends JpaRepository<ThirdPartyPayOrder, Long> {


    /**
     * 查询商户支付订单
     * @param merOrderNo
     * @param code
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT h.* FROM `third_party_pay_order` h WHERE h.`mer_order_no` = ?1 AND h.`order_type` = ?2")
    ThirdPartyPayOrder findByMerOrderNo(String merOrderNo, String code);

    /**
     * 查询未支付订单
     * @param status 状态, {@link cn.qg.clotho.model.PayStatus#code}
     * @param limit
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT h.* FROM `third_party_pay_order` h WHERE h.`pay_status` = ?1 LIMIT ?2")
    List<ThirdPartyPayOrder> findUnPayOrder(int status, int limit);

    /**
     * 查询未支付订单; ID 为偶数
     * @param status 状态, {@link cn.qg.clotho.model.PayStatus#code}
     * @param limit
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT * FROM `third_party_pay_order` WHERE `pay_status` = ?1 and id=(id>>1)<<1 LIMIT ?2")
    List<ThirdPartyPayOrder> findUnPayOrderByEven(int status,int limit);

    /**
     * 查询未支付订单; ID 为奇数
     * @param status 状态, {@link cn.qg.clotho.model.PayStatus#code}
     * @param limit
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT * FROM `third_party_pay_order` WHERE `pay_status` = ?1 and id&1 LIMIT ?2")
    List<ThirdPartyPayOrder> findUnPayOrderByOdd(int status,int limit);

    /**
     * 查询支付订单
     * @param orderNo
     * @return
     */
    ThirdPartyPayOrder findByPayOrderNo(String orderNo);

    /**
     * 更新支付状态
     * @param orderNo
     * @param code
     */
    @Query(nativeQuery = true,value = "UPDATE third_party_pay_order SET `pay_status` = ?2 WHERE `pay_order_no` = ?1")
    @Modifying
    void updatePayStatus(String orderNo, int code);

    /**
     * 支付失败
     * @param payOrderNo
     * @param orderId
     */
    @Query(nativeQuery = true,value = "UPDATE third_party_pay_order SET `pay_status` = 2,pay_serial_no = ?2 WHERE `pay_order_no` = ?1")
    @Modifying
    void updateToFail(String payOrderNo, String orderId);

    /**
     * 支付成
     * @param payOrderNo
     * @param orderId
     * @param payAt
     */
    @Query(nativeQuery = true,value = "UPDATE third_party_pay_order SET `pay_status` = 3,pay_serial_no = ?2,pay_at = ?3 WHERE `pay_order_no` = ?1")
    @Modifying
    void updateToSuccess(String payOrderNo, String orderId, Date payAt);

    /**
     * 回滚状态,进行重试
     * @param payOrderNo
     */
    @Query(nativeQuery = true,value = "UPDATE third_party_pay_order SET `pay_status` = 0, retry_count = retry_count+1 WHERE `pay_order_no` = ?1")
    @Modifying
    void updateToRetry(String payOrderNo);

    /**
     * 查询中
     * @param payOrderNo
     */
    @Query(nativeQuery = true,value = "UPDATE third_party_pay_order SET `pay_status` = 9 WHERE `pay_order_no` = ?1")
    @Modifying
    void updateToPulling(String payOrderNo);

    /**
     * 回退到待查状态
     * @param value
     */
    @Transactional(value = "clothoTransactionManager")
    @Query(nativeQuery = true,value = "UPDATE third_party_pay_order SET `pay_status` = 1 WHERE `pay_order_no` = ?1 and `pay_status` = 9")
    @Modifying
    void backToWaitingFetch(String value);
}

