package org.project.framework.repositories;

import org.project.easyf1.models.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    @Query("SELECT c FROM Vehicle c WHERE c.driverNumber = :driverNumber AND c.session.sessionKey = :sessionKey")
    Vehicle findCarBySessionKeyAndDriveNumber(@Param("sessionKey")Integer sessionKey, @Param("driverNumber") Integer driverNumber);

    @Query("SELECT c FROM Vehicle c WHERE c.session.sessionKey = :sessionKey")
    List<Vehicle> findCarsBySessionKey(@Param("sessionKey")Integer sessionKey);
}
