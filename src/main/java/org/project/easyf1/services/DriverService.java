package org.project.easyf1.services;


import jakarta.annotation.PostConstruct;
import org.project.easyf1.client.DriverClient;
import org.project.easyf1.models.dto.DriverDTO;
import org.project.easyf1.models.entity.Driver;
import org.project.easyf1.repositories.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    private final DriverClient driverClient;

    public DriverService(DriverRepository driverRepository, DriverClient driverClient) {
        this.driverRepository = driverRepository;
        this.driverClient = driverClient;
    }

    @PostConstruct
    public void addALlDrivers  () {

        if (driverRepository.existsAnyDriver()) {
            return;
        }

        List<DriverDTO> driversDTO = driverClient.getAllDrivers();

        List<Driver> drivers = driversDTO.stream()
                .map(DriverDTO::getDriver)
                .filter(Objects::nonNull)
                .toList();

        driverRepository.saveAll(drivers);
    }

}
