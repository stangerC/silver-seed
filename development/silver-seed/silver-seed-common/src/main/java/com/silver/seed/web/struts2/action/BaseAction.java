package com.silver.seed.web.struts2.action;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport{
	protected Object getBean(String beanName) {
		return WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext()).getBean(beanName);		
	}		
}
