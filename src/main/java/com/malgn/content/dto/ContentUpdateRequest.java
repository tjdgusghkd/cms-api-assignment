package com.malgn.content.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ContentUpdateRequest {
	
	@NotBlank(message = "제목은 필수입니다.")
	@Size(max = 100, message = "제목은 100자 이하여야 합니다.")
	private String title;
	
	private String description;
	
	@NotNull(message = "수정자 ID는 필수입니다.")
	private Long memberId;
	
	public String getTilte() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Long getMemberId() {
		return memberId;
	}
}
