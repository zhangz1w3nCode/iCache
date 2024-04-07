package com.zzw.iCache.monitor.dubbo.config;

import com.zzw.iCache.monitor.dubbo.domain.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.RegistryFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.zzw")
public class ConfigCenter {

    @Value("${" + Constants.REGISTRY_ADDRESS + ":}")
    private String address;

    @Bean
    public Registry getRegistry() {
        Registry registry = null;
        if (StringUtils.isBlank(address)) {
            throw new RuntimeException();
        }
        // 获取注册工厂
        RegistryFactory registryFactory = ExtensionLoader.getExtensionLoader(RegistryFactory.class).getAdaptiveExtension();
        registry = registryFactory.getRegistry(URL.valueOf(address));
        return registry;
    }

}

