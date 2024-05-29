package com.zzw.iCache.schedule.core.annotation;


import java.lang.annotation.*;

/**
 * 通过注解方式绑定 schedule任务
 *
 * @author zhangyang
 * @version $Id: SeaDogSchedule.java,v 0.1 2020年06月18日 15:13 $Exp
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SeaDogSchedule {

    /**
     * 缓存名称，必填
     *
     * @return cacheName
     */
    String cacheName();

    /**
     * 如果不配置，则默认为refresh的Class Name
     *
     * @return beanName
     */
    String beanName() default "";

    String cron() default "";

    long fixedDelay() default 0;

    long fixedRate() default 0;

    long initialDelay() default 0;

    /**
     * 程序启动后，是否执行一次刷新器
     *
     * @return boolean
     */
    boolean startup() default false;
}
