package com.zzw.iCache.schedule.core.protect;

/**
 * 缓存刷新-过载保护配置类
 *
 * @author zhangyang
 * @version $Id: ScheduleProjectConfig.java,v 0.1 2020年06月23日 15:48 $Exp
 */
public class ScheduleProjectConfig {

    /**
     * 保护策略
     */
    private String strategy;

    /**
     * 适用于：随机模式
     */
    private int randomTime;

    /**
     * 分布式令牌桶模式 key name
     */
    private String limiterName;

    /**
     * 分布式令牌桶模式
     */
    private String limiterType;

    /**
     * 适用于：分布式令牌桶模式
     */
    private int bucketInterval;

    /**
     * 适用于：分布式令牌桶模式
     */
    private int bucketSize;

    /**
     * 适用于：分布式令牌桶模式
     */
    private long waitTime;

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public int getRandomTime() {
        return randomTime;
    }

    public void setRandomTime(int randomTime) {
        this.randomTime = randomTime;
    }

    public String getLimiterName() {
        return limiterName;
    }

    public void setLimiterName(String limiterName) {
        this.limiterName = limiterName;
    }

    public String getLimiterType() {
        return limiterType;
    }

    public void setLimiterType(String limiterType) {
        this.limiterType = limiterType;
    }

    public int getBucketInterval() {
        return bucketInterval;
    }

    public void setBucketInterval(int bucketInterval) {
        this.bucketInterval = bucketInterval;
    }

    public int getBucketSize() {
        return bucketSize;
    }

    public void setBucketSize(int bucketSize) {
        this.bucketSize = bucketSize;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }
}
