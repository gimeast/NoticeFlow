package com.noticeflow.back.repository;

import com.noticeflow.back.domain.NormalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NormalUserRepository extends JpaRepository<NormalUser, Long> {

    Optional<NormalUser> findByUserId(Long userId);
}