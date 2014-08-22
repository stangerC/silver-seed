package com.silver.seed.core.web.springmvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;

public class HierarchicalMessageController  extends I18NMessageController{
	public static final String MESSAGE_ERRORS = "message.errors";	
	public static final String MESSAGE_WARNINGS = "message.warnings";
	public static final String MESSAGE_INFOS = "message.infos";
	public static final String MESSAGE_SUCCESSES = "message.successes";
	
	@SuppressWarnings("unchecked")
	protected void addMessage(String code, Model model, String attributeName) {
		if(model.containsAttribute(attributeName)) {
			((Map<String, String>)model.asMap().get(attributeName)).put(code, getMessage(code));
		} else {
			Map<String, String> messages = new HashMap<String, String>();
			messages.put(code, getMessage(code));
			model.addAttribute(attributeName, messages);			
		}
	}
		
	public void addError(String code, Model model) {		
		addMessage(code, model, MESSAGE_ERRORS);		
	}
	
	public void addWarning(String code, Model model) {
		addMessage(code, model, MESSAGE_WARNINGS);
	}
	
	public void addInfo(String code, Model model) {
		addMessage(code, model, MESSAGE_INFOS);		
	}
	
	public void addSuccess(String code, Model model) {
		addMessage(code, model, MESSAGE_SUCCESSES);		
	}
}