package com.yyy.wrsf.enums;

/**
 * @author zc
 * @date 2019-02-02
 * 日志枚举
 */
public enum SysLogEnum {
    /**
     * 新增 1
     */
    LOG_ACT_ADD("新增", 1),
    /**
     * 修改 2
     */
    LOG_ACT_UPDATE("修改", 2),
    /**
     * 删除 3
     */
    LOG_ACT_DELETE("删除", 3);



    private String desc;
    private int value;

    SysLogEnum(String desc, int value) {
        this.desc = desc;
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
