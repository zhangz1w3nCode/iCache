package com.zzw.iCache.monitor.dubbo.dubboUtils;

/**
 * Tool
 */
public class Tool {

    /**
     *  获取接口名
     * @param service
     * @return
     */
    public static String getInterface(String service) {
        if (service != null && service.length() > 0) {
            int i = service.indexOf('/');
            if (i >= 0) {
                service = service.substring(i + 1);
            }
            i = service.lastIndexOf(':');
            if (i >= 0) {
                service = service.substring(0, i);
            }
        }
        return service;
    }

    /**
     * 获取组
     * @param service
     * @return
     */
    public static String getGroup(String service) {
        if (service != null && service.length() > 0) {
            int i = service.indexOf('/');
            if (i >= 0) {
                return service.substring(0, i);
            }
        }
        return null;
    }

    /**
     * 获取接口的版本号
     * @param service
     * @return
     */
    public static String getVersion(String service) {
        if (service != null && service.length() > 0) {
            int i = service.lastIndexOf(':');
            if (i >= 0) {
                return service.substring(i + 1);
            }
        }
        return null;
    }
}
