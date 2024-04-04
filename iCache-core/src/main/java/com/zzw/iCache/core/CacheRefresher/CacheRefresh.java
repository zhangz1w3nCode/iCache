package com.zzw.iCache.core.CacheRefresher;


import com.zzw.iCache.core.Cache.Cache;


/**
 * 缓存管理类
 * @author zhangyang
 * @version 1.0
 */

public interface CacheRefresh<V> {

    /**
     * 缓存刷新
     * @param cache 待刷新的缓存对象
     */
    void refresh(Cache<V> cache);

}
