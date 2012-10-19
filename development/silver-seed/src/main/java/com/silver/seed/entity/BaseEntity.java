/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silver.seed.entity;

import java.sql.Timestamp;

/**
 *
 * @author player
 */
public abstract class BaseEntity implements Entity{
    private String operator;
    private String operation;
    private Timestamp timestamp;

    /**
     * @return the operator
     */
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
