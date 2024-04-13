package com.zzw.iCache.autoconfigure.properties;

import com.zzw.iCache.schedule.core.constant.ScheduleConstant;
import com.zzw.iCache.schedule.core.protect.ScheduleProjectConfig;

/**
 * 缓存刷新-过载保护配置类
 *
 * @author Clark
 * @version $Id: ScheduleProjectProperties.java,v 0.1 2020年06月23日 15:48 $Exp
 */

public class ScheduleProjectProperties {

    /**
     * 保护策略
     */
    private String strategy = ScheduleConstant.PROJECT_STRATEGY_RANDOM;

    /**
     * 适用于：随机模式
     */
    private int randomTime = ScheduleConstant.PROJECT_RANDOM_TIME;

    /**
     * 分布式令牌桶模式 key name
     */
    private String limiterName = ScheduleConstant.PROJECT_LIMITER_NAME;

    /**
     * 分布式令牌桶模式
     */
    private String limiterType = ScheduleConstant.PROJECT_LIMITER_NAME;

    /**
     * 适用于：分布式令牌桶模式
     */
    private int bucketInterval = ScheduleConstant.PROJECT_BUCKET_INTERVAL;

    /**
     * 适用于：分布式令牌桶模式
     */
    private int bucketSize = ScheduleConstant.PROJECT_BUCKET_SIZE;

    /**
     * 适用于：分布式令牌桶模式
     */
    private long waitTime = ScheduleConstant.PROJECT_WAIT_TIME;

    public ScheduleProjectConfig projectConfig(){
        ScheduleProjectConfig projectConfig = new ScheduleProjectConfig();
        projectConfig.setStrategy(strategy);
        projectConfig.setRandomTime(randomTime);
        projectConfig.setLimiterName(limiterName);
        projectConfig.setLimiterType(limiterType);
        projectConfig.setBucketSize(bucketSize);
        projectConfig.setBucketInterval(bucketInterval);
        projectConfig.setWaitTime(waitTime);
        return projectConfig;
    }

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
