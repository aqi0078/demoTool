package com.zdz.bean;

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
    private String fundId;

    @Column(name = "fund_name")
    private String fundName;

    @Column(name = "fund_account")
    private String fundAccount;

    @Column(name = "behalf_at")
    private String behalfAt;

    @Column(name = "fund_status")
    private int fundStatus;

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