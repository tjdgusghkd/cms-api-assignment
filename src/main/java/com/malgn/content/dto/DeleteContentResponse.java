package com.malgn.content.dto;

public class DeleteContentResponse {

    private final Long contentId;
    private final String message;

    public DeleteContentResponse(Long contentId, String message) {
        this.contentId = contentId;
        this.message = message;
    }

    public Long getContentId() {
        return contentId;
    }

    public String getMessage() {
        return message;
    }
}
