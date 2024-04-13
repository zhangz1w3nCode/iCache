package com.zzw.iCache.schedule.core.wrapper;

import com.zzw.iCache.core.Cache.Cache;
import com.zzw.iCache.core.CacheRefresher.CacheRefresh;

/**
 * @author qiaolin
 * @version $Id:  CacheRefreshWrapperImpl.java,v 0.1 2020年06月09日 09:43 $Exp
 */
public class CacheRefreshWrapperImpl implements CacheRefreshWrapper {

    private CacheRefresh cacheRefresh;

    private Cache cache;

    private boolean startup;


    public CacheRefreshWrapperImpl(CacheRefresh cacheRefresh, Cache cache, boolean startup) {
        this.cacheRefresh = cacheRefresh;
        this.cache = cache;
        this.startup = startup;
    }

    @Override
    public void refresh() {
        cacheRefresh.refresh(cache);
    }

    @Override
    public String getName() {
        return cache.getName();
    }

    @Override
    public boolean isStartup() {
        return startup;
    }
}
