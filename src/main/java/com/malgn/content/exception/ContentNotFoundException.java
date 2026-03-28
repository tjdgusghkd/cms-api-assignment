package com.malgn.content.exception;

public class ContentNotFoundException extends ContentException {

    public ContentNotFoundException() {
        super("존재하지 않는 콘텐츠입니다.");
    }
}
