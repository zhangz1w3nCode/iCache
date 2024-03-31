package com.zzw.iCache.core.CacheFactory.implement;

import com.zzw.iCache.core.CacheConfig.CacheConfig;
import com.zzw.iCache.core.CacheConstant.CacheConstant;
import com.zzw.iCache.core.CacheFactory.CacheFactory;
import com.zzw.iCache.core.RealCache.RealCache;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @Author: zhangyang
 * @Data:2024/3/27 16:31
 * @Description:
 *缓存工厂适配器它主要使用了SPI来加载其他的缓存工厂对象，
 *使使用者能够扩展缓存框架如 Ehcache、MemCache、redis
 *它并不局限于本地缓存框架...
 */

public class CacheFactoryAdapter implements CacheFactory, CacheConstant {

    public static void main(String[] args) {
        CacheFactory cacheFactory = new CacheFactoryAdapter();
        System.out.println(FACTORIES);
    }

    public static final List<CacheFactory> FACTORIES = new ArrayList<>();

    static {
        // 类加载时就去加载所有的缓存工厂 通过spi机制 拿到meta info的信息 去创建对象
        //最后，在使用SPI加载缓存工厂对象的地方，可以使用ServiceLoader类来加载所有注册的服务提供者
        ServiceLoader<CacheFactory> load = ServiceLoader.load(CacheFactory.class);
        for (CacheFactory cacheFactory : load) {
            FACTORIES.add(cacheFactory);
        }

    }

    @Override
    public boolean support(String cacheType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RealCache getCache(CacheConfig cacheConfig) {
        //遍历所有的缓存工厂 找到支持的缓存工厂 创建缓存实例
        for (CacheFactory cacheFactory : FACTORIES) {
            boolean isSupport = cacheFactory.support(cacheConfig.getCacheType());
            if (isSupport) {
                return cacheFactory.getCache(cacheConfig);
            }
        }
        throw new RuntimeException("无法创建缓存实例");
    }
}
