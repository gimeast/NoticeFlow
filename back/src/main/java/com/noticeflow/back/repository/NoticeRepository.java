package com.noticeflow.back.repository;

import com.noticeflow.back.domain.Category;
import com.noticeflow.back.domain.Notice;
import com.noticeflow.back.domain.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Page<Notice> findByOrganizationOrderByCreatedAtDesc(Organization organization, Pageable pageable);

    List<Notice> findByOrganizationOrderByCreatedAtDesc(Organization organization);

    boolean existsByCategory(Category category);
}