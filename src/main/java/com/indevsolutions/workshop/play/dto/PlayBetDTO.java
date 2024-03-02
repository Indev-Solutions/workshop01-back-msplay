package com.indevsolutions.workshop.play.dto;

import java.time.LocalDateTime;

public class PlayBetDTO {

	private String match;
	private Long leagueId;
	private LocalDateTime matchDate;
	private Integer status;
	
	public String getMatch() {
		return match;
	}
	public void setMatch(String match) {
		this.match = match;
	}
	
	public Long getLeagueId() {
		return leagueId;
	}
	public void setLeagueId(Long leagueId) {
		this.leagueId = leagueId;
	}
	public LocalDateTime getMatchDate() {
		return matchDate;
	}
	public void setMatchDate(LocalDateTime matchDate) {
		this.matchDate = matchDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
