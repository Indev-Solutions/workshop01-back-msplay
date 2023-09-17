package com.indevsolutions.workshop.play.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import com.indevsolutions.workshop.play.domain.Play;
import com.indevsolutions.workshop.play.dto.shared.BetDTO;
import com.indevsolutions.workshop.play.dto.shared.BetOptionDTO;
import com.indevsolutions.workshop.play.repository.PlayRepository;


@ExtendWith(MockitoExtension.class)
class PlayServiceTest {
	
	@Mock
	public PlayRepository playRepository;

	@Mock
	public BetService betService;
	
	@InjectMocks
	public PlayService playService;

	@BeforeEach
	public void setUp() {
		when(betService.findBetsByIds(anySet())).thenReturn(bet());
		ReflectionTestUtils.setField(playService, "modelMapper", new ModelMapper());
	}
	
	@Test
	void testFindLatesPlays() {
		when(playRepository.findTop5ByUserIdOrderByRegistrationDateDesc(anyLong())).thenReturn(plays(1l));

		var plays = playService.findLatestPlays(1l);
		
		assertNotNull(plays);
		assertEquals(1, plays.size());
	}
	
	@Test
	void testFindLatesPlaysWihtoutResult() {
		when(playRepository.findTop5ByUserIdOrderByRegistrationDateDesc(anyLong())).thenReturn(List.of());

		var plays = playService.findLatestPlays(1l);
		
		assertNotNull(plays);
		assertEquals(0, plays.size());
	}
	
	@Test
	void testCreatePlay() {
		var play = play(1l,1l,1l);
		
		when(playRepository.save(any(Play.class))).thenReturn(play);
		
		var result = playService.createPlay(play);
		
		assertNotNull(result);
	}
	
	@Test
	void testCreatePlayWithWrongChoide() {
		var play = play(1l,1l,2l);
		
	    Exception exception = assertThrows(ResponseStatusException.class, () -> {
			playService.createPlay(play);
	    });

	    String expectedMessage = "The choiceId is not valid.";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	private List<BetDTO> bet() {
		var newBet = new BetDTO();

		var tomorrow = LocalDateTime.now().plusDays(1);
		newBet.setMatchDate(tomorrow);
		newBet.setLeagueId(1l);
		newBet.setId(1l);
		
		var option = new BetOptionDTO();
		option.setId(1l);
		newBet.setOptions(Set.of(option));
		
		return List.of(newBet);
	}
	
	private List<Play> plays(Long userId) {
		var play = play(userId, 1l, 1l);
		return List.of(play);
	}
	
	private Play play(Long userId, Long betId, Long choiceId) {
		var play = new Play();
		play.setId(1l);
		play.setBetId(betId);
		play.setChoiceId(choiceId);
		play.setUserId(userId);
		
		return play;
	}

}
