package com.zzw.iCache.monitor.cacheMonitorImpl;

import com.alibaba.fastjson.JSON;
import com.zzw.iCache.core.CacheConfig.CacheConfig;
import com.zzw.iCache.core.CacheManager.CacheManager;
import com.zzw.iCache.core.RealCache.RealCache;
import com.zzw.iCache.core.RealCache.valueWrapper.ValueWrapper;
import com.zzw.iCache.monitor.CacheMonitor;
import com.zzw.iCache.monitor.dto.CacheInfo;
import com.zzw.iCache.schedule.core.maneger.SeaDogScheduleManager;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 接口实现类
 *
 */
@Component
@Service
public class DubboMonitorImpl implements CacheMonitor {

    @Autowired
    private CacheManager cacheManager;

    private static final String REFRESH_ALL = "refresh_all";



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

    @Override
    public Double calculateMemoryUsage(String cacheName) {
        //去缓存管理器中获取
        RealCache cache = cacheManager.getRealCache(cacheName);
        if(cache==null){
            return 0.0;
        }
        return cache.calculateMemoryUsage();
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

    @Override
    public void refreshCache(String cacheName, String refreshName) {
        // 如果是 refresh_all，则调用所有的刷新器
        if(REFRESH_ALL.equalsIgnoreCase(refreshName)){
            Set<String> refreshNames = cacheManager.getRefreshNames(cacheName);
            for (String name : refreshNames) {
                try {
                    cacheManager.refreshCache(cacheName, name);
                } catch (Exception e) {
                    //log.info("调用刷新器失败 cacheName:{}, refresh:{}", cacheName, name, e);
                    System.out.println("调用刷新器失败"+"---"+cacheName+"---"+name);
                    System.out.println(e);
                }
            }
        } else {
            // 是具体刷新器时，直接调用
            try {
                cacheManager.refreshCache(cacheName, refreshName);
            } catch (Exception e) {
                System.out.println("调用刷新器失败"+"---"+cacheName+"---"+refreshName);
                System.out.println(e);
            }
        }
    }

    @Override
    public Set<String> refreshNames(String cacheName) {
        // 如果是 refresh_all，则调用所有的刷新器
        Set<String> res = new HashSet<>();
            Set<String> refreshNames = cacheManager.getRefreshNames(cacheName);
            for (String name : refreshNames) {
                try {
                    cacheManager.refreshCache(cacheName, name);
                } catch (Exception e) {
                    //log.info("调用刷新器失败 cacheName:{}, refresh:{}", cacheName, name, e);
                    System.out.println("调用刷新器失败"+"---"+cacheName+"---"+name);
                    System.out.println(e);
                }
            }

        return res;

    }
}
