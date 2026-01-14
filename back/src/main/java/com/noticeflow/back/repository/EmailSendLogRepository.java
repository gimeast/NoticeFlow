package com.noticeflow.back.repository;

import com.noticeflow.back.domain.EmailSendLog;
import com.noticeflow.back.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailSendLogRepository extends JpaRepository<EmailSendLog, Long> {

    @Query("SELECT COUNT(DISTINCT e.notice.id) FROM EmailSendLog e WHERE e.notice.organization = :organization AND e.success = true")
    Long countDistinctSentNoticesByOrganization(@Param("organization") Organization organization);
}