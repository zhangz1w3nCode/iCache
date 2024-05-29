package com.zzw.iCache.schedule.core.tasks;


import com.zzw.iCache.schedule.core.SeaDogSchedule;
import com.zzw.iCache.schedule.core.enums.SeaDogTaskType;
import org.springframework.scheduling.config.FixedDelayTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 间隔时间执行 fixedDelay 模式
 *
 * @author zhangyang
 * @version $Id: SeaDogFixedDelayTask.java,v 0.1 2020年06月15日 21:04 $Exp
 */
public class SeaDogFixedDelayTask implements SeaDogTask {

    @Override
    public ScheduledTask addTask(SeaDogSchedule schedule, Runnable runnable, ScheduledTaskRegistrar registrar) {
        FixedDelayTask fixedDelayTask = new FixedDelayTask(runnable, schedule.getFixedDelay(), schedule.getInitialDelay());
        return registrar.scheduleFixedDelayTask(fixedDelayTask);
    }

    @Override
    public SeaDogTaskType taskType() {
        return SeaDogSchedule.getFixedDelayType();
    }
}

