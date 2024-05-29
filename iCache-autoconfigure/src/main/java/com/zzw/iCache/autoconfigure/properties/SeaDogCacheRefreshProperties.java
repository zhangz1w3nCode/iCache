package com.zzw.iCache.autoconfigure.properties;

import com.zzw.iCache.schedule.core.CacheRefreshConfig;

/**
 * schedule 刷新配置
 *
 * @author zhangyang
 * @version $Id:  SeaDogCacheRefreshProperties.java,v 0.1 2020年06月06日 14:07 $Exp
 */

public class SeaDogCacheRefreshProperties {

    private String beanName = null;

    private String corn = "";

    private long fixedDelay = 0;

    private long fixedRate = 0;

    private long initialDelay = 0;

    /**
     * 程序启动后，是否执行刷新器
     */
    private boolean startup = false;

    /**
     * 过载保护配置
     */
    private ScheduleProjectProperties project;

    public CacheRefreshConfig refreshConfig(){
        CacheRefreshConfig refreshConfig = new CacheRefreshConfig();
        refreshConfig.setBeanName(beanName);
        refreshConfig.setCorn(corn);
        refreshConfig.setFixedDelay(fixedDelay);
        refreshConfig.setFixedRate(fixedRate);
        refreshConfig.setInitialDelay(initialDelay);
        refreshConfig.setStartup(startup);

        if (project != null) {
            // 过载保护
            refreshConfig.setProjectConfig(project.projectConfig());
        }
        return refreshConfig;
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

    public ScheduleProjectProperties getProject() {
        return project;
    }

    public void setProject(ScheduleProjectProperties project) {
        this.project = project;
    }
}
