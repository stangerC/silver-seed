package com.silver.seed.common.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Liaojian on 2014/7/15.
 */
@MappedSuperclass
public abstract class AuditableEntity<U, ID extends Serializable> implements Entity<ID>{

    @ManyToOne
    protected U createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate;

    @ManyToOne
    protected U lastModifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastModifiedDate;

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
