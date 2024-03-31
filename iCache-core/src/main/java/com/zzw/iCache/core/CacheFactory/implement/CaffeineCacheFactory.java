package com.zzw.iCache.core.CacheFactory.implement;

import com.zzw.iCache.core.CacheConfig.CacheConfig;
import com.zzw.iCache.core.CacheConstant.CacheConstant;
import com.zzw.iCache.core.CacheFactory.CacheFactory;
import com.zzw.iCache.core.RealCache.RealCache;
import com.zzw.iCache.core.RealCache.implement.CaffeineCache;

/**
 * @Author: zhangyang
 * @Data:2024/3/27 16:31
 * @Description:CaffeineCacheFactory是实现类
 */
public class CaffeineCacheFactory implements CacheFactory, CacheConstant {
    @Override
    public boolean support(String cacheType) {
        return CACHE_TYPE_CAFFEINE.equals(cacheType);
    }

    @Override
    public RealCache getCache(CacheConfig cacheConfig) {
        return new CaffeineCache(cacheConfig);
    }
}
