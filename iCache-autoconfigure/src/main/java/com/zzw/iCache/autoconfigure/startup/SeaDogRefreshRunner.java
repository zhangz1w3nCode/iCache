package com.zzw.iCache.autoconfigure.startup;

import com.zzw.iCache.schedule.core.maneger.SeaDogScheduleManager;
import com.zzw.iCache.schedule.core.wrapper.CacheRefreshWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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

        cacheRefreshMap.forEach((k, v) -> {
            if(v.isStartup()){
                v.refresh();
            }
            log.info("启动后，执行一次缓存刷新器成功！：{}", k);
        });
    }

}