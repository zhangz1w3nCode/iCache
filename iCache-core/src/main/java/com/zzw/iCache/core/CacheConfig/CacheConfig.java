package com.zzw.iCache.core.CacheConfig;

import com.zzw.iCache.core.CacheConstant.CacheConstant;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;



@Getter
public class CacheConfig implements Cloneable, CacheConstant {

    /**
     *  缓存名称
     */
    private String name;

    /**
     *  当前缓存的类型，默认Caffeine
     */
    private String cacheType = CACHE_TYPE_CAFFEINE;

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


    public CacheConfig() {
    }

    //
    public CacheConfig(String name, String cacheType, int expireAfterWriteSecond, int expireAfterAccessSecond, int initialCapacity, int maxSize) {
        this.name = name;
        this.cacheType = cacheType;
        this.expireAfterWriteSecond = expireAfterWriteSecond;
        this.expireAfterAccessSecond = expireAfterAccessSecond;
        this.initialCapacity = initialCapacity;
        this.maxSize = maxSize;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCacheType(String cacheType) {
        this.cacheType = cacheType;
    }

    public void setExpireAfterWriteSecond(int expireAfterWriteSecond) {
        this.expireAfterWriteSecond = expireAfterWriteSecond;
    }

    public void setExpireAfterAccessSecond(int expireAfterAccessSecond) {
        this.expireAfterAccessSecond = expireAfterAccessSecond;
    }

    public void setInitialCapacity(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public void setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
