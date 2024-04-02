package com.zzw.iCache.autoconfigure.processor;

import com.zzw.iCache.autoconfigure.annocation.iCache;
import com.zzw.iCache.autoconfigure.properties.RealCacheProperties;
import com.zzw.iCache.autoconfigure.properties.RealProperties;
import com.zzw.iCache.core.Cache.*;
import com.zzw.iCache.core.CacheFilter.CacheFilter;
import com.zzw.iCache.core.CacheListener.CacheListener;
import com.zzw.iCache.core.CacheManager.CacheManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * bean后置处理器
 * 保证配置中的对象注册到ioc容器之中
 * <pre>
 *     主要工作如下；
 *     1、根据配置、将缓存的filter、listener注册到IOC容器中
 *     2、注册一个包装的缓存对象，这个缓存对象是一个包装对象，最后我们给它设置一个真正的缓存对象
 */
public class RealCacheBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private RealProperties properties;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 如果初始化的bean中字段包含了SeaDogCache注解，我们需要给他动态生成一个缓存
        Class<?> clazz = bean.getClass();

        for (Field declaredField : clazz.getDeclaredFields()) {
            iCache iCache = declaredField.getAnnotation(iCache.class);

            // 不存在，下一个
            if(iCache == null){
                continue;
            }

            Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(iCache);

            String cacheName = (String) annotationAttributes.get("name");
            if(StringUtils.isEmpty(cacheName)){
                throw new IllegalArgumentException("使用 @iCache 注解必须指定缓存名称 beanName:" + beanName + ", className:" + bean.getClass().getName());
            }

            RealCacheProperties cacheProperties = properties.getCaches().get(cacheName);

            // 如果配置中不包含这个缓存数据，那么给配置动态加一个，并且去注册它的Filter、Listener、缓存包装对象
            if(cacheProperties == null){
                cacheProperties = new RealCacheProperties();
                cacheProperties.setName(cacheName);
                cacheProperties.setType((String) annotationAttributes.get("type"));
                cacheProperties.setFilter((String[]) annotationAttributes.get("filter"));
                cacheProperties.setListener((String[]) annotationAttributes.get("listener"));
                properties.getCaches().put(cacheName, cacheProperties);
            } else {
                if (StringUtils.isEmpty(cacheProperties.getType())) {
                    cacheProperties.setType((String) annotationAttributes.get("type"));
                }
//                // 如果没有配置，使用注解上携带的拦截器
//                if (ArrayUtils.isEmpty(cacheProperties.getFilter())) {
//                    cacheProperties.setFilter((String[]) annotationAttributes.get("filter"));
//                }
//
//                // 如果没有配置，使用注解上携带的监听器
//                if (ArrayUtils.isEmpty(cacheProperties.getListener())) {
//                    cacheProperties.setFilter((String[]) annotationAttributes.get("listener"));
//                }
            }

//            List<CacheFilter> cacheFilters = registryFilterList(cacheProperties.getFilter());
//            List<CacheListener> cacheListeners = registryListenerList(cacheProperties.getListener());
            List<CacheFilter> cacheFilters = new ArrayList<>();
            List<CacheListener> cacheListeners = new ArrayList<>();
            // 创建缓存
            Cache cache = cacheManager.createCache(cacheProperties.cacheConfig(), cacheFilters, cacheListeners);
            // 将缓存包装对象设置到我们的字段上
            declaredField.setAccessible(true);
            try {
                declaredField.set(bean, cache);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("无法初始化缓存对象");
            }

        }
        return bean;
    }

//    private List<CacheFilter> registryFilterList(String[] filters){
//        return Arrays.stream(filters).map(filterName -> (CacheFilter)BeanRegister.registryIfAbsent(applicationContext, filterName)).collect(Collectors.toList());
//    }
//
//    private List<CacheListener> registryListenerList(String[] listeners){
//        return Arrays.stream(listeners).map(filterName -> (CacheListener)BeanRegister.registryIfAbsent(applicationContext, filterName)).collect(Collectors.toList());
//    }

}
