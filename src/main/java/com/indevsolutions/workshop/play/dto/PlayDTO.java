package com.indevsolutions.workshop.play.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public class PlayDTO {

	@NotNull
	private Long betId;

	@NotNull
	private Long choiceId;

	@NotNull
	private BigDecimal amount;

	public Long getBetId() {
		return betId;
	}

	public void setBetId(Long betId) {
		this.betId = betId;
	}

	public Long getChoiceId() {
		return choiceId;
	}

	public void setChoiceId(Long choiceId) {
		this.choiceId = choiceId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
