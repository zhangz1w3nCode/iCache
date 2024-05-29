package com.zzw.iCache.schedule.core.protect;

/**
 * 定时调度限流接口
 *
 * @author zhangyang
 * @version $Id: ScheduleRateLimiter.java,v 0.1 2020年06月18日 16:20 $Exp
 */
public interface ScheduleRateLimiter {

//  /**
//   * 一直阻塞直到拿到令牌
//   * <p> 阻塞 </p>
//   */
//  void acquire();
//
//  /**
//   * 尝试获取令牌，获取不到直接返回false
//   * <p> 不阻塞 </p>
//   *
//   * @return true-拿到令牌  | false-未拿到令牌
//   */
//  boolean tryAcquire();

    /**
     * 尝试获取令牌，等待一定时间
     * <p> 阻塞，但会超时 </p>
     *
     * @param waitTime 单位是毫秒
     *
     * @return true-拿到令牌  | false-未拿到令牌
     */
    boolean tryAcquire(long waitTime);
}

