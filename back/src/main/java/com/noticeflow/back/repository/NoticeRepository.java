package com.noticeflow.back.repository;

import com.noticeflow.back.domain.Category;
import com.noticeflow.back.domain.Notice;
import com.noticeflow.back.domain.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long>, JpaSpecificationExecutor<Notice> {

    Page<Notice> findByOrganization(Organization organization, Pageable pageable);

    Page<Notice> findByOrganizationAndCategory(Organization organization, Category category, Pageable pageable);

    Page<Notice> findByOrganizationAndTitleContainingOrOrganizationAndContentContaining(
            Organization org1, String titleKeyword,
            Organization org2, String contentKeyword,
            Pageable pageable);

    @Query("SELECT n FROM Notice n WHERE n.organization = :org AND n.category = :category " +
           "AND (n.title LIKE %:keyword% OR n.content LIKE %:keyword%)")
    Page<Notice> findByOrganizationAndCategoryAndKeyword(
            @Param("org") Organization organization,
            @Param("category") Category category,
            @Param("keyword") String keyword,
            Pageable pageable);

    List<Notice> findByOrganizationOrderByCreatedAtDesc(Organization organization);

    boolean existsByCategory(Category category);

    Long countByOrganization(Organization organization);
}