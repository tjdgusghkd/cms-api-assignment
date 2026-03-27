package com.malgn.content.service;

import org.springframework.stereotype.Service;

import com.malgn.content.dto.ContentCreateRequest;
import com.malgn.content.entity.Content;
import com.malgn.content.repository.ContentRepository;
import com.malgn.member.entity.Member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContentService {
	
	private final ContentRepository contentRepository;
	
	
	// 콘텐츠 생성
	@Transacional
	public Long createContent(@Valid ContentCreateRequest request) {
		Member member = memberRepository.findById(request.getMemberId())
						.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
		
		Content content =  new Content(
						request.getTitle(),
						request.getDescription(),
						member
		);
		
		return contentRepository.save(content).getContentId();
	}

}
