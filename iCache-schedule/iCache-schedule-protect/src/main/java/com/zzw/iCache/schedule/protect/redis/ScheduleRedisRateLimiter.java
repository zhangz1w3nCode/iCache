package com.zzw.iCache.schedule.protect.redis;



import com.zzw.iCache.schedule.core.CacheRefreshConfig;
import com.zzw.iCache.schedule.core.constant.ScheduleConstant;
import com.zzw.iCache.schedule.core.protect.ScheduleProjectConfig;
import com.zzw.iCache.schedule.core.protect.ScheduleRateLimiter;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * 基于redisoon，分布式令牌桶模式
 *
 * @author zhangyang
 * @version $Id: ScheduleRedisRateLimiter.java,v 0.1 2020年06月22日 16:23 $Exp
 */
public class ScheduleRedisRateLimiter implements ScheduleRateLimiter {

    /**
     * redisoon 分布式令牌桶
     */
    private RRateLimiter rRateLimiter;

    public ScheduleRedisRateLimiter(RedissonClient redissonClient, CacheRefreshConfig refreshConfig) {

        ScheduleProjectConfig projectConfig = refreshConfig.getProjectConfig();

        if(projectConfig.getLimiterName() == null || projectConfig.getLimiterName().length() <= 0){
            throw new RuntimeException("Schedule limiterName not null");
        }

        // 创建一个令牌器
        rRateLimiter = redissonClient.getRateLimiter(projectConfig.getLimiterName() + refreshConfig.getBeanName().toUpperCase());

        // 限流模式
        RateType rateType = ScheduleConstant.PROJECT_REDIS_OVERALL.equals(projectConfig.getLimiterType()) ? RateType.OVERALL : RateType.PER_CLIENT;

        // 初始化令牌桶，param1：单机或集群、param2：令牌桶大小、param3：令牌桶刷新频率、param4：令牌桶刷新频率时间单位
        rRateLimiter.trySetRate(rateType, projectConfig.getBucketSize(), projectConfig.getBucketInterval(), RateIntervalUnit.SECONDS);
    }

    @Override
    public boolean tryAcquire(long waitTime) {
        return rRateLimiter.tryAcquire(waitTime, TimeUnit.MILLISECONDS);
    }
}

