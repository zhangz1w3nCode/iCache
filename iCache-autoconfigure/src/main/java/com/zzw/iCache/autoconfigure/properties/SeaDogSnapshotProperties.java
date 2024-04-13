package com.zzw.iCache.autoconfigure.properties;

/**
 * 缓存快照配置
 * @author qiaolin
 * @version $Id:  SnapshotProperties.java,v 0.1 2020年07月29日 17:19 $Exp
 */
public class SeaDogSnapshotProperties {

    /**
     *  快照处理线程数大小
     */
    private int executor = 1;

    /**
     * 快照保存的天数，有如下作用
     * 1、超出天数将会被清理
     * 2、查询快照能查询的天数
     */
    private int saveDays = 3;


    public void setExecutor(int executor) {
        this.executor = executor;
    }

    public int getExecutor() {
        return executor;
    }

    public int getSaveDays() {
        return saveDays;
    }

    public void setSaveDays(int saveDays) {
        this.saveDays = saveDays;
    }
}
