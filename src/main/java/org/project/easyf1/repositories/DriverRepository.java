package org.project.easyf1.repositories;

import org.project.easyf1.models.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    @Query("SELECT CASE WHEN EXISTS (SELECT 1 FROM Driver d) THEN true ELSE false END")
    boolean existsAnyDriver();
}
