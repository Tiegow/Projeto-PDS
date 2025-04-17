package org.project.easyf1.repositories;

import java.time.OffsetDateTime;
import java.util.List;

import org.project.easyf1.models.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import feign.Param;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Session findFirstByOrderByEndDateDesc();

    List<Session> findAllByMeetingKey(Integer meetingKey);

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
}