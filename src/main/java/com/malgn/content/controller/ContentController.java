package com.malgn.content.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.malgn.content.dto.ContentCreateRequest;
import com.malgn.content.dto.ContentResponse;
import com.malgn.content.dto.ContentUpdateRequest;
import com.malgn.content.service.ContentService;
import com.malgn.member.dto.LoginResponse;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contents")
@RequiredArgsConstructor
public class ContentController {
	
	private final ContentService contentService;
	
	// 콘텐츠 생성(Create)
	@PostMapping
	public ResponseEntity<Long> createContent(@Valid @RequestBody ContentCreateRequest request, HttpSession session) {
	
		Long memberId = ((LoginResponse)session.getAttribute("LOGIN_MEMBER")).getMemberId();
		Long contentId = contentService.createContent(request, memberId);
		return ResponseEntity.status(HttpStatus.CREATED).body(contentId);
	}
	
	// 콘텐츠 삭제 (Delete)
	@DeleteMapping("/{contentId}")
	public ResponseEntity<Long> deleteContent(@PathVariable("contentId") Long contentId, HttpSession session) {
		Long memberId = ((LoginResponse)session.getAttribute("LOGIN_MEMBER")).getMemberId();
		Long deletedContentId = contentService.deleteContent(contentId, memberId);
		return ResponseEntity.ok(deletedContentId);
	}
	
	// 콘텐츠 목록 조회 (Read)
	@GetMapping
	public ResponseEntity<Page<ContentResponse>> getContentList(
			@PageableDefault(size = 10, direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable 
			) {
			Page<ContentResponse> response = contentService.getContentList(pageable);
			return ResponseEntity.ok(response);
		
	}
	
	// 콘텐츠 상세 조회
	@GetMapping("/{contentId}")
	public ResponseEntity<ContentResponse> getContentDetail(@PathVariable("contentId") Long contentId, HttpSession session) {
		
		LoginResponse loginMember = (LoginResponse)session.getAttribute("LOGIN_MEMBER");
		ContentResponse response = contentService.getContentDetail(contentId, loginMember);
		
		return ResponseEntity.ok(response);
	}
	
	// 콘텐츠 수정
	@PatchMapping("/{contentId}")
	public ResponseEntity<Long> updateContent(
			@PathVariable("contentId") Long contentId,
			@Valid @RequestBody ContentUpdateRequest request,
			HttpSession session
			){
		Long memberId = ((LoginResponse)session.getAttribute("LOGIN_MEMBER")).getMemberId();
		Long updatedContentId = contentService.updateContent(contentId, request, memberId);
		return ResponseEntity.ok(updatedContentId);
	}
	
	
	
}
