package org.project.easyf1.services;


import jakarta.annotation.PostConstruct;
import org.project.easyf1.client.DriverClient;
import org.project.easyf1.exception.SessionNotFoundException;
import org.project.easyf1.models.dto.DriverDTO;
import org.project.easyf1.models.entity.Driver;
import org.project.easyf1.models.entity.Session;
import org.project.easyf1.repositories.DriverRepository;
import org.project.framework.repositories.SessionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    private final DriverClient driverClient;

    private final SessionRepository sessionRepository;

    public DriverService(DriverRepository driverRepository, DriverClient driverClient, SessionRepository sessionRepository) {
        this.driverRepository = driverRepository;
        this.driverClient = driverClient;
        this.sessionRepository = sessionRepository;
    }

    public List<DriverDTO> getLastSessionDrivers() {
        Session lastSession = sessionRepository.findFirstByOrderByEndDateDesc();

        if(lastSession == null) {
            throw new SessionNotFoundException("Sessão não encontrada!");
        }

        return getDriversBySessionKey(lastSession.getSessionKey());
    }

    public List<DriverDTO> getDriversBySessionKey(Integer sessionKey) {

        List<Driver> drivers = driverRepository.findAllBySession_SessionKey(sessionKey);

        if(drivers.isEmpty()) {
            return driverClient.getDrivers(sessionKey);
        } else {
            return drivers.stream().map(DriverDTO::new).collect(Collectors.toList());
        }
    }

    public DriverDTO getDriver(Integer sessionKey, Integer driverNumber) {
        return driverClient.getDriver(sessionKey, driverNumber);
    }

    public DriverDTO detailDriver(Integer driverNumber) {

        Driver driver = driverRepository.findFirstByDriverNumber(driverNumber);

        return new DriverDTO(driver);
    }

}
