package com.zzw.iCache.core.CacheRefresher;


import com.zzw.iCache.core.Cache.Cache;


/**
 * 缓存管理类
 * @author qiaolin
 * @version $Id:  CacheManager.java,v 0.1 2020年06月03日 16:06 $Exp
 */

public interface CacheRefresh<V> {

    /**
     * 缓存刷新
     * @param cache 待刷新的缓存对象
     */
    void refresh(Cache<V> cache);

}
