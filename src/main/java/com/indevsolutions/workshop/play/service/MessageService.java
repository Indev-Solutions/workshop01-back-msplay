package com.indevsolutions.workshop.play.service;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

@Service
public class MessageService implements MessageSource {

	private final MessageSource messageSource;

	public MessageService(MessageSource messageSource) {
		super();
		this.messageSource = messageSource;
	}

	@Override
	public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		return messageSource.getMessage(code, args, defaultMessage, locale);
	}

	@Override
	public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException { //
		return messageSource.getMessage(code, args, locale);
	}

	@Override
	public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
		return messageSource.getMessage(resolvable, locale);
	}

	public String getMessage(Error error) {
		return messageSource.getMessage(error.getCode(), null, Locale.US);
	}

	public String getMessage(Error error, Object[] args) {
		return messageSource.getMessage(error.getCode(), args, Locale.US);
	}
}
