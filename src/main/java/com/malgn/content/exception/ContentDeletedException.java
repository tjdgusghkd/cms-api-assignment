package com.malgn.content.exception;

public class ContentDeletedException extends ContentException {

    public ContentDeletedException() {
        super("삭제된 콘텐츠입니다.");
    }
}
