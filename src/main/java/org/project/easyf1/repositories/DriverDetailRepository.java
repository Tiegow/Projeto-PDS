package org.project.easyf1.repositories;

import org.project.easyf1.models.dto.DriverDetailDTO;
import org.project.easyf1.models.entity.DriverDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface DriverDetailRepository extends JpaRepository<DriverDetail, Integer> {
    @Query("SELECT dd FROM DriverDetail dd WHERE dd.driverNumber = :number ORDER BY dd.date DESC")
    DriverDetail findLastByDate(Integer number);
}
