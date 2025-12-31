package com.noticeflow.back.repository;

import com.noticeflow.back.domain.ConnectionCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectionCodeRepository extends JpaRepository<ConnectionCode, Long> {

    Optional<ConnectionCode> findByCodeAndIsActiveTrue(String code);

    List<ConnectionCode> findByOrganizationId(Long organizationId);

    boolean existsByCode(String code);
}