package com.zzw.iCache.autoconfigure.properties;

/**
 * 缓存调度配置类
 * @author zhangyang
 * @version $Id:  SeaDogScheduleProperties.java,v 0.1 2020年06月06日 14:11 $Exp
 */
public class SeaDogScheduleProperties {

    /**
     *  缓存调度线程池大小
     */
    private int corePoolSize = 1;

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }
}
