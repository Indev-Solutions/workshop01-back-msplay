package com.indevsolutions.workshop.play.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.indevsolutions.workshop.play.dto.shared.BetDTO;

@Service
public class BetService {

	private final RestTemplate restTemplate;

	@Value("${services.bet.url: http://localhost:8080/workshop/bets}")
	private String betServiceUrl;

	public BetService(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	public List<BetDTO> findBetsByIds(Set<Long> ids) {
		var concatenatedIds = ids.stream().map(Object::toString).collect(Collectors.joining(","));
		var response = restTemplate.exchange(betServiceUrl + "?id={id}", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<BetDTO>>() {
				}, concatenatedIds);

		return response.getBody();
	}
}
