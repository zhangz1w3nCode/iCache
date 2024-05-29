package com.zzw.iCache.schedule.core.tasks;


import com.zzw.iCache.schedule.core.SeaDogSchedule;
import com.zzw.iCache.schedule.core.enums.SeaDogTaskType;
import org.springframework.scheduling.config.FixedRateTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 速率执行 fixedRate 模式
 *
 * @author zhangyang
 * @version $Id: SeaDogFixedRateTask.java,v 0.1 2020年06月15日 21:04 $Exp
 */
public class SeaDogFixedRateTask implements SeaDogTask {

    @Override
    public ScheduledTask addTask(SeaDogSchedule schedule, Runnable runnable, ScheduledTaskRegistrar registrar) {
        FixedRateTask fixedRateTask = new FixedRateTask(runnable, schedule.getFixedRate(), schedule.getInitialDelay());
        return registrar.scheduleFixedRateTask(fixedRateTask);
    }

    @Override
    public SeaDogTaskType taskType() {
        return SeaDogSchedule.getFixedRateType();
    }
}

