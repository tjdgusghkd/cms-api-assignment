package com.malgn.content.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.malgn.content.dto.ContentCreateRequest;
import com.malgn.content.service.ContentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contents")
@RequiredArgsConstructor
public class ContentController {
	
	private final ContentService contentService;
	
	@PostMapping
	public ResponseEntity<Long> createContent(@Valid @RequestBody ContentCreateRequest request) {
		Long contentId = contentService.createContent(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(contentId);
	}

}
