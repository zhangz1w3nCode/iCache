package com.zzw.iCache.core.CacheManager;


import com.zzw.iCache.core.Cache.Cache;
import com.zzw.iCache.core.CacheConfig.CacheConfig;
import com.zzw.iCache.core.CacheFilter.CacheFilter;
import com.zzw.iCache.core.CacheListener.CacheListener;
import com.zzw.iCache.core.RealCache.RealCache;

import java.util.List;


/**
 * 缓存管理类
 * @author qiaolin
 * @version $Id:  CacheManager.java,v 0.1 2020年06月03日 16:06 $Exp
 */

public interface CacheManager {

    /**
     * 获取最原始的缓存实现
     * @param name
     * @return
     */
    RealCache getRealCache(String name);

    /**
     *  获取缓存
     * @param name 缓存名称
     * @return 缓存对象
     */
    RealCache getCache(String name);

    /**
     * 获取对外的缓存对象
     * @param name
     * @return
     */
    Cache getFacadeCache(String name);

    /**
     * 创建缓存
     * @param cacheConfig
     * @param cacheFilters
     * @param listeners
     * @return
     */
    Cache createCache(CacheConfig cacheConfig, List<CacheFilter> cacheFilters, List<CacheListener> listeners);


    CacheConfig getCacheConfig(String name);
//
//    /**
//     *  是否包含某个缓存
//     * @param name 缓存名称
//     * @return
//     */
//    boolean containsCache(String name);
//
//    /**
//     * 获取所有的缓存名称
//     * @return
//     */
//    Set<String> getCacheNames();
//
//    /**
//     * 注册刷新器到缓存管理器中统一管理
//     * @param cacheName 缓存名成
//     * @param refreshName 刷新器名称
//     * @param refresh 刷新器
//     */
//    void registryRefresh(String cacheName, String refreshName, CacheRefresh refresh);
//
//    /**
//     * 根据缓存名获取对应所有的刷新器名称
//     * @param cacheName 缓存名
//     * @return 刷新器列表，一个缓存可以对应多个刷新器
//     */
//    Set<String> getRefreshNames(String cacheName);
//
//    /**
//     * 根据缓存名、刷新器名获取对应的刷新器
//     * @param cacheName 缓存名
//     * @param refreshName 刷新器名称
//     * @return 刷新器对象
//     */
//    void refreshCache(String cacheName, String refreshName);
}
