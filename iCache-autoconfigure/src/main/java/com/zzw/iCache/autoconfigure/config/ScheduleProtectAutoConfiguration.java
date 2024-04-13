package com.zzw.iCache.autoconfigure.config;



import com.zzw.iCache.autoconfigure.properties.RealCacheProperties;
import com.zzw.iCache.autoconfigure.properties.RealProperties;
import com.zzw.iCache.core.CacheRefresher.CacheRefresh;
import com.zzw.iCache.schedule.core.CacheRefreshConfig;
import com.zzw.iCache.schedule.core.ScheduleAnnotationScanner;
import com.zzw.iCache.schedule.core.constant.ScheduleConstant;
import com.zzw.iCache.schedule.core.protect.ScheduleProjectConfig;
import com.zzw.iCache.schedule.core.tasks.SeaDogTaskFactory;
import com.zzw.iCache.schedule.protect.random.ScheduleRandomRateLimiter;
import com.zzw.iCache.schedule.protect.redis.ScheduleRedisRateLimiter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * 调度过载保护配置类
 *
 * @author Clark
 * @version $Id: ScheduleProtectAutoConfiguration.java,v 0.1 2020年06月27日 9:58 $Exp
 */
@ConditionalOnClass(ScheduleRandomRateLimiter.class)
@Configuration
@ConditionalOnBean(RealCacheScheduleAutoConfiguration.class)
public class ScheduleProtectAutoConfiguration {

    @Autowired(required = false)
    private RedissonClient redissonClient;

    @Autowired
    private RealProperties properties;

    @Autowired
    private SeaDogTaskFactory seaDogTaskFactory;

    @Autowired
    private List<CacheRefresh> cacheRefreshes;

    @PostConstruct
    public void addRateLimiter(){
        // scanner annotation schedule ...
        Map<String, CacheRefreshConfig> annoSchedules = ScheduleAnnotationScanner.findScheduleAnnotation(cacheRefreshes);

        // 通过配置方式绑定，配置优先
        Map<String, RealCacheProperties> caches = properties.getCaches();
        if(caches != null && caches.size() > 0){
            caches.forEach((cacheName, cacheProperties) -> {
                if(cacheProperties.getRefresh() != null) {
                    // 一个缓存可能对于多个刷新器，每个刷新器一对一过载保护
                    cacheProperties.getRefresh().forEach((beanName, refreshProperties) -> {
                        refreshProperties.setBeanName(beanName);
                        addRateLimiter(cacheName, refreshProperties.refreshConfig(), seaDogTaskFactory);
                    });
                }
            });
        }

        // 通过注解方式绑定
        if (!CollectionUtils.isEmpty(annoSchedules)) {
            annoSchedules.forEach((k, v) -> addRateLimiter(k, v, seaDogTaskFactory));
        }
    }

    private void addRateLimiter(String cacheName ,CacheRefreshConfig refreshConfig, SeaDogTaskFactory seaDogTaskFactory) {
        // 如果该缓存没有刷新器，或已经绑定了过载保护，那么直接退出
        if (refreshConfig == null || seaDogTaskFactory.getRateLimiters().containsKey(cacheName)) {
            return;
        }

        // 无过载保护也退出
        ScheduleProjectConfig projectConfig = refreshConfig.getProjectConfig();
        if (projectConfig == null) {
            return;
        }

        // 根据保护类型，选择一个过载保护策略
        if (ScheduleConstant.PROJECT_STRATEGY_RANDOM.equals(projectConfig.getStrategy())) {

            seaDogTaskFactory.getRateLimiters().put(cacheName, new ScheduleRandomRateLimiter(projectConfig.getRandomTime()));

        } else if(ScheduleConstant.PROJECT_STRATEGY_REDIS.equals(projectConfig.getStrategy())) {

            seaDogTaskFactory.getRateLimiters().put(cacheName, new ScheduleRedisRateLimiter(redissonClient, refreshConfig));
        }
    }

}