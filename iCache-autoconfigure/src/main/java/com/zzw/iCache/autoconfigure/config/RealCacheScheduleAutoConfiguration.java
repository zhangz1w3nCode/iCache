package com.zzw.iCache.autoconfigure.config;


import com.zzw.iCache.autoconfigure.properties.RealCacheProperties;
import com.zzw.iCache.autoconfigure.properties.RealProperties;
import com.zzw.iCache.core.CacheManager.CacheManager;
import com.zzw.iCache.core.CacheRefresher.CacheRefresh;
import com.zzw.iCache.schedule.core.CacheRefreshConfig;
import com.zzw.iCache.schedule.core.ScheduleAnnotationScanner;
import com.zzw.iCache.schedule.core.SeaDogScheduleBinder;
import com.zzw.iCache.schedule.core.maneger.SeaDogScheduleManager;
import com.zzw.iCache.schedule.core.tasks.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * 调度器配置类
 *
 * @author zhangyang
 * @version $Id: SeaDogScheduleAutoConfiguration.java,v 0.1 2020年04月14日 16:26 $Exp
 */
@Configuration
public class RealCacheScheduleAutoConfiguration implements SchedulingConfigurer {

    @Autowired
    private RealProperties seaDogProperties;

    @Autowired
    private CacheManager cacheManager;

    private SeaDogScheduleBinder seaDogScheduleBinder;

    private Map<String, CacheRefreshConfig> annoSchedules;


    /***
     * 此类初始化时，拿到schedule注解，之后用于bind定时任务
     *
     * @param cacheRefreshes 刷新实现类
     */
    public RealCacheScheduleAutoConfiguration(List<CacheRefresh> cacheRefreshes) {
        // scanner annotation schedule ...
        annoSchedules = ScheduleAnnotationScanner.findScheduleAnnotation(cacheRefreshes);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // 调度执行器的核心线程数如果大于1，那么本地定时任务会使用多线程调用，默认为单线程执行
        if(seaDogProperties.getSchedule() != null && seaDogProperties.getSchedule().getCorePoolSize() > 1) {
            taskRegistrar.setScheduler(Executors.newScheduledThreadPool(seaDogProperties.getSchedule().getCorePoolSize()));
        }

        bindScheduleTask(taskRegistrar);
    }

    @Bean
    public SeaDogScheduleBinder seaDogScheduleBinder(ConfigurableApplicationContext applicationContext,
                                                     SeaDogTaskFactory seaDogTaskFactory) {
        seaDogScheduleBinder = new SeaDogScheduleBinder(applicationContext, seaDogTaskFactory, cacheManager);
        return seaDogScheduleBinder;
    }

    @Bean
    public SeaDogScheduleManager seaDogScheduleManager(SeaDogScheduleBinder seaDogScheduleBinder) {
        return new SeaDogScheduleManager(seaDogScheduleBinder);
    }

    @Bean
    public SeaDogTaskFactory seaDogTaskFactory() {
        List<SeaDogTask> tasks = new ArrayList<>(3);

        SeaDogCronTask cronTask = new SeaDogCronTask();
        SeaDogFixedDelayTask fixedDelayTask = new SeaDogFixedDelayTask();
        SeaDogFixedRateTask fixedRateTask = new SeaDogFixedRateTask();
        tasks.add(cronTask);
        tasks.add(fixedDelayTask);
        tasks.add(fixedRateTask);

        SeaDogTaskFactory factory = new SeaDogTaskFactory(tasks);
        return factory;
    }

    /**
     * 循环本地缓存配置，给所有配置了刷新器的缓存绑定定时调度任务
     *
     * <p> 配置优先 </p>
     */
    private void bindScheduleTask(ScheduledTaskRegistrar taskRegistrar){
        seaDogScheduleBinder.setScheduledTaskRegistrar(taskRegistrar);

        // 通过配置方式绑定
        Map<String, RealCacheProperties> caches = seaDogProperties.getCaches();

        if (!CollectionUtils.isEmpty(caches)) {

            caches.forEach((k, v) -> {
                if( v.getRefresh() != null && v.getRefresh().size() > 0){

                    v.getRefresh().forEach((kBeanName, vConfig) -> {
                        vConfig.setBeanName(kBeanName);
                        seaDogScheduleBinder.bind(k, vConfig.refreshConfig());
                    });

                }
            });

        }

        // 通过注解方式绑定
        if (!CollectionUtils.isEmpty(annoSchedules)) {
            annoSchedules.forEach((k, v) -> seaDogScheduleBinder.bind(v.getCacheName(), v));
        }
    }
}
