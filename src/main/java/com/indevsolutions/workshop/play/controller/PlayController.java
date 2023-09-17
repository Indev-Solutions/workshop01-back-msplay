package com.indevsolutions.workshop.play.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.indevsolutions.workshop.play.domain.Play;
import com.indevsolutions.workshop.play.dto.PlayDTO;
import com.indevsolutions.workshop.play.dto.PlaySummaryDTO;
import com.indevsolutions.workshop.play.service.PlayService;

@RestController
@RequestMapping("/plays")
public class PlayController {

	private static final Long userId = 1L;

	@Autowired
	private PlayService playService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public List<PlaySummaryDTO> findLatestPlays() {
		return playService.findLatestPlays(userId);
	}

	@PostMapping
	public Play  createPlay(@RequestBody PlayDTO playRequest) {
		var play = modelMapper.map(playRequest, Play.class);
		play.setUserId(userId);
		return playService.createPlay(play);
	}
}
