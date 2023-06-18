package com.shoes.ordering.system.domain.member.dataaccess.repository;

import com.shoes.ordering.system.domain.member.dataaccess.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberJpaRepository extends JpaRepository<MemberEntity, UUID> {

    Optional<MemberEntity> findByMemberId(UUID memberId);
}
