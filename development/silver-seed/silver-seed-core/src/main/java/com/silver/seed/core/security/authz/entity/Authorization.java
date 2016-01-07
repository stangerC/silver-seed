package com.silver.seed.core.security.authz.entity;

import com.silver.seed.core.security.authc.entity.Principal;

/**
 *
 * @author Liaojian
 */
public interface Authorization {
    public boolean hasPermission();
    
    public Principal getPrincipal();
    
    public Action getAction();
    
    public Resource getResouece();
}
