package com.zzw.iCache.schedule.core.tasks;


import com.zzw.iCache.schedule.core.CacheRefreshConfig;
import com.zzw.iCache.schedule.core.SeaDogSchedule;
import com.zzw.iCache.schedule.core.enums.SeaDogTaskType;
import com.zzw.iCache.schedule.core.protect.ScheduleProjectConfig;
import com.zzw.iCache.schedule.core.protect.ScheduleRateLimiter;
import com.zzw.iCache.schedule.core.wrapper.CacheRefreshWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 根据配置的不同，选择不同的定时器模式
 * <p> cron </p>
 * <p> fixed delay </p>
 * <p> fixed rate </p>
 *
 * @author zhangyang
 * @version $Id: SeaDogTaskFactory.java,v 0.1 2020年06月15日 21:44 $Exp
 */
public class SeaDogTaskFactory {
    private static final Logger log = LoggerFactory.getLogger(SeaDogTaskFactory.class);

    public Map<SeaDogTaskType, SeaDogTask> tasks = new HashMap<>();

    public Map<String, ScheduleRateLimiter> rateLimiters = new ConcurrentHashMap<>();

    /**
     * 添加cron、fixedDelay、fixedRate的job任务注册
     * @param tasks
     */
    public SeaDogTaskFactory(List<SeaDogTask> tasks){
        tasks.forEach(task -> this.tasks.put(task.taskType(), task));
    }

    public Map<String, ScheduleRateLimiter> getRateLimiters() {
        return rateLimiters;
    }

    /**
     * 添加任务到registrar中管理
     *
     * @param schedule 任务配置的定时刷新参数
     * @param refreshConfig 刷新器总配置
     * @param refreshWrapper 刷新器包装类
     * @param registrar spring schedule 定时任务注册器
     *
     * @return ScheduledTask
     */
    public ScheduledTask addTask(SeaDogSchedule schedule, CacheRefreshConfig refreshConfig, CacheRefreshWrapper refreshWrapper,
                                 ScheduledTaskRegistrar registrar){

        SeaDogTask seaDogTask = tasks.get(schedule.getTaskType());

        // 获取此缓存的过载保护器
        ScheduleRateLimiter rateLimiter = rateLimiters.get(refreshConfig.getCacheName());

        // 包装成一个线程对象，让schedule调度器执行run方法
        SeaDogRunnable runnable = new SeaDogRunnable(refreshWrapper, refreshConfig.getProjectConfig(), rateLimiter);

        return seaDogTask.addTask(schedule, runnable , registrar);
    }

    /**
     * 添加本地job时，需要利用到线程，调度器后面会执行run方法
     */
    class SeaDogRunnable implements Runnable {

        private CacheRefreshWrapper refreshWrapper;

        private ScheduleRateLimiter rateLimiter;

        private ScheduleProjectConfig projectConfig;

        public SeaDogRunnable(CacheRefreshWrapper refreshWrapper, ScheduleProjectConfig projectConfig, ScheduleRateLimiter rateLimiter){
            this.refreshWrapper = refreshWrapper;
            this.rateLimiter = rateLimiter;
            this.projectConfig = projectConfig;
        }

        @Override
        public void run() {

            try {
                // TODO 开启性能统计 StopWatch？

                // 判断是否有过载保护器
                if (rateLimiter != null) {
                    // 等待waitTime后强制退出令牌竞争，开始刷新
                    rateLimiter.tryAcquire(projectConfig.getWaitTime());
                }

                // 刷新缓存
                refreshWrapper.refresh();

            } catch (Exception ex) {
                log.error("[SeaDog] dynamic schedule {} unknown exception", refreshWrapper.getName(), ex);
            }
        }
    }

}