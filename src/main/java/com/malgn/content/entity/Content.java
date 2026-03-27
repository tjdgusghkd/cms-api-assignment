package com.malgn.content.entity;

import java.time.LocalDateTime;

import com.malgn.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "CONTENTS")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTENT_ID")
    private Long contentId;

    @Column(name="TITLE",length = 100, nullable = false)
    private String title;

    @Lob
    @Column(name="DESCRIPTION")
    private String description;

    @Column(name = "VIEW_COUNT", nullable = false)
    private Long viewCount = 0L;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY_ID", nullable = false)
    private Member createdBy;

    @Column(name = "LAST_MODIFIED_DATE")
    private LocalDateTime lastModifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LAST_MODIFIED_BY_ID")
    private Member lastModifiedBy;

    // ===== 생성 메서드 =====
    public Content(String title, String description, Member createdBy) {
        this.title = title;
        this.description = description;
        this.createdBy = createdBy;
        this.createdDate = LocalDateTime.now();
        this.viewCount = 0L;
    }

    @PrePersist
    public void prePersist() {
        if (this.createdDate == null) {
            this.createdDate = LocalDateTime.now();
        }
        if (this.viewCount == null) {
            this.viewCount = 0L;
        }
    }
    
    // ===== 수정 메서드 =====
    public void update(String title, String description, Member modifier) {
        this.title = title;
        this.description = description;
        this.lastModifiedBy = modifier;
        this.lastModifiedDate = LocalDateTime.now();
    }

    // ===== 조회수 증가 =====
    public void increaseViewCount() {
        this.viewCount++;
    }
}