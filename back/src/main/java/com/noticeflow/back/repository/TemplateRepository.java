package com.noticeflow.back.repository;

import com.noticeflow.back.domain.Organization;
import com.noticeflow.back.domain.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

    Optional<Template> findByIsDefaultTrue();

    List<Template> findAllByOrderByCreatedAtDesc();

    @Query("SELECT COUNT(t) FROM Template t WHERE t.isDefault = true OR t.organization = :organization")
    Long countByIsDefaultTrueOrOrganization(@Param("organization") Organization organization);
}