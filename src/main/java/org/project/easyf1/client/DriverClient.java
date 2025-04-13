package org.project.easyf1.client;


import org.project.easyf1.models.dto.DriverDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "driver", url = "https://api.openf1.org/v1")
public interface DriverClient {

    @GetMapping("drivers")
    DriverDTO getDriver(@RequestParam("driver_number") Integer driverNumber, @RequestParam("session_key") Integer sessionKey);


}
