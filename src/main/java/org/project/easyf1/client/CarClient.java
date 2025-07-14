package org.project.easyf1.client;


import org.project.easyf1.models.dto.VehicleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "car", url = "https://api.openf1.org/v1/car_data")
public interface CarClient {

    @GetMapping
    VehicleDTO getCar(@RequestParam("driver_number") Integer driverNumber, @RequestParam("session_key") Integer sessionKey);
}
