package com.indevsolutions.workshop.play.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import com.indevsolutions.workshop.play.domain.Play;
import com.indevsolutions.workshop.play.dto.PlayDTO;
import com.indevsolutions.workshop.play.dto.PlaySummaryDTO;
import com.indevsolutions.workshop.play.dto.shared.BetDTO;
import com.indevsolutions.workshop.play.dto.shared.BetOptionDTO;
import com.indevsolutions.workshop.play.service.BetService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayControllerTest {
	
	@Autowired
	private PlayController playController;
	
	@MockBean
    BetService betService;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void index() {
		assertThat(playController).isNotNull();
	}
	
	@Test
	void testFindLatestPlays() {
        when(betService.findBetsByIds(anySet())).thenReturn(List.of());

		var playsResponse = restTemplate.exchange("http://localhost:" + port + "/workshop/plays", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PlaySummaryDTO>>() {
				});
		assertNotNull(playsResponse);

		var plays = playsResponse.getBody();
		assertNotNull(plays);
		assertEquals(0, plays.size());
		
	}
	
	@Test
	void testCreatePlay() {
		
		var bet = new BetDTO();
		bet.setId(1L);
		bet.setLeagueId(1L);
		bet.setMatch("Temp");
		bet.setMatchDate(LocalDateTime.now());
		var option = new BetOptionDTO();
		option.setId(1L);
		bet.setOptions(Set.of(option));
		
        when(betService.findBetsByIds(anySet())).thenReturn(List.of(bet));

        var request = new PlayDTO();
        request.setAmount(new BigDecimal(100));
        request.setBetId(1l);
        request.setChoiceId(1l);
		
		var play = restTemplate.postForObject("http://localhost:" + port + "/workshop/plays", request, Play.class);

		assertNotNull(play);
		
	}

}
