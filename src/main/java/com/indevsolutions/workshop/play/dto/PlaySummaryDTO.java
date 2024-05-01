package com.indevsolutions.workshop.play.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PlaySummaryDTO {

	private Long id;
	private PlayBetDTO bet;
	private PlayChoiceDTO choice;
	private BigDecimal amount;
	private LocalDateTime registrationDate;
	private String result;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PlayBetDTO getBet() {
		return bet;
	}

	public void setBet(PlayBetDTO bet) {
		this.bet = bet;
	}

	public PlayChoiceDTO getChoice() {
		return choice;
	}

	public void setChoice(PlayChoiceDTO choice) {
		this.choice = choice;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

}
