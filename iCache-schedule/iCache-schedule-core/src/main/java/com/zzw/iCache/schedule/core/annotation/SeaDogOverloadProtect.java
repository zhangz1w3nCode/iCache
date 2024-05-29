package com.zzw.iCache.schedule.core.annotation;

import com.zzw.iCache.schedule.core.constant.ScheduleConstant;

import java.lang.annotation.*;

/**
 * 定时刷新执行时，开启缓存过载保护，防止集群机器的本地定时任务同时触发
 * <p> 1、支持随机模式 </p>
 * <p> 2、支持redis分布式令牌桶模式 </p>
 *
 * @author zhangyang
 * @version $Id: SeaDogOverloadProtect.java,v 0.1 2020年06月18日 15:44 $Exp
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SeaDogOverloadProtect {

    /**
     * 保护策略
     * 默认 随机策略
     *
     * @return String
     */
    String strategy() default ScheduleConstant.PROJECT_STRATEGY_RANDOM;

    /**
     * 适用于：随机模式
     * 如果配置为10s，那么将在10s内随机休眠后 开始执行
     *
     * @return 随机休眠时间
     */
    int randomTime() default ScheduleConstant.PROJECT_RANDOM_TIME;

    /**
     * 分布式令牌桶模式 key name
     *
     * @return key name
     */
    String limiterName() default ScheduleConstant.PROJECT_LIMITER_NAME;

    /**
     * 分布式令牌桶模式 - 保护类型
     *
     * @return OVERALL-全局模式、PER_CLIENT-单机模式
     */
    String limiterType() default ScheduleConstant.PROJECT_REDIS_OVERALL;

    /**
     * 适用于：分布式令牌桶模式
     *
     * @return 令牌桶初始化频率
     */
    int bucketInterval() default ScheduleConstant.PROJECT_BUCKET_INTERVAL;

    /**
     * 适用于：分布式令牌桶模式
     *
     * @return 分布式令牌桶大小
     */
    int bucketSize() default ScheduleConstant.PROJECT_BUCKET_SIZE;

    /**
     * 适用于：分布式令牌桶模式
     *
     * @return 获取令牌最大等待时间
     */
    int waitTime() default ScheduleConstant.PROJECT_WAIT_TIME;
}
