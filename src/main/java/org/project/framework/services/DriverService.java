package org.project.framework.services;

import org.project.framework.providers.DriverProvider;
import org.project.framework.exception.SessionNotFoundException;
import org.project.framework.models.entity.Driver;
import org.project.framework.models.entity.Session;
import org.project.framework.repositories.DriverRepository;
import org.project.framework.models.dto.DriverDTO;
import org.project.framework.repositories.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    private final DriverProvider driverClient;

    private final SessionRepository sessionRepository;

    public DriverService(DriverRepository driverRepository, DriverProvider driverClient, SessionRepository sessionRepository) {
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
