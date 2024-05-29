package com.zzw.iCache.schedule.core.constant;

/**
 * schedule的一些常量
 *
 * @author zhangyang
 * @version $Id: ScheduleConstant.java,v 0.1 2020年06月18日 16:23 $Exp
 */
public class ScheduleConstant {

    /**
     * 单机随机限流
     */
    public static final String PROJECT_STRATEGY_RANDOM = "RANDOM";

    /**
     * 随机限流模式，随机休眠时间
     */
    public static final int  PROJECT_RANDOM_TIME = 10000;

    /**
     * redis分布式限流
     */
    public static final String PROJECT_STRATEGY_REDIS = "REDIS";

    /**
     * 使用redis分布式限流：集群模式
     */
    public static final String PROJECT_REDIS_OVERALL = "OVERALL";

    /**
     * 使用redis分布式限流：单机模式(此模式后续可以采用Guava的RateLimiter实现)
     */
    public static final String PROJECT_REDIS_CLIENT = "PER_CLIENT";

    /**
     * 使用redis分布式限流：redis key name
     */
    public static final String PROJECT_LIMITER_NAME = "SEA_DOG:SCHEDULE:RATE_LIMITER:";

    /**
     * 使用redis分布式限流：令牌桶初始化间隔
     */
    public static final int PROJECT_BUCKET_INTERVAL = 1000;

    /**
     * 使用redis分布式限流：令牌桶大小
     */
    public static final int PROJECT_BUCKET_SIZE = 10;

    /**
     * 使用redis分布式限流：最大等待令牌时间
     */
    public static final int PROJECT_WAIT_TIME = 10000;
}

