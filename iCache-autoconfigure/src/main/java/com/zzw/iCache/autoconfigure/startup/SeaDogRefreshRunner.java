package com.zzw.iCache.autoconfigure.startup;

import com.zzw.iCache.schedule.core.maneger.SeaDogScheduleManager;
import com.zzw.iCache.schedule.core.wrapper.CacheRefreshWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * spring-boot启动后，加载刷新一次缓存
 *
 * <p> 启动后默认给所有刷新器执行一次 </p>
 *
 * @author Clark
 * @version $Id: SeaDogRefreshRunner.java,v 0.1 2020年07月17日 9:21 $Exp
 */
@Component
public class SeaDogRefreshRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(SeaDogRefreshRunner.class);

    @Autowired
    private SeaDogScheduleManager seaDogScheduleManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<String, CacheRefreshWrapper> cacheRefreshMap = seaDogScheduleManager.getAllCacheRefresh();
        if(cacheRefreshMap == null || cacheRefreshMap.size() < 0){
            return;
        }

        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        // 椎内存使用情况
        MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();

        // 最大可用内存
        long maxMemorySize = memoryUsage.getMax();
        // 已使用的内存
        long usedMemorySize = memoryUsage.getUsed();

        double used = usedMemorySize * 1.0 / 1024.0 / 1024.0;
        BigDecimal usedBig = new BigDecimal(used);
        double max = maxMemorySize * 1.0 / 1024.0 / 1024.0;
        BigDecimal usedMax = new BigDecimal(max);

        log.info("pre-已使用的内存(JVM):" + new DecimalFormat("#.#").format(usedMemorySize * 1.0 / 1024.0 / 1024.0) + "M");
        log.info("pre-最大可用内存(JVM):" + new DecimalFormat("#.#").format(maxMemorySize * 1.0 / 1024.0 / 1024.0) + "M");
        BigDecimal memoryRate = usedBig.divide(usedMax, 2, BigDecimal.ROUND_HALF_UP);
        log.info("pre-占内存的大小比率:" +memoryRate+"%");

//      //监控内存告警
//        //内存使用率高于80% 同时 告警次数大于10次
//        if(usedBig.compareTo(new BigDecimal(monitorConstant.MONITOR_WARN_RATE_DEFAULT))>=0) {
//            warnValue++;
//            if(warnValue>=monitorConstant.MONITOR_WARN_TIMES_DEFAULT) {
//                log.error("内存使用率过高,请及时清理缓存");
//                //发送到企业微信
//            }
//        }

        cacheRefreshMap.forEach((k, v) -> {

            if(v.isStartup()){
                v.refresh();
            }
            log.info("启动后，执行一次缓存刷新器成功！：{}", k);
        });

    }

}