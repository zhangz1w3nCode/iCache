package com.zzw.iCache.schedule.core.tasks;


import com.zzw.iCache.schedule.core.SeaDogSchedule;
import com.zzw.iCache.schedule.core.enums.SeaDogTaskType;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * @author Clark
 * @version $Id: SeaDogTask.java,v 0.1 2020年06月15日 21:06 $Exp
 */
public interface SeaDogTask {

    ScheduledTask addTask(SeaDogSchedule schedule, Runnable runnable, ScheduledTaskRegistrar registrar);

    SeaDogTaskType taskType();

}
