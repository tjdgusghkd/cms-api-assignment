package com.malgn.content.dto;

import java.time.LocalDateTime;

import com.malgn.content.entity.Content;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContentResponse {

    private Long contentId;
    private String title;
    private String description;
    private Long viewCount;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime lastModifiedDate;
    private String lastModifiedBy;

    public ContentResponse(Content content) {
        this.contentId = content.getContentId();
        this.title = content.getTitle();
        this.description = content.getDescription();
        this.viewCount = content.getViewCount();
        this.createdDate = content.getCreatedDate();
        this.createdBy = content.getCreatedBy().getLoginId();
        this.lastModifiedDate = content.getLastModifiedDate();
        this.lastModifiedBy = content.getLastModifiedBy() != null ? content.getLastModifiedBy().getLoginId() : null;
    }
}