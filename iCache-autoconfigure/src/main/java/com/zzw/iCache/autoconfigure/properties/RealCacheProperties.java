package com.zzw.iCache.autoconfigure.properties;



import com.alibaba.fastjson.JSON;
import com.zzw.iCache.core.CacheConfig.CacheConfig;
import com.zzw.iCache.core.CacheConstant.CacheConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存配置信息
 */
public class RealCacheProperties {


    /**
     *  缓存名称
     */
    private String name;

    /**
     *  当前缓存的类型，默认Caffeine
     */
    private String type = CacheConstant.CACHE_TYPE_CAFFEINE;

    /**
     *  缓存刷新策略
     */
    private Map<String, SeaDogCacheRefreshProperties> refresh;

    /**
     *  缓存对应的过滤器
     */
    private String[] filter = {};

    /**
     *  缓存对应的监听器
     */
    private String[] listener = {};

    /**
     *  写入多少秒之后过期
     */
    private int expireAfterWriteSecond = 0;

    /**
     *  访问多少秒之后过期
     */
    private int expireAfterAccessSecond = 0;

    /**
     *  缓存初始化大小
     */
    private int initialCapacity = 0;

    /**
     *  缓存最大容量
     */
    private int maxSize = 0;

    /**
     *  其他配置信息，方便后面扩展用
     */
    private Map<String, String> attachments = new HashMap<>();


    public CacheConfig cacheConfig(){

        CacheConfig cacheConfig = new CacheConfig();
        cacheConfig.setName(name);
        cacheConfig.setMaxSize(maxSize);
        cacheConfig.setExpireAfterAccessSecond(expireAfterAccessSecond);
        cacheConfig.setExpireAfterWriteSecond(expireAfterWriteSecond);
        cacheConfig.setInitialCapacity(initialCapacity);


        if(!StringUtils.isEmpty(type)){
            cacheConfig.setCacheType(type);
        }

        if(!attachments.isEmpty()){
            cacheConfig.setAttachments(attachments);
        }

        System.out.println("通过资源对象创建配置对象成功"+ JSON.toJSONString(cacheConfig));

        return cacheConfig;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, SeaDogCacheRefreshProperties> getRefresh() {
        return refresh;
    }

    public void setRefresh(Map<String, SeaDogCacheRefreshProperties> refresh) {
        this.refresh = refresh;
    }

    public String[] getFilter() {
        return filter;
    }

    public void setFilter(String[] filter) {
        this.filter = filter;
    }

    public String[] getListener() {
        return listener;
    }

    public void setListener(String[] listener) {
        this.listener = listener;
    }

    public int getExpireAfterWriteSecond() {
        return expireAfterWriteSecond;
    }

    public void setExpireAfterWriteSecond(int expireAfterWriteSecond) {
        this.expireAfterWriteSecond = expireAfterWriteSecond;
    }

    public int getExpireAfterAccessSecond() {
        return expireAfterAccessSecond;
    }

    public void setExpireAfterAccessSecond(int expireAfterAccessSecond) {
        this.expireAfterAccessSecond = expireAfterAccessSecond;
    }

    public int getInitialCapacity() {
        return initialCapacity;
    }

    public void setInitialCapacity(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}
