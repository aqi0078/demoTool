package com.zdz.test;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 第三方支付订单表; 用于通用简易支付转发
 * @author Jie.Feng
 */
@Data
@Entity
@Table(name = "third_party_pay_order")
public class ThirdPartyPayOrder {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * 支付订单号,本系统生成
     */
    @Column(name = "pay_order_no")
    private String payOrderNo;
    /**
     * 支付流水,支付平台返回
     */
    @Column(name = "pay_serial_no")
    private String paySerialNo;
    /**
     * 发起的支付订单号
     */
    @Column(name = "mer_order_no")
    private String merOrderNo;
    /**
     * 支付平台,枚举
     * @see
     */
    @Column(name = "pay_platform")
    private String payPlatform;
    /**
     * 支付状态
     * @see
     */
    @Column(name = "pay_status")
    private int payStatus;

    /**
     * 支付中心的付款商编; 扣款账户
     */
    @Column(name = "pay_merchant_account")
    private String payMerchantAccount;

    /**
     * 目标帐号
     */
    @Column(name = "target_account")
    private String targetAccount;
    /**
     * 订单金额
     */
    @Column(name = "order_amount")
    private BigDecimal orderAmount;
    /**
     * 订单类型
     * @see OrderType#code 订单类型下的code 字段
     */
    @Column(name = "order_type")
    private String orderType;
    /**
     * 订单简易描述
     */
    @Column(name = "order_describe")
    private String orderDescribe;
    /**
     * 订单其他信息
     */
    @Column(name = "ext_data")
    private String extData;
    /**
     * 支付时间
     */
    @Column(name = "pay_at")
    private Date payAt;


    /**
     * 重试次数
     */
    @Column(name = "retry_count")
    private int retryCount = 0;
    /**
     *下单时间
     */
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;


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
