package com.zdz.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
/**
 * 代偿结算
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "fund_behalf_settle_summery")
public class FundBehalfSettleSummery {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fund_id")
    private Long fundId;

    @Column(name = "required_sum_amount")
    private BigDecimal requiredSumAmount;//应还总额

    @Column(name = "required_sum_principal")
    private BigDecimal requiredSumPrincipal;//应还本金

    @Column(name = "required_sum_interest")
    private BigDecimal requiredSumInterest;//应还利息

    @Column(name = "required_sum_overdue")
    private BigDecimal requiredSumOverdue;//应还罚息

    @Column(name = "required_sum_service_fee")
    private BigDecimal requiredSumServiceFee;//应还服务费

    @Column(name = "behalf_sum_amount")
    private BigDecimal behalfSumAmount;//代偿总额

//    @JSONField(name = "principal")
    @Column(name = "behalf_sum_principal")
    private BigDecimal behalfSumPrincipal;//代偿本金

//    @JSONField(name = "interest")
    @Column(name = "behalf_sum_interest")
    private BigDecimal behalfSumInterest;//代偿利息

//    @JSONField(name = "overdueInterest")
    @Column(name = "behalf_sum_overdue")
    private BigDecimal behalfSumOverdue;//代偿罚息

 //   @JSONField(name = "serviceFee")
    @Column(name = "behalf_sum_service_fee")
    private BigDecimal behalfSumServiceFee;//代偿服务费

    @Column(name = "enable")
    private Boolean enable = Boolean.TRUE;

    @Column(name = "behalf_at")
    private Date behalfAt;//代偿日期

    @Column(name = "settle_at")
    private Date settleAt;//结算日期

    @Column(name = "settle_status")
    private BehalftSettleStatus settleStatus;//结算状态

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    /**
     * 结算流水号,
     */
    @Column(name = "mer_order_no")
    private String merOrderNo;


    @PrePersist
    public void prePersist() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        createdAt = timestamp;
        updatedAt = timestamp;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }

}
