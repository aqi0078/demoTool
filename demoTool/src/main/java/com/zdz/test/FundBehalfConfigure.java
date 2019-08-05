package com.zdz.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 代偿配置
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "fund_behalf_configure")
public class FundBehalfConfigure {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "fund_id")
    private Long fundId;

    @Column(name = "fund_name")
    private String fundName;

    @Column(name = "fund_account_id")
    private Long fundAccountId;

    @Column(name = "behalf_at")
    private String behalfAt;
    @Column(name = "fail_at")
    private String failAt;

    @Column(name = "fund_status")
    private Integer fundStatus;

    @Column(name = "his_compensatory_status")
    private Integer hisCompensatoryStatus;

    @Column(name = "his_compensatory_day")
    private Integer hisCompensatoryDay;

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
