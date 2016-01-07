package com.silver.seed.admin.core.web.controller;

import com.silver.seed.admin.core.web.MessageAndRoute;
import com.silver.seed.admin.security.entity.InternalUser;
import com.silver.seed.admin.security.entity.NullUser;
import com.silver.seed.admin.security.service.SecurityService;
import com.silver.seed.core.security.authc.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Liaojian on 2015/12/8.
 */
@Controller
@RequestMapping(value = "/")
public class AdminController {

    private SecurityService securityService;

    public SecurityService getSecurityService() {
        return securityService;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String layout() {
        return "common/layout";
    }

    @RequestMapping(value = "index")
    public String index() {
        return "common/index";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "common/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public void doLogin(String userName, String password,Model model) {
        User user = new InternalUser(userName, password);
        user = getSecurityService().login(user);

        if(user instanceof NullUser) {
            MessageAndRoute mar = new MessageAndRoute();
            mar.setMessage("用户不存在");
            mar.setMessageCode("userNotExisted");
            mar.setType("error");
            mar.setRouteType("N/A");
            mar.setPath("N/A");
            model.addAttribute("messageAndRoute", model);
        }
    }
}
