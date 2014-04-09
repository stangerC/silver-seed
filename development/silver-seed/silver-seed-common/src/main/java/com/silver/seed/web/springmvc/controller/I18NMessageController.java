package com.silver.seed.web.springmvc.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public abstract class I18NMessageController {	
	
	private MessageSource messageSource;

	public MessageSource getMessageSource() {
		return messageSource;
	}

	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public Locale getLocale() {
		return LocaleContextHolder.getLocale();
	}

	public String getMessage(String code) {
		return messageSource.getMessage(code, null, getLocale());
	}

	public String getMessage(String code, Object[] args) {
		return messageSource.getMessage(code, args, getLocale());
	}
}
