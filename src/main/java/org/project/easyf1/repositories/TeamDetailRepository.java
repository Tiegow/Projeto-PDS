package org.project.easyf1.repositories;

import org.project.easyf1.models.entity.TeamDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamDetailRepository extends JpaRepository<TeamDetail, Long> {

    @Query("SELECT team FROM TeamDetail team WHERE team.teamName = :teamName ORDER BY team.date DESC")
    TeamDetail findByTeamName(String teamName);
}
