package com.malgn.member.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malgn.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	Optional<Member> findByLoginId(String loginId);
	boolean existsByLoginId(String loginId);

}
