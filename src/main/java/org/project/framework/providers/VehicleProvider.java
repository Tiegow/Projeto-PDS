package org.project.framework.providers;

import org.project.framework.models.dto.VehicleDTO;


public interface VehicleProvider {

    VehicleDTO getCar(Integer driverNumber, Integer sessionKey);

}
