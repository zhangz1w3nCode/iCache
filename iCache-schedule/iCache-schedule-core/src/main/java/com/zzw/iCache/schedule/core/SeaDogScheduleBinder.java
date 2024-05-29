package com.zzw.iCache.schedule.core;


import com.zzw.iCache.core.Cache.Cache;
import com.zzw.iCache.core.CacheManager.CacheManager;
import com.zzw.iCache.core.CacheRefresher.CacheRefresh;
import com.zzw.iCache.schedule.core.tasks.SeaDogTaskFactory;
import com.zzw.iCache.schedule.core.wrapper.CacheRefreshWrapper;
import com.zzw.iCache.schedule.core.wrapper.CacheRefreshWrapperImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态绑定 schedule
 *
 * @author zhangyang
 * @version $Id: SeaDogScheduleBinder.java,v 0.1 2020年06月08日 19:33 $Exp
 */
public class SeaDogScheduleBinder {
    private static final Logger log = LoggerFactory.getLogger(SeaDogScheduleBinder.class);

    private ConfigurableApplicationContext applicationContext;

    private SeaDogTaskFactory seaDogTaskFactory;

    private CacheManager cacheManager;

    public SeaDogScheduleBinder (ConfigurableApplicationContext applicationContext, SeaDogTaskFactory seaDogTaskFactory,
                                 CacheManager cacheManager) {
        this.applicationContext = applicationContext;
        this.seaDogTaskFactory = seaDogTaskFactory;
        this.cacheManager = cacheManager;
    }


    private ScheduledTaskRegistrar scheduledTaskRegistrar;

    /**
     * 存储配置的调度器任务，到时可用于停止、更改
     *
     * [K-CacheName, [K-RefreshName, V-ScheduledTask]]
     */
    public Map<String, Map<String, ScheduledTask>> scheduledFutures = new HashMap<>();


    /**
     * 存储配置的调度器任务-刷新实现类
     */
    private Map<String, CacheRefreshWrapper> cacheRefreshMap = new HashMap<>(16);



    /**
     * 单个缓存绑定定时调度
     *
     * @param cacheName 缓存名
     * @param refreshConfig 缓存刷新配置
     */
    public void bind(String cacheName, CacheRefreshConfig refreshConfig) {
        if(refreshConfig == null){
            return;
        }

        refreshConfig.setCacheName(cacheName);

        // 配置中刷新器的bean名称，定时器将调用此函数
        String refreshBeanName = refreshConfig.getBeanName();

        // 定时调度器
        SeaDogSchedule schedule = SeaDogSchedule.getInstance(refreshConfig);

        // 如果没有配置刷新实例或没有配置表达式之类的，将丢弃
        if (StringUtils.isEmpty(refreshBeanName) || schedule == null) {
            return;
        }
        if (!applicationContext.containsBean(refreshBeanName)) {
            return;
        }

        // 根据beanName找到刷新实现类
        CacheRefresh cacheRefresh = (CacheRefresh)applicationContext.getBean(refreshConfig.getBeanName());
        if (this.cacheRefreshMap.containsKey(cacheRefresh.getClass().getName())) {
            // 同一个刷新实现类不能与其他缓存共用
            throw new RuntimeException("[SeaDog] add dynamic schedule exception, name:"+cacheRefresh.getClass().getName()+" already exists");
        }

        Cache cache = cacheManager.getFacadeCache(cacheName);
        CacheRefreshWrapper cacheRefreshWrapper = new CacheRefreshWrapperImpl(cacheRefresh, cache, refreshConfig.isStartup());

        // 注册 schedule task
        ScheduledTask scheduledTask = seaDogTaskFactory.addTask(schedule, refreshConfig, cacheRefreshWrapper, scheduledTaskRegistrar);

        // 存起来，用于以后管理；停止、修改等操作
        Map<String, ScheduledTask> tasks = scheduledFutures.get(cacheName);
        if(tasks == null || tasks.size() < 1){
            tasks = new HashMap<>(2);
        }
        tasks.put(refreshBeanName, scheduledTask);
        scheduledFutures.put(cacheName, tasks);

        // 存入缓存管理器中
        this.cacheManager.registryRefresh(cacheName, refreshConfig.getBeanName(), cacheRefresh);

        this.cacheRefreshMap.put(cacheRefresh.getClass().getName(), cacheRefreshWrapper);
        log.info("[SeaDog] dynamic schedule cache:{} refresh:{} info:{}", cacheName, refreshBeanName, schedule.toString());
    }

//  @PostConstruct
//  public void init() throws Exception {
//    Map<String, SeaDogCacheProperties> caches = seaDogProperties.getCaches();
//
//    if(CollectionUtils.isEmpty(caches)){
//      return;
//    }
//
//    Iterator<Entry<String, SeaDogCacheProperties>> iterator = caches.entrySet().iterator();
//
//    while (iterator.hasNext()){
//      Entry<String, SeaDogCacheProperties> entry = iterator.next();
//
//      SeaDogCacheProperties cacheProperties = entry.getValue();
//
//      String refreshBeanName = cacheProperties.getRefresh().getBeanName();
//
//      // 定时调度器
//      SeaDogSchedule schedule = SeaDogSchedule.getInstance(cacheProperties);
//
//      // 如果没有配置刷新实例或没有配置表达式之类的，将丢弃
//      if(StringUtils.isEmpty(refreshBeanName) || schedule == null){
//        return;
//      }
//      if(!applicationContext.containsBean(refreshBeanName)){
//        return;
//      }
//
//
//      // 根据beanName找到刷新实现类
//      CacheRefresh cacheRefreshMap = (CacheRefresh)applicationContext.getBean(cacheProperties.getRefresh().getBeanName());
//      Cache bean = applicationContext.getBean(entry.getKey(), Cache.class);
//      CacheRefreshWrapper cacheRefreshWrapper = new CacheRefreshWrapperImpl(cacheRefreshMap, bean);
//
//
//      // 拿到需要定时调度的方法
//      Method refreshMethod = CacheRefreshWrapper.class.getDeclaredMethod("refresh");
//
//      // 确保能够访问
//      boolean isAccessible = refreshMethod.isAccessible();
//      if(!isAccessible){
//        refreshMethod.setAccessible(true);
//      }
//
//      Method processScheduled = ScheduledAnnotationBeanPostProcessor.class.getDeclaredMethod(
//          "processScheduled", Scheduled.class, Method.class, Object.class);
//      processScheduled.setAccessible(true);
//
//      // 加入到spring调度器中
//      processScheduled.invoke(scheduledAnnotationBeanPostProcessor, schedule, refreshMethod, cacheRefreshWrapper);
//
//      // 将定时调度器缓存起来
//      scheduledAnnotationBeanPostProcessor.getScheduledTasks().forEach(scheduledTask -> scheduledFutures.putIfAbsent(entry.getKey(), scheduledTask));
//
//      log.info("[SeaDog] Dynamic Schedule name:{} info:{}", entry.getKey(), schedule.toString());
//    }
//
//  }

    public ScheduledTaskRegistrar getScheduledTaskRegistrar() {
        return scheduledTaskRegistrar;
    }

    public void setScheduledTaskRegistrar(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        this.scheduledTaskRegistrar = scheduledTaskRegistrar;
    }

    public Map<String, Map<String, ScheduledTask>> getScheduledFutures() {
        return scheduledFutures;
    }
    public Map<String, CacheRefreshWrapper> getCacheRefreshMap() {
        return cacheRefreshMap;
    }
}
