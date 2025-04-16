package org.project.easyf1.repositories;

import org.project.easyf1.models.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Session findFirstByOrderByEndDateDesc();
}