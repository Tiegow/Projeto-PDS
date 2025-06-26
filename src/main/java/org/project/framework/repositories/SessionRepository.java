package org.project.framework.repositories;

import java.time.OffsetDateTime;
import java.util.List;

import org.project.easyf1.models.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Session findFirstByOrderByEndDateDesc();

    @Query("SELECT s FROM Session s where s.meeting.meetingKey = :meetingKey")
    List<Session> findAllByMeetingKey(@Param("meetingKey") Integer meetingKey);

    @Query("SELECT s FROM Session s where s.meeting.meetingKey = :meetingKey ORDER BY s.endDate DESC")
    Session findFirstByMeetingKeyOrderByEndDateDesc(@Param("meetingKey") Integer meetingKey);

    @Query("""
      SELECT s FROM Session s
      WHERE s.startDate >= :startOfDay
        AND s.startDate < :startOfNextDay
        AND s.endDate >= :now
      ORDER BY s.startDate ASC
      """)
      Session findTodaySession(
          @Param("startOfDay") OffsetDateTime startOfDay,
          @Param("startOfNextDay") OffsetDateTime startOfNextDay,
          @Param("now") OffsetDateTime now
      );

    boolean existsSessionBySessionKey(Long sessionKey);
}
