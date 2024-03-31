package com.zzw.iCache.core.Cache;

import com.zzw.iCache.core.RealCache.valueWrapper.ValueWrapper;

import java.util.List;

/**
 * 统一缓存接口
 * @param <V>
 */
public interface Cache<V>{
    V get(String key);

    void put(String key, V value);

    List<ValueWrapper<V>> getValues();
}
