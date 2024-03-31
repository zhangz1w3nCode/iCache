package com.zzw.iCache.core.CacheFilter.implement;

import com.zzw.iCache.core.CacheFilter.CacheFilter;
import com.zzw.iCache.core.RealCache.RealCache;
import com.zzw.iCache.core.RealCache.valueWrapper.ValueWrapper;

/**
 * @Author: zhangyang
 * @Data:2024/3/27 17:52
 * @Description:
 */
public class CacheFilterImp implements CacheFilter<Object> {

    @Override
    public ValueWrapper<Object> get(RealCache<Object> filterChain, RealCache<Object> realCache, String key) {
        return CacheFilter.super.get(filterChain, realCache, key);
    }

    @Override
    public void put(RealCache<Object> filterChain, RealCache<Object> realCache, String key, Object value) {
        CacheFilter.super.put(filterChain, realCache, key, value);
    }
}
