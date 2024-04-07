package com.zzw.iCache.monitor.dubbo.dubboApi;


import com.zzw.iCache.monitor.dubbo.domain.Constants;
import com.zzw.iCache.monitor.dubbo.domain.Provider;
import com.zzw.iCache.monitor.dubbo.dubboUtils.RegistryServerSync;
import com.zzw.iCache.monitor.dubbo.dubboUtils.SyncUtils;
import org.apache.dubbo.common.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

@Component
public class ProviderService {

    @Autowired
    private RegistryServerSync sync;

    public List<Provider> findByService(String serviceName) {
        return SyncUtils.url2ProviderList(findProviderUrlByService(serviceName));
    }

    public List<String> findServicesByApplication(String application) {
        List<String> ret = new ArrayList<>();

        ConcurrentMap<String, Set<URL>> providerUrls = getRegistryCache().get(Constants.PROVIDERS_CATEGORY);
        if (providerUrls == null || application == null || application.length() == 0) {
            return ret;
        }

        for (Map.Entry<String, Set<URL>> e1 : providerUrls.entrySet()) {
            Set<URL> value = e1.getValue();
            for (URL url : value) {
                if (application.equals(url.getParameter(Constants.APPLICATION))) {
                    ret.add(e1.getKey());
                    break;
                }
            }
        }

        return ret;
    }

    private Set<URL> findProviderUrlByService(String service) {
        Map<String, String> filter = new HashMap<>();
        filter.put(Constants.CATEGORY_KEY, Constants.PROVIDERS_CATEGORY);
        filter.put(SyncUtils.SERVICE_FILTER_KEY, service);

        return SyncUtils.filterFromCategory(getRegistryCache(), filter);
    }

    private ConcurrentMap<String, ConcurrentMap<String, Set<URL>>> getRegistryCache() {
        return sync.getRegistryCache();
    }
}
