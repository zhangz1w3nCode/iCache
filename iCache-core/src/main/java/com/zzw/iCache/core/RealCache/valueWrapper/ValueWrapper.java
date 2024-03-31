package com.zzw.iCache.core.RealCache.valueWrapper;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zhangyang
 * @Data:2024/3/27 15:03
 * @Description:真正缓存的Value-数据包装类：在原来数据的基础上加上一些缓存信息 方便后续的缓存管理 比如缓存写入时间 缓存最后访问时间等
 */

@Data
public class ValueWrapper<V> implements Serializable {

    /**
     * 缓存的数据
     */
    private V data;

    /**
     * 缓存写入时间
     */
    private long writeTime;

    /**
     * 缓存最后访问时间
     */
    private long accessTime;

    public ValueWrapper(V data) {
        this.data = data;
        this.writeTime = System.currentTimeMillis();
        this.accessTime = this.writeTime;
    }

    //更新最后访问时间
    public void updateAccessTime(){
        this.accessTime = System.currentTimeMillis();
    }

}
