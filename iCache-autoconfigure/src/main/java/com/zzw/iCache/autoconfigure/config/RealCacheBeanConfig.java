//package com.zzw.iCache.autoconfigure.config;
//
//
//
//import com.zzw.iCache.autoconfigure.properties.RealProperties;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 优先加载缓存需要的bean
// * @author qiaolin
// * @version $Id:  SeaDogCacheBeanConfig.java,v 0.1 2020年07月29日 17:11 $Exp
// */
//
//@Configuration
//public class RealCacheBeanConfig {
//
//    @Autowired
//    private RealProperties properties;
//
//    /**
//     * 快照管理器
//     * @return
//     */
//    @Bean
//    @ConditionalOnMissingBean(SnapshotManager.class)
//    public SnapshotManager snapshotManager(){
//        DerbySnapshotManager derbySnapshotManager = new DerbySnapshotManager();
//        if(properties.getSnapshot() != null){
//            derbySnapshotManager.setSaveDays(properties.getSnapshot().getSaveDays());
//        }
//        return derbySnapshotManager;
//    }
//
//    /**
//     * 快照监听器
//     * @param snapshotManager
//     * @return
//     */
//    @Bean
//    public CacheSnapshotListener snapshot(SnapshotManager snapshotManager){
//        return new CacheSnapshotListener(properties.getSnapshot().getExecutor(), snapshotManager);
//    }
//}
//
