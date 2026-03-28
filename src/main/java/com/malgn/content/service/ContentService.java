package com.malgn.content.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.malgn.content.dto.ContentCreateRequest;
import com.malgn.content.dto.ContentResponse;
import com.malgn.content.dto.ContentUpdateRequest;
import com.malgn.content.entity.Content;
import com.malgn.content.enums.ContentStatus;
import com.malgn.content.repository.ContentRepository;
import com.malgn.member.dto.LoginResponse;
import com.malgn.member.entity.Member;
import com.malgn.member.enums.Role;
import com.malgn.member.repository.MemberRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContentService {
	
	private final ContentRepository contentRepository;
	private final MemberRepository memberRepository;
	
	// 콘텐츠 생성
	@Transactional
	public Long createContent(@Valid ContentCreateRequest request, Long memberId) {
		Member member = memberRepository.findById(memberId)
						.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
		
		Content content =  new Content(
						request.getTitle(),
						request.getDescription(),
						member
		);
		
		return contentRepository.save(content).getContentId();
	}
	
	// 콘텐츠 삭제
	@Transactional
	public Long deleteContent(Long contentId, Long memberId) {
		Content content = contentRepository.findById(contentId)
							.orElseThrow(()->new IllegalArgumentException("존재하지 않는 콘텐츠입니다."));
		
		Member member = memberRepository.findById(memberId)
							.orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));
		validateAuthority(content, member);
		
		content.delete(member);
		
		return content.getContentId();
	}
	
	// 콘텐츠 리스트 조회
	@Transactional(readOnly=true)
	public Page<ContentResponse> getContentList(Pageable pageable) {
		return contentRepository.findByStatus(ContentStatus.ACTIVE, pageable)
				.map(ContentResponse::new);
	}

	// 콘텐츠 상세 조회
	@Transactional(readOnly=true)
	public ContentResponse getContentDetail(Long contentId, LoginResponse loginMember) {
		Content content = contentRepository.findById(contentId)
							.orElseThrow(()-> new IllegalArgumentException("존재하지 않는 콘텐츠입니다."));
		
		Long writerId = (content.getCreatedBy() != null) ? content.getCreatedBy().getMemberId() : null;
		
		if(content.getStatus() == ContentStatus.DEACTIVE) {
			throw new IllegalArgumentException("삭제된 콘텐츠입니다.");
		}
		
		if (loginMember == null || !loginMember.getMemberId().equals(writerId)) {
		    content.increaseViewCount();
		}
		return new ContentResponse(content);
	}
	
	// 콘텐츠 수정
	@Transactional
	public Long updateContent(Long contentId, @Valid ContentUpdateRequest request, Long memberId) {
		Content content = contentRepository.findById(contentId)
							.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 콘텐츠입니다."));
		
		if(content.getStatus() == ContentStatus.DEACTIVE) {
			throw new IllegalArgumentException("삭제된 콘텐츠는 수정할 수 없습니다.");
		}
		
		Member member = memberRepository.findById(memberId)
				.orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));
		
		validateAuthority(content, member);
		
		content.update(request.getTilte(), request.getDescription(), member);
		return content.getContentId();
	}
	
	
	// 작성자 본인인지 관리자인지 확인
	private void validateAuthority(Content content, Member member) {
	    boolean isWriter = content.getCreatedBy().getMemberId().equals(member.getMemberId());
	    boolean isAdmin = member.getRole() == Role.ROLE_ADMIN;

	    if (!isWriter && !isAdmin) {
	        throw new IllegalArgumentException("삭제 또는 수정 권한이 없습니다.");
	    }
	}
}
