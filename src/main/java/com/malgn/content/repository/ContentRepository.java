package com.malgn.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malgn.content.entity.Content;

public interface ContentRepository extends JpaRepository<Content, Long>{
}
