package com.zzw.iCache.core.CacheManager.implement;

import com.zzw.iCache.core.Cache.Cache;
import com.zzw.iCache.core.Cache.implement.CacheWrapperImpl;
import com.zzw.iCache.core.CacheConfig.CacheConfig;
import com.zzw.iCache.core.CacheFactory.CacheFactory;
import com.zzw.iCache.core.CacheFactory.implement.CacheFactoryAdapter;
import com.zzw.iCache.core.CacheFilter.CacheFilter;
import com.zzw.iCache.core.CacheListener.CacheListener;
import com.zzw.iCache.core.CacheManager.CacheManager;
import com.zzw.iCache.core.CacheRefresher.CacheRefresh;
import com.zzw.iCache.core.RealCache.RealCache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhangyang
 * @Data:2024/3/27 17:38
 * @Description:
 */
public class CacheManagerImp implements CacheManager {


    // 缓存工厂
    private CacheFactory cacheFactory =null;

    public CacheManagerImp() {
        this.cacheFactory = new CacheFactoryAdapter();
    }

    /**
     *  真实的缓存对象
     *  Map<缓存名称, 缓存对象>
     */
    private Map<String, RealCache> realCacheMap = new HashMap<>();

    /**
     *  被包装过的缓存对象
     *  里面已经包含了过滤器和监听器
     */
    private Map<String, RealCache> wrapperCacheMap = new HashMap<>();

    /**
     *  对外使用的缓存对象
     */
    private Map<String, Cache> facadeCache = new HashMap<>();

    /**
     * 缓存名称与刷新器的映射关系
     */

    private Map<String, Map<String, CacheRefresh>> refreshMaps = new HashMap<>();

    private Map<String, CacheConfig> cacheConfigMap = new HashMap<>();


    @Override
    public Cache createCache(CacheConfig cacheConfig, List<CacheFilter> cacheFilters, List<CacheListener> listeners) {
        //facadeCache中有直接返回
        if (facadeCache.containsKey(cacheConfig.getName())) {
            return facadeCache.get(cacheConfig.getName());
        }

//        List<CacheFilter> cacheFilterList = new ArrayList<>();
//
//       if(!CollectionUtils.isEmpty(cacheFilters)) {
//           cacheFilterList.addAll(cacheFilters);
//       }
//
//       //todo：将监听器转为 过滤器  监听器是特殊的过滤器
//        if(!CollectionUtils.isEmpty(listeners)) {
//           // cacheFilterList.addAll(new CacheFilter<>());
//        }

        RealCache cache = cacheFactory.getCache(cacheConfig);
        realCacheMap.put(cacheConfig.getName(),cache);

        //todo:用过滤器和拦截器 包一层存入 包装map
        RealCache wrappedCache = buildFitter(cache,cacheConfig.getName());
        wrapperCacheMap.put(cacheConfig.getName(), wrappedCache);

        Cache cacheWrapper = new CacheWrapperImpl(wrappedCache);
        //放入facadeCacheMap中
        facadeCache.put(cacheConfig.getName(),cacheWrapper);

        //放入缓存配置map中
        cacheConfigMap.put(cacheConfig.getName(), cacheConfig);

        //返回对外的包装过的缓存对象
        return cacheWrapper;
    }

    //todo: 构建包装过的缓存对象
    private RealCache buildFitter(RealCache cache, String name) {
        return cache;
    }


    @Override
    public RealCache getRealCache(String cacheName) {
        return realCacheMap.get(cacheName);
    }

    @Override
    public RealCache getCache(String name) {
        return wrapperCacheMap.get(name);
    }

    @Override
    public Cache getFacadeCache(String name) {
        return facadeCache.get(name);
    }

    @Override
    public CacheConfig getCacheConfig(String name) {
        return cacheConfigMap.get(name);
    }
}
