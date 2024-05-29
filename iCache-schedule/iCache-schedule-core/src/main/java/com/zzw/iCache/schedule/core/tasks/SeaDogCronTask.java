package com.zzw.iCache.schedule.core.tasks;


import com.zzw.iCache.schedule.core.SeaDogSchedule;
import com.zzw.iCache.schedule.core.enums.SeaDogTaskType;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.util.TimeZone;

/**
 * cron 表达式模式
 *
 * @author zhangyang
 * @version $Id: SeaDogCronTask.java,v 0.1 2020年06月15日 21:03 $Exp
 */
public class SeaDogCronTask implements SeaDogTask {

    @Override
    public ScheduledTask addTask(SeaDogSchedule schedule, Runnable runnable, ScheduledTaskRegistrar registrar) {
        CronTask task = new CronTask(runnable, new CronTrigger(schedule.getCron(), TimeZone.getDefault()));
        return registrar.scheduleCronTask(task);
    }

    @Override
    public SeaDogTaskType taskType() {
        return SeaDogSchedule.getCronType();
    }
}

