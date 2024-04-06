package com.zzw.iCache.autoconfigure.config;

import com.zzw.iCache.autoconfigure.processor.RealCacheBeanPostProcessor;
import com.zzw.iCache.autoconfigure.properties.RealProperties;
import com.zzw.iCache.core.CacheManager.CacheManager;
import com.zzw.iCache.core.CacheManager.implement.CacheManagerImp;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;

/**
 * SeaDog 缓存自动配置类
 */

/** 保证优先加载，防止出现找不到缓存对象的问题 */
@Order(-999999)
@Configuration
@DubboComponentScan(basePackages = "com.zzw")
//@AutoConfigureAfter(RealCacheBeanConfig.class)
@Import(RealCacheBeanPostProcessor.class)
@EnableConfigurationProperties({RealProperties.class})
public class RealCacheAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(CacheManager.class)
    public CacheManager cacheManager(){
        return new CacheManagerImp();
    }

}
