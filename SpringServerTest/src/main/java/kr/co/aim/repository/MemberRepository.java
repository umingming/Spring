package kr.co.aim.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.aim.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
