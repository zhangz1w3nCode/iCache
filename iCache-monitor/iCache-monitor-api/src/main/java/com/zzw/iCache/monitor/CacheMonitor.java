package com.zzw.iCache.monitor;

import java.util.Set;




/**
 * 缓存监控接口
 */
public interface CacheMonitor {
    /**
      cacheName:定义缓存名称：比如商品缓存
      key：需要查询缓存的key
     */
     Object getCache(String cacheName,String key);


     /**
     查询某个缓存的所有key
      */
     Set<String> getCacheKeys(String cacheName);

    /**
    查询被管理的所有缓存
     */
    Set<String> getCacheNames();

    /**
     查询某个缓存的数据数量
     */
    long cacheSize(String cacheName);


    void refreshCache(String cacheName, String refreshName);
}
