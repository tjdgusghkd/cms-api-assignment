package com.malgn.member.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.malgn.content.entity.Content;
import com.malgn.member.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="MEMBERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "MEMBER_ID")
	private Long memberId;
	
	@Column(name = "LOGIN_ID", nullable = false, unique=true, length=50)
	private String loginId;
	
	@Column(name = "PASSWORD", nullable = false, length = 255)
	private String password;
	
	@Column(name="NAME", nullable = false, length = 50)
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(name="ROLE", nullable = false, length = 20)
	private Role role;
	
	@Column(name="CREATED_AT", nullable = false)
	private LocalDateTime createdAt;
	
	@PrePersist
	public void prePersist() {
	
		if(this.createdAt == null) {
			this.createdAt = LocalDateTime.now();
		}
	}
	
	public Member(String loginId, String password, String name, Role role) {
	    this.loginId = loginId;
	    this.password = password;
	    this.name = name;
	    this.role = role;
	}

}
