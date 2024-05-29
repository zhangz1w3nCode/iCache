package com.zzw.iCache.schedule.protect.random;


import com.zzw.iCache.schedule.core.CacheRefreshConfig;
import com.zzw.iCache.schedule.core.constant.ScheduleConstant;
import com.zzw.iCache.schedule.core.protect.ScheduleProjectConfig;
import com.zzw.iCache.schedule.core.protect.ScheduleRateLimiter;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;


import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 随机限流模式
 *
 * @author zhangyang
 * @version $Id: ScheduleRandomRateLimiter.java,v 0.1 2020年06月22日 10:59 $Exp
 */
public class ScheduleRandomRateLimiter implements ScheduleRateLimiter {

    /**
     * 单位毫秒
     */
    private int randomTime;

    public ScheduleRandomRateLimiter(int randomTime) {
        this.randomTime = randomTime;
    }

    @Override
    public boolean tryAcquire(long waitTime) {
        if (randomTime <= 0) {
            return false;
        }

        Random random = new Random();
        try {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(randomTime));
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}

