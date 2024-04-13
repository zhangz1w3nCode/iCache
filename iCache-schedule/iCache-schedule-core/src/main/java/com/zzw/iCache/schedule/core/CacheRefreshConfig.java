package com.zzw.iCache.schedule.core;

import com.zzw.iCache.schedule.core.protect.ScheduleProjectConfig;

/**
 * 缓存刷新配置类
 *
 * @author qiaolin
 * @version $Id:  CacheRefreshConfig.java,v 0.1 2020年06月18日 11:13 $Exp
 */
public class CacheRefreshConfig {

    /**
     * 缓存名
     */
    private String cacheName;

    /**
     * 实现刷新接口bean name
     */
    private String beanName = null;

    /**
     * cron表达式可以定制化执行任务，但是执行的方式是与fixedDelay相近的，也是会按照上一次方法结束时间开始算起
     */
    private String corn = null;

    /**
     * fixedDelay控制方法执行的间隔时间，是以上一次方法执行完开始算起，如上一次方法执行阻塞住了，那么直到上一次执行完，并间隔给定的时间后，执行下一次
     */
    private long fixedDelay = 0;

    /**
     * fixedRate是按照一定的速率执行，是从上一次方法执行开始的时间算起，如果上一次方法阻塞住了，下一次也是不会执行，但是在阻塞这段时间内累计应该执行的次数，
     * 当不再阻塞时，一下子把这些全部执行掉，而后再按照固定速率继续执行
     */
    private long fixedRate = 0;

    /**
     * 如： @Scheduled(initialDelay = 10000,fixedRate = 15000
     * 这个定时器就是在上一个的基础上加了一个initialDelay = 10000 意思就是在容器启动后,延迟10秒后再执行一次定时器,以后每15秒再执行一次该定时器
     */
    private long initialDelay = 0;

    /**
     * 程序启动后，是否去执行一次刷新器
     */
    private boolean startup = false;

    /**
     * 过载保护配置
     */
    private ScheduleProjectConfig projectConfig;

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getCorn() {
        return corn;
    }

    public void setCorn(String corn) {
        this.corn = corn;
    }

    public long getFixedDelay() {
        return fixedDelay;
    }

    public void setFixedDelay(long fixedDelay) {
        this.fixedDelay = fixedDelay;
    }

    public long getFixedRate() {
        return fixedRate;
    }

    public void setFixedRate(long fixedRate) {
        this.fixedRate = fixedRate;
    }

    public long getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(long initialDelay) {
        this.initialDelay = initialDelay;
    }

    public boolean isStartup() {
        return startup;
    }

    public void setStartup(boolean startup) {
        this.startup = startup;
    }

    public ScheduleProjectConfig getProjectConfig() {
        return projectConfig;
    }

    public void setProjectConfig(ScheduleProjectConfig projectConfig) {
        this.projectConfig = projectConfig;
    }
}
