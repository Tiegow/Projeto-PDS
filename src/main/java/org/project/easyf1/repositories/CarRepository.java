package org.project.easyf1.repositories;

import org.project.easyf1.models.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    @Query("SELECT c FROM Car c WHERE c.driverNumber = :driverNumber AND c.session.sessionKey = :sessionKey")
    Car findCarBySessionKeyAndDriveNumber(@Param("sessionKey")Integer sessionKey,@Param("driverNumber") Integer driverNumber);

    @Query("SELECT c FROM Car c WHERE c.session.sessionKey = :sessionKey")
    List<Car> findCarsBySessionKey(@Param("sessionKey")Integer sessionKey);
}
