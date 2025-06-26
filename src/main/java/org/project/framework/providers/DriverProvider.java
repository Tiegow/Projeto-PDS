package org.project.framework.providers;

import java.util.List;

import org.project.easyf1.models.dto.DriverDTO;

public interface DriverProvider {
    DriverDTO getDriver(Integer driverNumber, Integer sessionKey);

    List<DriverDTO> getDrivers(Integer sessionKey);
}
