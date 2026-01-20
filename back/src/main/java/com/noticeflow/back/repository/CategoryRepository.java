package com.noticeflow.back.repository;

import com.noticeflow.back.domain.Category;
import com.noticeflow.back.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByOrganizationAndIsVisibleTrueOrderByCreatedAtDesc(Organization organization);

    List<Category> findByOrganizationOrderByCreatedAtDesc(Organization organization);

    Optional<Category> findByIdAndOrganization(Long id, Organization organization);

    boolean existsByIdAndIsVisibleFalse(Long id);

    long countByOrganization(Organization organization);
}