package com.indevsolutions.workshop.play.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.indevsolutions.workshop.play.domain.Play;
import com.indevsolutions.workshop.play.dto.PlayDTO;
import com.indevsolutions.workshop.play.dto.PlaySummaryDTO;
import com.indevsolutions.workshop.play.service.PlayService;

@RestController
@RequestMapping("/plays")
public class PlayController {

	private static final Long USER_ID = 1L;	
	private final PlayService playService;	
	private final ModelMapper modelMapper;
	
	public PlayController(PlayService playService, ModelMapper modelMapper) {
		super();
		this.playService = playService;
		this.modelMapper = modelMapper;
	}

	@GetMapping
	public List<PlaySummaryDTO> findLatestPlays() {
		return playService.findLatestPlays(USER_ID);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Play  createPlay(@RequestBody PlayDTO playRequest) {
		var play = modelMapper.map(playRequest, Play.class);
		play.setUserId(USER_ID);
		return playService.createPlay(play);
	}
}
