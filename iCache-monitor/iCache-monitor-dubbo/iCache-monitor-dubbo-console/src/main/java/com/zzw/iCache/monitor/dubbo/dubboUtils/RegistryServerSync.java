package com.zzw.iCache.monitor.dubbo.dubboUtils;


import com.zzw.iCache.monitor.dubbo.domain.Constants;
import com.zzw.iCache.monitor.dubbo.dubboApi.CacheMonitor;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.common.utils.NetUtils;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.registry.NotifyListener;
import org.apache.dubbo.registry.Registry;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Component
public class RegistryServerSync implements InitializingBean, DisposableBean, NotifyListener {

    @Autowired
    private Registry registry;

    private static final Logger logger = LoggerFactory.getLogger(RegistryServerSync.class);

    private static final URL SUBSCRIBE = new URL(Constants.ADMIN_PROTOCOL, NetUtils.getLocalHost(), 0, "",
            Constants.INTERFACE_KEY, CacheMonitor.class.getName(),
            Constants.GROUP_KEY, Constants.ANY_VALUE,
            Constants.VERSION_KEY, Constants.ANY_VALUE,
            Constants.CLASSIFIER_KEY, Constants.ANY_VALUE,
            Constants.CATEGORY_KEY, Constants.PROVIDERS_CATEGORY,
            Constants.ENABLED_KEY, Constants.ANY_VALUE,
            Constants.CHECK_KEY, String.valueOf(false));

    /**
     * ConcurrentMap<category, ConcurrentMap<servicename, Set<URL>>>
     * registryCache
     */
    private final ConcurrentMap<String, ConcurrentMap<String, Set<URL>>> registryCache = new ConcurrentHashMap<>();



    public ConcurrentMap<String, ConcurrentMap<String, Set<URL>>> getRegistryCache() {
        return registryCache;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("SeaDog 启动监听Monitor");
        registry.subscribe(SUBSCRIBE, this);
    }

    @Override
    public void destroy() throws Exception {
        logger.info("SeaDog 关闭监听Monitor");
        registry.unsubscribe(SUBSCRIBE, this);
    }


    @Override
    public void notify(List<URL> urls) {
        if (urls == null || urls.isEmpty()) {
            return;
        }
        // Map<category, Map<servicename, Map<Long, URL>>>
        final Map<String, Map<String, Set<URL>>> categories = new HashMap<>();
        for (URL url : urls) {

            // 如果是空协议，代表某个提供者全部下线了
            if (Constants.EMPTY_PROTOCOL.equalsIgnoreCase(url.getProtocol())) {
                removeRegistryInfo(url);
            } else {
                updateRegistryInfo(url, categories);
            }
        }
        if (categories.size() == 0) {
            return;
        }
        String interfaceName = urls.get(0).getServiceInterface();
        for (Map.Entry<String, Map<String, Set<URL>>> categoryEntry : categories.entrySet()) {

            String category = categoryEntry.getKey();

            // 获取当前分类本地缓存  ConcurrentMap<interface, Set<URL>>
            ConcurrentMap<String, Set<URL>> services = registryCache.get(category);
            if (services == null) {
                services = new ConcurrentHashMap<>();
                registryCache.put(category, services);
            } else {
                // Fix map can not be cleared when service is unregistered: when a unique “group/service:version” service is unregistered,
                // but we still have the same services with different version or group, so empty protocols can not be invoked.


                Set<String> keys = new HashSet<>(services.keySet());
                for (String key : keys) {

                    // 如果当前通知的接口，他的服务不包含在本地更新中，那么应该移除她，他可能是被漏掉的
                    if (Tool.getInterface(key).equals(interfaceName) && !categoryEntry.getValue().entrySet().contains(key)) {
                        services.remove(key);
                    }
                }
            }
            services.putAll(categoryEntry.getValue());
        }
    }


    /**
     * 下线注册信息
     * @param url
     */
    private void removeRegistryInfo(URL url){
        String category = url.getParameter(Constants.CATEGORY_KEY, Constants.PROVIDERS_CATEGORY);
        ConcurrentMap<String, Set<URL>> services = registryCache.get(category);

        if (services != null) {
            String group = url.getParameter(Constants.GROUP_KEY);
            String version = url.getParameter(Constants.VERSION_KEY);
            // NOTE: group and version in empty protocol is *
            if (!Constants.ANY_VALUE.equals(group) && !Constants.ANY_VALUE.equals(version)) {
                //
                services.remove(url.getServiceKey());
            } else {
                // 取消相同interface、相同version、相同组的订阅
                for (Map.Entry<String, Set<URL>> serviceEntry : services.entrySet()) {
                    String service = serviceEntry.getKey();
                    if (Tool.getInterface(service).equals(url.getServiceInterface())
                            && (Constants.ANY_VALUE.equals(group) || StringUtils.isEquals(group, Tool.getGroup(service)))
                            && (Constants.ANY_VALUE.equals(version) || StringUtils.isEquals(version, Tool.getVersion(service)))) {
                        services.remove(service);
                    }
                }
            }
        }
    }


    /**
     *  更新注册信息
     * @param url
     * @param categories
     */
    private void updateRegistryInfo(URL url,  Map<String, Map<String, Set<URL>>> categories){
        String category = url.getParameter(Constants.CATEGORY_KEY, Constants.PROVIDERS_CATEGORY);

        // 分类
        Map<String, Set<URL>> services = categories.get(category);
        if(services == null){
            services = new HashMap<>();
            categories.putIfAbsent(category, services);
        }

        Set<URL> ids = services.get(url.getServiceKey());
        if(ids == null){
            ids = new HashSet<>();
            services.putIfAbsent(url.getServiceKey(), ids);
        }

        ids.add(url);
    }


}




