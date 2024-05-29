package com.zzw.iCache.schedule.core.maneger;


import com.zzw.iCache.schedule.core.CacheRefreshConfig;
import com.zzw.iCache.schedule.core.SeaDogScheduleBinder;
import com.zzw.iCache.schedule.core.wrapper.CacheRefreshWrapper;

import java.util.Map;

/**
 * schedule 管理器
 *
 * @author zhangyang
 * @version $Id: SeaDogScheduleManager.java,v 0.1 2020年06月08日 20:50 $Exp
 */
public class SeaDogScheduleManager {

    private SeaDogScheduleBinder seaDogScheduleBinder;


    public SeaDogScheduleManager(SeaDogScheduleBinder seaDogScheduleBinder) {
        this.seaDogScheduleBinder = seaDogScheduleBinder;
    }
    /**
     * 获取所有的定时刷新器的包装类
     *
     * @return 定时刷新器包装类
     */
    public Map<String, CacheRefreshWrapper> getAllCacheRefresh(){
        return this.seaDogScheduleBinder.getCacheRefreshMap();
    }

    /**
     * 停止定时任务
     *
     * @param cacheName 缓存名称
     */
    public void cancel(String cacheName) {
        if (!seaDogScheduleBinder.getScheduledFutures().containsKey(cacheName)) {
            return;
        }
        // 一般情况下，一个缓存对应一个刷新器，但特殊场景一个缓存会有多个刷新器
        seaDogScheduleBinder.getScheduledFutures().get(cacheName).forEach((k, task)-> task.cancel());
    }

    /**
     * 修改定时任务
     *
     * @param cacheName 缓存名称
     * @param refreshConfig 刷新配置
     */
    public void change(String cacheName, CacheRefreshConfig refreshConfig) {
        // 1、首先cancel
        cancel(cacheName);

        // 2、再重新绑定
        seaDogScheduleBinder.bind(cacheName, refreshConfig);
    }

}
