/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.silver.seed.core.security.authz.entity;

/**
 *
 * @author Liaojian
 */
public interface Role {
    public String getName();

    public Iterable<Authorization> getAuthorization();
}
