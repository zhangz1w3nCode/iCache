package com.zzw.iCache.monitor.dubbo.domain;

import java.util.Date;

/**
 * 服务提供者
 *
 * @author dubboAdmin
 */
public class Provider extends Entity {

    private static final long serialVersionUID = 5981342400350878171L;

    /**
     * 服务名
     */
    private String service;

    /**
     * 服务地址
     */
    private String url;

    /**
     * 服务参数
     */
    private String parameters;

    /**
     * 服务地址
     */
    private String address;

    /**
     * 服务提供者注册地址
     */
    private String registry;

    /**
     * 动态注册
     */
    private boolean dynamic;

    /**
     * 服务提供者是否启动
     */
    private boolean enabled;

    /**
     * 权重
     */
    private int weight;

    /**
     * 应用名
     */
    private String application;

    /**
     * 操作者用户名
     */
    private String username;

    /**
     * 过期时间
     */
    private Date expired;

    /**
     * 存活时间（毫秒）
     */
    private long alived;


    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegistry() {
        return registry;
    }

    public void setRegistry(String registry) {
        this.registry = registry;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public long getAlived() {
        return alived;
    }

    public void setAlived(long alived) {
        this.alived = alived;
    }
}
