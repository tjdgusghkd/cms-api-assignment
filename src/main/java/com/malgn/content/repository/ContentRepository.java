package com.malgn.content.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.malgn.content.entity.Content;
import com.malgn.content.enums.ContentStatus;

public interface ContentRepository extends JpaRepository<Content, Long>{
	@EntityGraph(attributePaths = {"createdBy", "lastModifiedBy"})
	Page<Content> findByStatus(ContentStatus status, Pageable pageable);
	
	@EntityGraph(attributePaths = {"createdBy", "lastModifiedBy"})
	Optional<Content> findById(Long conetentId);
}
