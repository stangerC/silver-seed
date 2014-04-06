package com.silver.seed.security.authorization;

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
