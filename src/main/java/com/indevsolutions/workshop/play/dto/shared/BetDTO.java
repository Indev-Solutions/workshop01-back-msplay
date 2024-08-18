package com.indevsolutions.workshop.play.dto.shared;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class BetDTO {

	private Long id;
	private String match;
	private LocalDateTime matchDate;
	private Long resultId;
	private Set<BetOptionDTO> options;
	private Integer status;
	private BigDecimal minAmount;
	private Long leagueId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<BetOptionDTO> getOptions() {
		return options;
	}

	public void setOptions(Set<BetOptionDTO> options) {
		this.options = options;
	}

	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
	}

	public LocalDateTime getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(LocalDateTime matchDate) {
		this.matchDate = matchDate;
	}

	public Long getResultId() {
		return resultId;
	}

	public void setResultId(Long resultId) {
		this.resultId = resultId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(Long leagueId) {
		this.leagueId = leagueId;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}
	
}
