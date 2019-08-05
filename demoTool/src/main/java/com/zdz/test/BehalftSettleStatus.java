package com.zdz.test;

import lombok.Getter;

@Getter
public enum BehalftSettleStatus {
    not_settled(0, "未结算"),
    settled(1, "已结算");

    int value;
    String desc;

    BehalftSettleStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}
