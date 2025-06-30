package org.project.easyf1.repositories;

import org.project.easyf1.models.entity.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {
    List<Ranking> findAllByDate(GregorianCalendar date);

    List<Ranking> findAllByDateOrderByPoints(Date date);
}
