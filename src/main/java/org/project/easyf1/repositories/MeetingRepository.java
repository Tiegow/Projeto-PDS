package org.project.easyf1.repositories;

import org.project.easyf1.models.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer>{

    Meeting findFirstByOrderByStartDateDesc();
}
