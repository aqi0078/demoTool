package com.zdz.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
/**
 * 代偿账户
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "fund_behalf_account")
public class FundBehalfAccount {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fund_account_id")
    private Long fundAccountId;

    @Column(name = "card_no")
    private String cardNo;

    @Column(name = "card_holder_name")
    private String cardHolderName;
    @Column(name = "phone_no")
    private String phoneNo;
    @Column(name = "bank_name")
    private String bankName;
    @Column(name = "bank_code")
    private String bankCode;
    @Column(name = "branch_bank")
    private String branchBank;
    @Column(name = "card_type")
    private String cardType;
    @Column(name = "province")
    private String province;
    @Column(name = "city")
    private String city;
    @Column(name = "fund_status")
    private String fundStatus;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;


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
