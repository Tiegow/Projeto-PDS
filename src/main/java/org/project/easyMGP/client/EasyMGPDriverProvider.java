package org.project.easyMGP.client;

import java.util.List;

import org.project.framework.models.dto.DriverDTO;
import org.project.framework.providers.DriverProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class EasyMGPDriverProvider implements DriverProvider {
    
    private final EasyF1DriverClient easyf1Client;

    @Autowired
    public EasyMGPDriverProvider(EasyF1DriverClient easyf1Client) {
        this.easyf1Client = easyf1Client;
    }

    public List<DriverDTO> getAllDrivers() {
        return easyf1Client.getAllDrivers();
    }

    @Override
    public DriverDTO getDriver(Integer driverNumber, Integer sessionKey) {
        return easyf1Client.getDriver(driverNumber, sessionKey);
    }

    @Override
    public List<DriverDTO> getDrivers(Integer sessionKey) {
        return easyf1Client.getDrivers(sessionKey);
    }

    @FeignClient(name = "openf1-driver", url = "")
    interface EasyF1DriverClient {
        @GetMapping()
        List<DriverDTO> getDrivers(@RequestParam("session_key") Integer sessionKey);

        @GetMapping()
        DriverDTO getDriver(@RequestParam("driver_number") Integer driverNumber, @RequestParam("session_key") Integer sessionKey);    
        
        @GetMapping()
        List<DriverDTO> getAllDrivers();
    }    
}
