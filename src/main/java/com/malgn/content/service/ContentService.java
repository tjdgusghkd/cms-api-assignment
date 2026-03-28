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
import com.malgn.content.exception.ContentAccessDeniedException;
import com.malgn.content.exception.ContentDeletedException;
import com.malgn.content.exception.ContentNotFoundException;
import com.malgn.content.repository.ContentRepository;
import com.malgn.member.entity.Member;
import com.malgn.member.enums.Role;
import com.malgn.member.repository.MemberRepository;
import com.malgn.member.security.LoginMemberPrincipal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createContent(@Valid ContentCreateRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Content content = new Content(
                request.getTitle(),
                request.getDescription(),
                member
        );

        return contentRepository.save(content).getContentId();
    }

    @Transactional
    public Long deleteContent(Long contentId, Long memberId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(ContentNotFoundException::new);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        validateAuthority(content, member);
        content.delete(member);

        return content.getContentId();
    }

    @Transactional(readOnly = true)
    public Page<ContentResponse> getContentList(Pageable pageable) {
        return contentRepository.findByStatus(ContentStatus.ACTIVE, pageable)
                .map(ContentResponse::new);
    }

    @Transactional
    public ContentResponse getContentDetail(Long contentId, LoginMemberPrincipal loginMember) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(ContentNotFoundException::new);

        if (content.getStatus() == ContentStatus.DEACTIVE) {
            throw new ContentDeletedException();
        }

        Long writerId = content.getCreatedBy() != null ? content.getCreatedBy().getMemberId() : null;

        if (loginMember == null || !loginMember.getMemberId().equals(writerId)) {
            content.increaseViewCount();
        }

        return new ContentResponse(content);
    }

    @Transactional
    public Long updateContent(Long contentId, @Valid ContentUpdateRequest request, Long memberId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(ContentNotFoundException::new);

        if (content.getStatus() == ContentStatus.DEACTIVE) {
            throw new ContentDeletedException();
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        validateAuthority(content, member);
        content.update(request.getTitle(), request.getDescription(), member);

        return content.getContentId();
    }

    private void validateAuthority(Content content, Member member) {
        boolean isWriter = content.getCreatedBy().getMemberId().equals(member.getMemberId());
        boolean isAdmin = member.getRole() == Role.ROLE_ADMIN;

        if (!isWriter && !isAdmin) {
            throw new ContentAccessDeniedException();
        }
    }
}
