package org.project.easyNascar.client;


import org.project.framework.models.dto.VehicleDTO;
import org.project.framework.providers.VehicleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class EasyNascarMotoProvider implements VehicleProvider {

    private final EasyNascarMotoClient easyf1CarClient;


    @Autowired
    public EasyNascarMotoProvider(EasyNascarMotoClient easyf1CarClient) {this.easyf1CarClient = easyf1CarClient;}

    @Override
    public VehicleDTO getCar(Integer driverNumber, Integer sessionKey) {
        return easyf1CarClient.getCar(driverNumber, sessionKey);
    }

    @FeignClient(name = "moto", url = "")
    interface EasyNascarMotoClient {

        @GetMapping
        VehicleDTO getCar(@RequestParam("driver_number") Integer driverNumber, @RequestParam("session_key") Integer sessionKey);
    }
}
