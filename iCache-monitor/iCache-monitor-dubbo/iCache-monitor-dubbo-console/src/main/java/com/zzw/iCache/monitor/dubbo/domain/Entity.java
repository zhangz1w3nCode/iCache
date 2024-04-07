package com.zzw.iCache.monitor.dubbo.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Entity
 */

public abstract class Entity implements Serializable {

    private static final long serialVersionUID = -3031128781434583143L;

    private List<Long> ids;

    private Long id;

    private String hash;

    private Date created;

    private Date modified;

    private Date now;

    private String operator;

    private String operatorAddress;

    private boolean miss;

    public Entity() {
    }

    public void setOperator(String operator) {
        if (operator != null && operator.length() > 200) {
            operator = operator.substring(0, 200);
        }
        this.operator = operator;
    }


    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }

    public String getOperator() {
        return operator;
    }

    public String getOperatorAddress() {
        return operatorAddress;
    }

    public void setOperatorAddress(String operatorAddress) {
        this.operatorAddress = operatorAddress;
    }

    public boolean isMiss() {
        return miss;
    }

    public void setMiss(boolean miss) {
        this.miss = miss;
    }
}
