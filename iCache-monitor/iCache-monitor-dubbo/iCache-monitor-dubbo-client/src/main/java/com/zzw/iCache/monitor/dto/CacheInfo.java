package com.zzw.iCache.monitor.dto;


import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * 返回给web-iCache服务的对象
 */
public class CacheInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 缓存数据
     */
    private Object data;

    /**
     * 缓存写入时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date writeTime;

    /**
     * 缓存最后访问时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date accessTime;



    /**
     * 过期时间(秒为单位):还剩下多少秒过期
     */
    private long expirationSecond;


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Date getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(Date writeTime) {
        this.writeTime = writeTime;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

    public long getExpirationSecond() {
        return expirationSecond;
    }

    public void setExpirationSecond(long expirationSecond) {
        this.expirationSecond = expirationSecond;
    }

    public CacheInfo() {
    }

    public CacheInfo(Object data, Date writeTime, Date accessTime, long expirationSecond) {
        this.data = data;
        this.writeTime = writeTime;
        this.accessTime = accessTime;
        this.expirationSecond = expirationSecond;
    }
}
