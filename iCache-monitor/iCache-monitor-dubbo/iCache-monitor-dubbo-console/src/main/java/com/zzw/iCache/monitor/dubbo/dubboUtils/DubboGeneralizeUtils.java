package com.zzw.iCache.monitor.dubbo.dubboUtils;


import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.model.ApplicationModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * dubbo 泛化调用工具类
 *
 * @author juchengcheng
 * @version : DubboGeneralizeUtils.java,v 0.1 2019年11月09日 10:00
 */
public final class DubboGeneralizeUtils {

    /**
     * 默认超时时长为10s
     */
    private static final Integer DEFAULT_TIMEOUT = 10000;

    /**
     * 默认版本
     */
    private static final String DEFAULT_VERSION = "";

    /**
     * 默认不检查提供者是否存在
     */
    private static final boolean DEFAULT_CHECK = false;

    /**
     * 默认应用名称
     */
    private static final String DEFAULT_APPLICATION_NAME = "Sea-Dog-Monitor-Impl";

    /**
     * 直连url正则表达式规则
     */
    private static final String URL_RULE = "^((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}:(\\d)*$";

    /**
     * 私有化构造器，避免工具类被实例化
     */
    private DubboGeneralizeUtils() {
    }


    /**
     * 获取门面T实例
     *
     * @param facadeClass facadeClass
     * @param url         直连url
     * @param version     版本
     * @param timeout     超时时间
     * @param <T>         门面类
     * @return 门面类实例
     */
    public static <T> T getFacadeImpl(Class<T> facadeClass, String url, String version, Integer timeout, Boolean check) {
        // 校验参数
        paramsVerify(facadeClass, url);
        ReferenceConfig<T> referenceConfig = new ReferenceConfig<>();
        // 设置interfaceClass
        referenceConfig.setInterface(facadeClass);
        // 设置applicationName
        referenceConfig.setApplication(getApplication());
        // 设置版本
        referenceConfig.setVersion(version);
        // 设置直连的url
        referenceConfig.setUrl(url);
        // 设置启动时是否检查提供者存在
        referenceConfig.setCheck(check);
        // 设置超时时间，当时间小于0时，设置为默认值5s
        if (timeout < 0) {
            referenceConfig.setTimeout(DEFAULT_TIMEOUT);
        } else {
            referenceConfig.setTimeout(timeout);
        }
        // 返回门面实例
        return referenceConfig.get();
    }

    /**
     * 获取门面T实例
     *
     * @param facadeClass facadeClass
     * @param url         直连url地址
     * @param <T>         门面类
     * @return 返回门面类实例
     */
    public static <T> T getFacadeImpl(Class<T> facadeClass, String url, String version) {
        return getFacadeImpl(facadeClass, url, version, DEFAULT_TIMEOUT, DEFAULT_CHECK);
    }

    /**
     * 获得applicationConfig实例
     *
     * @return ApplicationConfig实例
     */
    private static ApplicationConfig getApplication() {
        ApplicationConfig config = new ApplicationConfig();
        String name = ApplicationModel.getApplication();
        // 判断applicationName是否为空，为空设置默认值test
        if (StringUtils.isNotBlank(name)) {
            // 设置应用名称
            config.setName(name);
        } else {
            config.setName(DEFAULT_APPLICATION_NAME);
        }
        return config;
    }

    /**
     * 校验url和version参数的格式
     *
     * @param url 直连url
     * @return 校验结果
     */
    private static void paramsVerify(Class facadeClass, String url) {
        if (facadeClass == null) {
            throw new IllegalArgumentException("facadeClass不能为空");
        }
        boolean urlVerifyResult = strVerify(url, URL_RULE);
        if (!urlVerifyResult) {
            throw new IllegalArgumentException("url格式不正确，正确示例： 172.8.7.23:9968");
        }
    }

    /**
     * 校验参数
     *
     * @param str  待校验字符
     * @param rule 正则表达式规则
     * @return 校验结果
     */
    private static boolean strVerify(String str, String rule) {
        Pattern p = Pattern.compile(rule);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}