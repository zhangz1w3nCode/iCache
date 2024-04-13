package com.zzw.iCache.schedule.core;


import com.zzw.iCache.core.CacheRefresher.CacheRefresh;
import com.zzw.iCache.schedule.core.annotation.SeaDogOverloadProtect;
import com.zzw.iCache.schedule.core.annotation.SeaDogSchedule;
import com.zzw.iCache.schedule.core.protect.ScheduleProjectConfig;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * schedule 注解扫描类
 *
 * @author Clark
 * @version $Id: ScheduleAnnotationScanner.java,v 0.1 2020年06月19日 10:31 $Exp
 */
public class ScheduleAnnotationScanner {

    /**
     * 拿到刷新实现类上面的schedule注解
     *
     * @param refreshes 刷新实现类
     *
     * @return Map CacheRefreshConfig
     */
    public static Map<String, CacheRefreshConfig> findScheduleAnnotation(List<CacheRefresh> refreshes) {
        if(refreshes == null || refreshes.size() < 1) {
            return null;
        }

        Map<String, CacheRefreshConfig> annotationSchedules = new HashMap<>();

        refreshes.forEach(cacheRefresh -> {

            SeaDogSchedule[] annotations = cacheRefresh.getClass().getAnnotationsByType(SeaDogSchedule.class);
            SeaDogOverloadProtect[] protects = cacheRefresh.getClass().getAnnotationsByType(SeaDogOverloadProtect.class);

            if(annotations != null && annotations.length > 0){

                SeaDogSchedule schedule = annotations[0];
                CacheRefreshConfig refreshConfig = new CacheRefreshConfig();
                // 缓存名
                refreshConfig.setCacheName(schedule.cacheName());

                // 刷新器名
                if(StringUtils.isNotEmpty(schedule.beanName())) {
                    refreshConfig.setBeanName(schedule.beanName());
                } else {
                    refreshConfig.setBeanName(StringUtils.uncapitalize(cacheRefresh.getClass().getSimpleName()));
                }

                refreshConfig.setCorn(schedule.cron());
                refreshConfig.setFixedDelay(schedule.fixedDelay());
                refreshConfig.setFixedRate(schedule.fixedRate());
                refreshConfig.setInitialDelay(schedule.initialDelay());
                refreshConfig.setStartup(schedule.startup());

                // 设置过载保护
                refreshConfig.setProjectConfig(getProjectConfig(protects));

                annotationSchedules.put(refreshConfig.getBeanName(), refreshConfig);
            }

        });

        return annotationSchedules;
    }

    private static ScheduleProjectConfig getProjectConfig(SeaDogOverloadProtect[] protects) {
        if(protects != null && protects.length > 0) {
            SeaDogOverloadProtect overloadProtect = protects[0];

            ScheduleProjectConfig projectConfig = new ScheduleProjectConfig();
            projectConfig.setStrategy(overloadProtect.strategy());
            projectConfig.setRandomTime(overloadProtect.randomTime());
            projectConfig.setLimiterName(overloadProtect.limiterName().toUpperCase());
            projectConfig.setLimiterType(overloadProtect.limiterType());
            projectConfig.setBucketSize(overloadProtect.bucketSize());
            projectConfig.setBucketInterval(overloadProtect.bucketInterval());
            projectConfig.setWaitTime(overloadProtect.waitTime());

            return projectConfig;
        }

        return null;
    }

}

