package com.zzw.iCache.core.RealCache;

import com.zzw.iCache.core.RealCache.valueWrapper.ValueWrapper;

import java.util.List;
import java.util.Set;

/**
 * 真正缓存的接口
 * @param <V>
 */
public interface RealCache<V>{

    ValueWrapper<V> get(String key);

    void put(String key, V value);

    List<ValueWrapper<V>> getValues();

    Set<String> getKeys();

    long size();
}
