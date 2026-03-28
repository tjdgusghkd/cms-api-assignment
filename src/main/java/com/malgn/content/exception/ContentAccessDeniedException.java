package com.malgn.content.exception;

public class ContentAccessDeniedException extends ContentException {

    public ContentAccessDeniedException() {
        super("콘텐츠 수정 또는 삭제 권한이 없습니다.");
    }
}
