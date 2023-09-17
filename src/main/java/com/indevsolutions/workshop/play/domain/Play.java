package com.indevsolutions.workshop.play.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Play {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "bet_id")
	private Long betId;

	@Column(name = "choice_id")
	private Long choiceId;

	@Column(precision = 8, scale = 2)
	private BigDecimal amount;

	@Column(name = "registration_date")
	private LocalDateTime registrationDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBetId() {
		return betId;
	}

	public void setBetId(Long betId) {
		this.betId = betId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Long getChoiceId() {
		return choiceId;
	}

	public void setChoiceId(Long choiceId) {
		this.choiceId = choiceId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
