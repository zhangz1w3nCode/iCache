package com.zzw.iCache.monitor.cacheMonitorImpl;

import com.alibaba.fastjson.JSON;
import com.zzw.iCache.autoconfigure.annocation.iCache;
import com.zzw.iCache.core.Cache.Cache;
import com.zzw.iCache.core.CacheConfig.CacheConfig;
import com.zzw.iCache.core.CacheManager.CacheManager;
import com.zzw.iCache.core.RealCache.RealCache;
import com.zzw.iCache.core.RealCache.valueWrapper.ValueWrapper;
import com.zzw.iCache.monitor.CacheMonitor;
import com.zzw.iCache.monitor.dto.CacheInfo;
import com.zzw.iCache.monitor.dto.ProductInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 接口实现类
 *
 */
@Component
@Service(version = "1.0.0")
public class DubboMonitorImpl implements CacheMonitor {

    @Autowired
    private CacheManager cacheManager;

    @iCache("productCache")
    Cache<ProductInfo> productInfoCache;

    /**
     * cacheName:定义缓存名称：比如商品缓存
     * key：需要查询缓存的key
     */
    @Override
    public Object getCache(String cacheName, String key) {

        //构建返回对象
        CacheInfo cacheInfo = new CacheInfo();

        //去缓存管理器中获取
        RealCache cache = cacheManager.getRealCache(cacheName);
        if(cache==null){
            return JSON.toJSONString(cacheInfo);
        }

        //去真缓存中获取
        ValueWrapper value = cache.get(key);
        if(value==null){
            return JSON.toJSONString(cacheInfo);
        }

        //对象转换
        cacheInfo.setData(value.getData());
        cacheInfo.setWriteTime(new Date(value.getWriteTime()));
        cacheInfo.setAccessTime(new Date(value.getAccessTime()));

        //看缓存是否过期了(需求多久？在cacheConfig去)
        //过期了就要续期
        CacheConfig cacheConfig = cacheManager.getCacheConfig(cacheName);

        if(cacheConfig.getExpireAfterWriteSecond()>0){
            long expireTime =  value.getWriteTime()+ TimeUnit.SECONDS.toMillis(cacheConfig.getExpireAfterWriteSecond());
            //计算剩余时间：剩余时间=过期时间-当前时间
            cacheInfo.setExpirationSecond(TimeUnit.SECONDS.toSeconds(expireTime-System.currentTimeMillis()));
        }else if(cacheConfig.getExpireAfterAccessSecond()>0){
            long expireTime =  value.getWriteTime()+ TimeUnit.SECONDS.toMillis(cacheConfig.getExpireAfterAccessSecond());
            //计算剩余时间：剩余时间=过期时间-当前时间
            cacheInfo.setExpirationSecond(TimeUnit.SECONDS.toSeconds(expireTime-System.currentTimeMillis()));
        }


        return cacheInfo;
    }

    /**
     * 模拟缓存存入
     *
     * @param skuSn
     * @param productName
     * @param productDesc
     */
    @Override
    public String putProductCache(String skuSn, String productName, String productDesc) {
        productInfoCache.put(skuSn,new ProductInfo(skuSn,productName,productDesc));
        return skuSn;
    }

    /**
     * 查询某个缓存的所有key
     *
     * @param cacheName
     */
    @Override
    public Set<String> getCacheKeys(String cacheName) {
        //去缓存管理器中获取
        RealCache cache = cacheManager.getRealCache(cacheName);
        if(cache==null){
            return Collections.EMPTY_SET;
        }

        Set<String> res = cache.getKeys();

        return res;
    }

    /**
     * 查询被管理的所有缓存
     */
    @Override
    public Set<String> getCacheNames() {
        return cacheManager.getCacheNames();
    }

    /**
     * 查询某个缓存的数据数量
     *
     * @param cacheName
     */
    @Override
    public long cacheSize(String cacheName) {
        //去缓存管理器中获取
        RealCache cache = cacheManager.getRealCache(cacheName);
        if(cache==null){
            return 0l;
        }

        return cache.size();
    }
}
