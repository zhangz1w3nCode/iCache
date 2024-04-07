package com.zzw.iCache.monitor.dubbo.dubboUtils;


import com.zzw.iCache.monitor.dubbo.domain.Constants;
import com.zzw.iCache.monitor.dubbo.domain.Provider;
import org.apache.dubbo.common.URL;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

public class SyncUtils {

    public static final String SERVICE_FILTER_KEY = ".service";

    public static final String ADDRESS_FILTER_KEY = ".address";

    public static final String ID_FILTER_KEY = ".id";

    public static final String COLON = ":";

    public static Provider url2Provider(URL url) {

        if (url == null) {
            return null;
        }

        Provider p = new Provider();
        p.setService(url.getServiceKey());
        p.setAddress(url.getAddress());
        p.setApplication(url.getParameter(Constants.APPLICATION_KEY));
        p.setUrl(url.toIdentityString());
        p.setParameters(url.toParameterString());

        p.setDynamic(url.getParameter("dynamic", true));
        p.setEnabled(url.getParameter(Constants.ENABLED_KEY, true));
        p.setWeight(url.getParameter(Constants.WEIGHT_KEY, Constants.DEFAULT_WEIGHT));
        p.setUsername(url.getParameter("owner"));

        return p;
    }

    public static List<Provider> url2ProviderList(Set<URL> urlSet) {
        List<Provider> ret = new ArrayList<>();
        for (URL url : urlSet) {
            ret.add(url2Provider(url));
        }
        return ret;
    }

    // Map<category, Map<servicename, Map<Long, URL>>>
    public static Set<URL> filterFromCategory(Map<String, ConcurrentMap<String, Set<URL>>> urls, Map<String, String> filter) {
        String c = (String) filter.get(Constants.CATEGORY_KEY);
        if (c == null) {
            throw new IllegalArgumentException("no category");
        }

        filter.remove(Constants.CATEGORY_KEY);
        Set<URL> urls1 = filterFromService(urls.get(c), filter);
        return urls1;
    }


    // Map<servicename, Map<Long, URL>>
    public static Set<URL> filterFromService(Map<String, Set<URL>> urls, Map<String, String> filter) {
        Set<URL> ret = new HashSet<>();
        if (urls == null) {
            return ret;
        }

        String s = filter.remove(SERVICE_FILTER_KEY);
        if (s == null) {
            for (Map.Entry<String, Set<URL>> entry : urls.entrySet()) {
                filterFromUrls(entry.getValue(), ret, filter);
            }
        } else {
            Set<URL> map = urls.get(s);
            filterFromUrls(map, ret, filter);
        }

        return ret;
    }

    // Map<Long, URL>
    static void filterFromUrls(Set<URL> from, Set<URL> to, Map<String, String> filter) {
        if (from == null || from.isEmpty()) {
            return;
        }

        for (URL url : from) {

            boolean match = true;
            for (Map.Entry<String, String> e : filter.entrySet()) {
                String key = e.getKey();
                String value = e.getValue();

                if (ADDRESS_FILTER_KEY.equals(key)) {
                    // value is address:port
                    if (value.contains(COLON)) {
                        if (!value.equals(url.getIp() + COLON + url.getPort())) {
                            match = false;
                            break;
                        }
                    } else {  // value is just address
                        if (!value.equals(url.getIp())) {
                            match = false;
                            break;
                        }
                    }
                } else {
                    if (!value.equals(url.getParameter(key))) {
                        match = false;
                        break;
                    }
                }
            }

            if (match) {
                to.add(url);
            }
        }
    }
}
