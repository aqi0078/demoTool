//package com.zdz.bean;
//
//import lombok.Data;
//
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Entity
//@Data
//@Table(name = "info")
//public class FundBehalfDetail {
//
//
//    @Id
//    private Long id;
//	`fund_id` bigint(20) NOT NULL COMMENT '资金方id',
//	`loan_id` bigint(20) NOT NULL COMMENT 'loan标识',
//	`plan_id` bigint(20) NOT NULL COMMENT 'plan标识',
//	`term_no` tinyint(3) DEFAULT NULL COMMENT '期数',
//	`pay_status` tinyint(2) NOT NULL DEFAULT 0 COMMENT '代偿支付状态：0-未处理，1-处理中 3-成功 4-失败',
//	`repaid_at` date DEFAULT NULL COMMENT '应还日期',
//	`behalf_at` date DEFAULT NULL COMMENT '代偿日期',
//	`required_amount` decimal(20,2) DEFAULT 0.00 COMMENT '应还总额',
//	`required_principal` decimal(20,2) DEFAULT 0.00 COMMENT '应还本金',
//	`required_interest` decimal(20,2) DEFAULT 0.00 COMMENT '应还利息',
//	`required_overdue` decimal(20,2) DEFAULT 0.00 COMMENT '应还罚息',
//	`required_service_fee` decimal(20,2) DEFAULT 0.00 COMMENT '应还服务费',
//	`behalf_amount` decimal(20,2) DEFAULT 0.00 COMMENT '代偿总额',
//	`behalf_principal` decimal(20,2) DEFAULT 0.00 COMMENT '代偿本金',
//	`behalf_interest` decimal(20,2) DEFAULT 0.00 COMMENT '代偿利息',
//	`behalf_overdue` decimal(20,2) DEFAULT 0.00 COMMENT '代偿罚息',
//	`behalf_service_fee` decimal(20,2) DEFAULT 0.00 COMMENT '代偿服务费',
//	`ref_id` bigint(20) DEFAULT NULL COMMENT '关联fund_behalf_settle_summery.id',
//	`behalf_config_id` bigint(20) DEFAULT NULL COMMENT '关联fund_behalf_task_config.id',
//	`behalf_fund_id` bigint(20) DEFAULT NULL COMMENT '关联fund_behalf.fund_behalf_no',
//	`enable` tinyint(3) DEFAULT 1 COMMENT '删除标记',
//	`created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
//	`updated_at` timestamp NULL ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
//
//
//
//}
