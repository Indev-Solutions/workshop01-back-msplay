package com.indevsolutions.workshop.play.service;

public enum Error {

	// @formatter:off
	BET_NOT_VALID("error.bet.notvalid"), 
	CHOICE_NOT_VALID("error.choice.notvalid"), 
	BET_CLOSED("error.bet.closed"),
	BET_NOT_VALID_MIN("error.bet.min");
	// @formatter:on

	private String code;

	private Error(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
