package com.zzw.iCache.schedule.core.wrapper;

public interface CacheRefreshWrapper {

    void refresh();

    String getName();

    boolean isStartup();
}
