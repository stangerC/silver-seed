package com.silver.seed.admin.security.service;


import com.silver.seed.admin.security.entity.NullUser;
import com.silver.seed.core.security.authc.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Liaojian on 2015/12/16.
 */
@Service("securityService")
public class SecurityService {

    @Resource
    private SecurityService securityService;

    public User login(User user) {
        if(user != null) {
            return user;
        }

        return NullUser.getInstance();
    }


}
