package org.project.easyf1.client;


import java.util.List;

import org.project.easyf1.models.dto.DriverDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = "driver", url = "https://api.openf1.org/v1/drivers")
public interface DriverClient {



    @GetMapping()
    DriverDTO getDriver(@RequestParam("driver_number") Integer driverNumber, @RequestParam("session_key") Integer sessionKey);

    @GetMapping()
    List<DriverDTO> getAllDrivers();

    @GetMapping()
    List<DriverDTO> getDrivers(@RequestParam("session_key") Integer sessionKey);
}
