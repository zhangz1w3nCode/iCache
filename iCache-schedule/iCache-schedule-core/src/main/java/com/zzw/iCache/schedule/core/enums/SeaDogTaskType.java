package com.zzw.iCache.schedule.core.enums;

/**
 * schedule task 类型
 *
 * @author zhangyang
 * @version $Id: SeaDogTaskType.java,v 0.1 2020年06月18日 17:08 $Exp
 */
public enum SeaDogTaskType {

    CRON("CRON"),

    FIXED_RATE("FIXED_RATE"),

    FIXED_DELAY("FIXED_DELAY");

    /**
     * 枚举值
     */
    private String value;

    /**
     * 构造方法
     *
     * @param value value
     */
    SeaDogTaskType(final String value) {
        this.value = value;
    }
}
