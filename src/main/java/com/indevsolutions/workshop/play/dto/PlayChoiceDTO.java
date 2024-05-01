package com.indevsolutions.workshop.play.dto;

import java.math.BigDecimal;

public class PlayChoiceDTO {
	
	private String description;
	private BigDecimal rate;
	
	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
