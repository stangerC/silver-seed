package com.silver.seed.core.security.authz;

import com.silver.seed.core.security.authc.Principal;

/**
 *
 * @author Liaojian
 */
public interface Authorization {
    public boolean hasPermission();
    
    public Principal getPrincipal();
    
    public Role getRole();
    
    public Action getAction();
    
    public Resource getResouece();
}
