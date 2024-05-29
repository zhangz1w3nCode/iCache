package com.zzw.iCache.schedule.core;

import com.zzw.iCache.schedule.core.enums.SeaDogTaskType;

/**
 * schedule注解实现类
 *
 * @author zhangyang
 * @version $Id: SeaDogSchedule.java,v 0.1 2020年06月06日 14:22 $Exp
 */
public class SeaDogSchedule {

    /**
     * cron表达式可以定制化执行任务，但是执行的方式是与fixedDelay相近的，也是会按照上一次方法结束时间开始算起
     */
    private String cron = null;

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

    private SeaDogTaskType taskType;

    /**
     * 默认的构造函数
     */
    public SeaDogSchedule(){}



    /**
     * 根据不同的参数，配置不同的定时调度器
     *
     * @param refreshConfig 本地缓存刷新配置
     * @return SeaDogSchedule
     */
    public static SeaDogSchedule getInstance(CacheRefreshConfig refreshConfig) {
        if (refreshConfig == null) {
            return null;
        }

        if (refreshConfig.getCorn() != null && refreshConfig.getCorn().length() > 0) {
            return cronInstance(refreshConfig.getCorn());
        }

        if (refreshConfig.getFixedDelay() > 0) {
            return fixedDelayInstance(refreshConfig.getFixedDelay(), refreshConfig.getInitialDelay());
        }

        if (refreshConfig.getFixedRate() > 0) {
            return fixedRateInstance(refreshConfig.getFixedRate(), refreshConfig.getInitialDelay());
        }

        return null;
    }


    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
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

    public SeaDogTaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(SeaDogTaskType taskType) {
        this.taskType = taskType;
    }

    public static SeaDogSchedule cronInstance(String cron){
        SeaDogSchedule seaDogSchedule = new SeaDogSchedule();
        seaDogSchedule.cron = cron;
        seaDogSchedule.taskType = getCronType();
        return seaDogSchedule;
    }

    public static SeaDogSchedule fixedDelayInstance(long fixedDelay, long initialDelay){
        SeaDogSchedule seaDogSchedule = new SeaDogSchedule();
        seaDogSchedule.fixedDelay = fixedDelay;
        seaDogSchedule.initialDelay = initialDelay;

        if(seaDogSchedule.initialDelay < 0){
            seaDogSchedule.initialDelay = 0;
        }

        seaDogSchedule.taskType = getFixedDelayType();
        return seaDogSchedule;
    }

    public static SeaDogSchedule fixedRateInstance(long fixedRate, long initialDelay){
        SeaDogSchedule seaDogSchedule = new SeaDogSchedule();
        seaDogSchedule.fixedRate = fixedRate;
        seaDogSchedule.initialDelay = initialDelay;

        if(seaDogSchedule.initialDelay < 0){
            seaDogSchedule.initialDelay = 0;
        }

        seaDogSchedule.taskType = getFixedRateType();
        return seaDogSchedule;
    }

    public static SeaDogTaskType getCronType(){
        return SeaDogTaskType.CRON;
    }

    public static SeaDogTaskType getFixedRateType(){
        return SeaDogTaskType.FIXED_RATE;
    }

    public static SeaDogTaskType getFixedDelayType(){
        return SeaDogTaskType.FIXED_DELAY;
    }

    @Override
    public String toString(){
        if(cron != null && cron.length() > 0){
            return "cron: " + cron;
        }

        if(fixedDelay > 0){
            return "fixedDelay:" + fixedDelay + ", initialDelay:" + initialDelay;
        }

        if(fixedRate > 0){
            return "fixedRate:" + fixedRate + ", initialDelay:" + initialDelay;
        }

        return "";
    }



}
