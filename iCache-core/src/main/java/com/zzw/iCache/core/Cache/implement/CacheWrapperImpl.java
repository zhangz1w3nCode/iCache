package com.zzw.iCache.core.Cache.implement;

import com.zzw.iCache.core.Cache.Cache;
import com.zzw.iCache.core.RealCache.RealCache;
import com.zzw.iCache.core.RealCache.valueWrapper.ValueWrapper;

import java.util.Collections;
import java.util.List;

/**
 * @Author: zhangyang
 * @Data:2024/3/27 14:52
 * @Description:
 */
public class CacheWrapperImpl<V> implements Cache<V> {

    //引入真正的缓存实现类
    private RealCache<V> realCache;

    public CacheWrapperImpl(RealCache<V> cache) {
        this.realCache = cache;
    }

    @Override
    public V get(String key) {
        ValueWrapper<V> valueWrapper = realCache.get(key);
        if (valueWrapper == null) {
            return null;
        }
        valueWrapper.updateAccessTime();
        return valueWrapper.getData();
    }

    @Override
    public void put(String key, V value) {
        realCache.put(key, value);
    }

    @Override
    public List<ValueWrapper<V>> getValues() {
        List<V> allValues = Collections.emptyList();

        List<ValueWrapper<V>> valueWrapperList = realCache.getValues();

//        if (CollectionUtils.isNotEmpty(valueWrapperList)) {
//            for (ValueWrapper<V> item : valueWrapperList) {
//                item.updateAccessTime();
//                allValues.add(item.getData());
//            }
//        }

        return valueWrapperList;
    }
}
