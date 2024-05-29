package com.zzw.iCache.autoconfigure.register;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Bean注册器
 * 将class全类名对应的bean加载到ioc容器中
 * @author zhangyang
 * @version $Id:  BeanRegister.java,v 0.1 2020年06月20日 16:32 $Exp
 */
public class BeanRegister {

    public static void registryIfAbsent(ConfigurableApplicationContext applicationContext, String[] beanNames) {
        if(beanNames == null){
            return;
        }
        for (String beanName : beanNames) {
            boolean b = applicationContext.containsBean(beanName);
            if (!b) {
                Class<?> clazz;
                try {
                    clazz = Class.forName(beanName);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("无法加载bean " + beanName, e);
                }

                registryBean(applicationContext, beanName, clazz);
            }
        }

    }

    public static Object registryIfAbsent(ConfigurableApplicationContext applicationContext, String beanName) {
        if(StringUtils.isBlank(beanName)){
            throw new NullPointerException("beanName不能为空");
        }
        boolean b = applicationContext.containsBean(beanName);
        if (!b) {
            Class<?> clazz;
            try {
                clazz = Class.forName(beanName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("无法加载bean " + beanName, e);
            }

            return registryBean(applicationContext, beanName, clazz);
        }

        return applicationContext.getBean(beanName);
    }

    /**
     * 注册bean到ioc
     *
     * @param applicationContext
     * @param beanName
     * @param clazz
     * @return
     */
    public static Object registryBean(ConfigurableApplicationContext applicationContext, String beanName, Class clazz){
        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) applicationContext.getBeanFactory();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanDefinitionBuilder.getRawBeanDefinition();
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
        return applicationContext.getBean(beanName);
    }
}