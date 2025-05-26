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



}
