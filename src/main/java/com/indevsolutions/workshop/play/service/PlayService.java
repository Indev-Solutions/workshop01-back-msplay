package com.indevsolutions.workshop.play.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.indevsolutions.workshop.play.domain.Play;
import com.indevsolutions.workshop.play.dto.PlayBetDTO;
import com.indevsolutions.workshop.play.dto.PlayChoiceDTO;
import com.indevsolutions.workshop.play.dto.PlaySummaryDTO;
import com.indevsolutions.workshop.play.dto.shared.BetDTO;
import com.indevsolutions.workshop.play.dto.shared.BetOptionDTO;
import com.indevsolutions.workshop.play.repository.PlayRepository;

@Service
public class PlayService {

	private static final long MINUTES_BEFORE_CLOSE_BET = 10;

	@Autowired
	private PlayRepository playRepository;

	@Autowired
	private BetService betService;

	@Autowired
	private ModelMapper modelMapper;

	public List<PlaySummaryDTO> findLatestPlays(Long userId) {
		var plays = playRepository.findTop5ByUserIdOrderByRegistrationDateDesc(userId);
		var betIds = plays.stream().map(Play::getBetId).collect(Collectors.toSet());
		var bets = betService.findBetsByIds(betIds).stream().collect(Collectors.toMap(BetDTO::getId, b -> b));

		return plays.stream().map(p -> {
			var play = modelMapper.map(p, PlaySummaryDTO.class);

			var bet = bets.get(p.getBetId());
			if (bet != null) {
				var playBet = modelMapper.map(bet, PlayBetDTO.class);
				play.setBet(playBet);

				getBetOption(bet, p.getChoiceId()).map(o -> modelMapper.map(o, PlayChoiceDTO.class))
						.ifPresent(play::setChoice);

				getBetOption(bet, bet.getResultId()).map(BetOptionDTO::getDescription)
						.ifPresent(play::setResult);
			}

			return play;
		}).toList();
	}

	private Optional<BetOptionDTO> getBetOption(BetDTO bet, Long id) {
		if (id == null) {
			return Optional.empty();
		}

		return bet.getOptions().stream().filter(o -> Objects.equals(o.getId(), id)).findFirst();
	}

	public Play createPlay(Play play) {
		var bet = betService.findBetsByIds(Set.of(play.getBetId()));

		if (bet == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The betId is not valid.");
		}

		var isOptionValid = bet.stream().map(BetDTO::getOptions).flatMap(Set::stream)
				.anyMatch(o -> o.getId().equals(play.getChoiceId()));
		if (!isOptionValid) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The choiceId is not valid.");
		}

		var now = LocalDateTime.now();
		var duration = Duration.between(now, bet.get(0).getMatchDate());

		if (duration.toMinutes() <= MINUTES_BEFORE_CLOSE_BET) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The bet is closed.");
		}

		play.setRegistrationDate(now);
		return playRepository.save(play);
	}
}
