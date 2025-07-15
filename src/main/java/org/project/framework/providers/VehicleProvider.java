package org.project.framework.providers;

import org.project.easyf1.models.dto.VehicleDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface VehicleProvider {

    VehicleDTO getCar(Integer driverNumber, Integer sessionKey);

}
