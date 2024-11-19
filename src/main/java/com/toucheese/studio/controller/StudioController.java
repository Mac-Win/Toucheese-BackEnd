package com.toucheese.studio.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.toucheese.studio.dto.StudioSearchResponse;
import com.toucheese.studio.service.StudioService;

@RestController
@RequestMapping("/api/v1/studios")
public class StudioController {

	private final StudioService studioService;

	public StudioController(StudioService studioService) {
		this.studioService = studioService;
	}

	@GetMapping("/search")
	public List<StudioSearchResponse> searchStudios(@RequestParam String keyword) {
		return studioService.searchStudios(keyword);
	}
}