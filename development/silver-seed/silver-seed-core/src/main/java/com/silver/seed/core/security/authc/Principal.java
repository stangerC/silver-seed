/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.silver.seed.core.security.authc;

import java.io.Serializable;

/**
 *
 * @author Liaojian
 */
public interface Principal<ID extends Serializable> {
    public ID getId();
}
