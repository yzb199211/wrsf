package com.yyy.wrsf.enums;


import com.yyy.wrsf.enums.util.EnumEntity;
import com.yyy.wrsf.enums.util.EnumUtils;

import java.util.List;

/**
 * [订单状态枚举]
 *
 * @version v1.0
 * @Author: cyq
 * @Date: 2020/3/18
 */
public enum ContractStatusEnum {
    CANCEL(-1, "取消订单"),

    WAIT_SUBMIT(0, "未下单"),

    PLACE_ORDER(1, "待确认"),

    CONFIRM(2, "已确认"),

    WAIT_SEND(3, "待发货"),

    TRANSING(4, "运输中"),

    WAIT_REC(5, "待收货"),

    FINISH(6, "已完成");

    private Integer status;
    private String desc;

    ContractStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String getDescByStatus(Integer status) {
        for (ContractStatusEnum contractStatusEnum : ContractStatusEnum.values()) {
            if (contractStatusEnum.getStatus().equals(status))
                return contractStatusEnum.getDesc();
        }
        return "";
    }

    public static String getName(Integer status) {
        List<EnumEntity> enumEntityList = EnumUtils.getEnumList(ContractStatusEnum.values(), ContractStatusEnum::getStatus, ContractStatusEnum::getDesc);
        for (EnumEntity enumEntity : enumEntityList) {
            if (enumEntity.getId().equals(status)) {
                return enumEntity.getName();
            }
        }
        return null;
    }
}
