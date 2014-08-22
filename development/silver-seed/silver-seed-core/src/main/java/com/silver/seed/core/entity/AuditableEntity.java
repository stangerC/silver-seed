package com.silver.seed.core.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Liaojian on 2014/7/15.
 */
@MappedSuperclass
public abstract class AuditableEntity<U, ID extends Serializable> implements Entity<ID>{

    /*状态，1为正常，9为无效或删除*/
    protected int status;

    @OneToOne
    protected U createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate;

    @OneToOne
    protected U lastModifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastModifiedDate;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public U getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final U createdBy) {

        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {

        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {

        this.createdDate = createdDate;
    }

    public U getLastModifiedBy() {

        return lastModifiedBy;
    }

    public void setLastModifiedBy(final U lastModifiedBy) {

        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {

        return lastModifiedDate;
    }

    public void setLastModifiedDate(final Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
