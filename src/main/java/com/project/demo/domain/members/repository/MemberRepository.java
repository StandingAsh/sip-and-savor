package com.project.demo.domain.members.repository;

import com.project.demo.domain.members.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // JPA Repository
    boolean existsByUserId(String userId);

    Member findByUserId(String userId);
}
