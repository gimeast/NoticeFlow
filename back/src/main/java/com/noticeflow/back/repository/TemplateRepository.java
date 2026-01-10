package com.noticeflow.back.repository;

import com.noticeflow.back.domain.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

    Optional<Template> findByIsDefaultTrue();

    List<Template> findAllByOrderByCreatedAtDesc();
}