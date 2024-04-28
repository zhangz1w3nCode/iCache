package com.zzw.iCache.core.RealCache.implement;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.zzw.iCache.core.CacheConfig.CacheConfig;
import com.zzw.iCache.core.CacheConstant.CacheConstant;
import com.zzw.iCache.core.RealCache.RealCache;
import com.zzw.iCache.core.RealCache.valueWrapper.ValueWrapper;
import org.apache.dubbo.common.utils.CollectionUtils;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

import java.text.DecimalFormat;

/**
 * @Author: zhangyang
 * @Data:2024/3/27 14:52
 * @Description:真正缓存RealCache：Caffeine实现
 */
public class CaffeineCache<V> implements RealCache<V>, CacheConstant {

    //缓存名称
    private String name;

    //具体的缓存实现 这里是caffeine的实现
    private Cache<String, ValueWrapper<V>> cache;

    // 构建caffeine实例 通过缓存配置类 初始化 cache的相关信息
    public CaffeineCache(CacheConfig cacheConfig){
        this.name = cacheConfig.getName();

        //通过builder去构建一个原生的caffeine实例
        Caffeine<Object, Object> builder = Caffeine.newBuilder();

        //初始化缓存的容量
        if(cacheConfig.getInitialCapacity() > 0){
            builder.initialCapacity(cacheConfig.getInitialCapacity());
        }

        //初始化缓存的写入多少秒之后过期
        if(cacheConfig.getExpireAfterWriteSecond() > 0){
            builder.expireAfterWrite(cacheConfig.getExpireAfterWriteSecond(), TimeUnit.SECONDS);
        }

        //初始化缓存的访问多少秒之后过期
        if(cacheConfig.getExpireAfterAccessSecond() > 0){
            builder.expireAfterAccess(cacheConfig.getExpireAfterAccessSecond(), TimeUnit.SECONDS);
        }

        //初始化缓存的缓存最大容量
        if(cacheConfig.getMaxSize() > 0){
            builder.maximumSize(cacheConfig.getMaxSize());
        }


        //构建
        cache = builder.build();

        System.out.println("成功构建一个CaffeineCache"+cacheConfig.toString());
    }

    @Override
    public ValueWrapper<V> get(String key) {
        return cache.getIfPresent(key);
    }

    @Override
    public void put(String key, V value) {
        cache.put(key, new ValueWrapper<>(value));
    }

    @Override
    public List<ValueWrapper<V>> getValues() {

        Collection<ValueWrapper<V>> values = cache.asMap().values();

        if(CollectionUtils.isNotEmpty(values)){
            return new ArrayList<>(values);
        }

        return Collections.emptyList();
    }

    @Override
    public Set<String> getKeys() {
        return cache.asMap().keySet();
    }

    @Override
    public long size() {
        return cache.estimatedSize();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Double calculateMemoryUsage() {

        // 计算缓存对象所占用的内存
        long cacheSize =(cache.estimatedSize());

        double totalSizeInMB = cacheSize * 1.0/ 1024.0/1024.0;
        // 获取当前JVM占用的内存情况


        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        // 椎内存使用情况
        MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();

        // 最大可用内存
        long maxMemorySize = memoryUsage.getMax();
        // 已使用的内存
        long usedMemorySize = memoryUsage.getUsed();

        double used = usedMemorySize * 1.0 / 1024.0 / 1024.0;
        double max = maxMemorySize * 1.0 / 1024.0 / 1024.0;

        System.err.println("已使用的内存(JVM):" + new DecimalFormat("#.#").format(usedMemorySize * 1.0 / 1024.0 / 1024.0) + "M");
        System.err.println("最大可用内存(JVM):" + new DecimalFormat("#.#").format(maxMemorySize * 1.0 / 1024.0 / 1024.0) + "M");
        BigDecimal bigDecimal = new BigDecimal(used / max * 100).setScale(2, BigDecimal.ROUND_HALF_UP);
        System.err.println("占内存的大小比率:" +bigDecimal+"%");

        return bigDecimal.doubleValue();
    }

    //单位转化为MB
    private String formatMemory(long memory){
        if (memory < 1024) {
            return memory + "B";
        } else if (memory < 1024 * 1024) {
            return memory / 1024 + "KB";
        } else if (memory < 1024 * 1024 * 1024) {
            return memory / (1024 * 1024) + "MB";
        } else {
            return memory / (1024 * 1024 * 1024) + "GB";
        }
    }


}
