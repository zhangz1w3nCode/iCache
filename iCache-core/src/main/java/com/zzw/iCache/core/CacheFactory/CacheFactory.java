package com.zzw.iCache.core.CacheFactory;

import com.zzw.iCache.core.CacheConfig.CacheConfig;
import com.zzw.iCache.core.RealCache.RealCache;

/**
 * @Author: zhangyang
 * @Data:2024/3/27 16:30
 * @Description:缓存工厂接口
 */
public interface CacheFactory {
    /**
     * 当前缓存工厂是否支持某种类型的缓存创建
     * @param cacheType
     * @return
     */
    boolean support(String cacheType);

    /**
     *  根据缓存配置获取缓存对象
     * @param cacheConfig 缓存配置类
     * @return
     */
    RealCache getCache(CacheConfig cacheConfig);
}
