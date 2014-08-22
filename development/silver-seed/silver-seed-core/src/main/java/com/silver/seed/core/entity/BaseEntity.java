/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silver.seed.core.entity;

import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @deprecated
 * @author Liaojian
 */
public abstract class BaseEntity<ID  extends Serializable> implements Entity<ID>{

    private String operator;

    private String operation;

    private Timestamp timestamp;

    /**
     * @return the operator
     */
    @Column
    public String getOperator() {
        return operator;
    }

    /**
     * @param operator the operator to set
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * @return the operation
     */
    @Column
    public String getOperation() {
        return operation;
    }

    /**
     * @param operation the operation to set
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * @return the timestamp
     */
    @Column
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    
    
}
