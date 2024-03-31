package com.zzw.iCache.autoconfigure.properties;


import com.zzw.iCache.core.CacheConstant.CacheConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * SeaDog 配置类
 * @author qiaolin
 * @version $Id:  SeaDogProperties.java,v 0.1 2020年06月06日 14:05 $Exp
 */

@ConfigurationProperties(prefix = CacheConstant.REAL_CACHE)
public class RealProperties {

    /**
     *  缓存配置信息
     */
    private Map<String, RealCacheProperties> caches = new HashMap();

//    /**
//     *  缓存调度配置
//     */
//    private SeaDogScheduleProperties schedule = new SeaDogScheduleProperties();
//
//    /**
//     *  快照配置
//     */
//    private SeaDogSnapshotProperties snapshot = new SeaDogSnapshotProperties();



    public Map<String, RealCacheProperties> getCaches() {
        return caches;
    }

    public void setCaches(Map<String, RealCacheProperties> caches) {
        this.caches = caches;
    }

//    public SeaDogScheduleProperties getSchedule() {
//        return schedule;
//    }
//
//    public void setSchedule(SeaDogScheduleProperties schedule) {
//        this.schedule = schedule;
//    }
//
//    public SeaDogSnapshotProperties getSnapshot() {
//        return snapshot;
//    }
//
//    public void setSnapshot(SeaDogSnapshotProperties snapshot) {
//        this.snapshot = snapshot;
//    }
}
