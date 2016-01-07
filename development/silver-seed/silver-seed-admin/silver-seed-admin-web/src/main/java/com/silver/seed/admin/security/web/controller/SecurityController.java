package com.silver.seed.admin.security.web.controller;

import com.silver.seed.admin.security.entity.InternalUser;
import com.silver.seed.admin.security.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by Liaojian on 2015/12/16.
 */
@Controller
@RequestMapping("/module/security")
public class SecurityController {

    @Resource
    private SecurityService securityService;

    public SecurityService getSecurityService() {
        return securityService;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

}
