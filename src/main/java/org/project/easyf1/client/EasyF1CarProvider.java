package org.project.easyf1.client;


import org.project.easyf1.models.dto.VehicleDTO;
import org.project.framework.providers.VehicleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
public class EasyF1CarProvider implements VehicleProvider {

    private final EasyF1VehicleClient easyf1CarClient;


    @Autowired
    public EasyF1CarProvider(EasyF1VehicleClient easyf1CarClient) {this.easyf1CarClient = easyf1CarClient;}

    @Override
    public VehicleDTO getCar(Integer driverNumber, Integer sessionKey) {
        return easyf1CarClient.getCar(driverNumber, sessionKey);
    }

    @FeignClient(name = "car", url = "https://api.openf1.org/v1/car_data")
    interface EasyF1VehicleClient {

        @GetMapping
        VehicleDTO getCar(@RequestParam("driver_number") Integer driverNumber, @RequestParam("session_key") Integer sessionKey);
    }
}
