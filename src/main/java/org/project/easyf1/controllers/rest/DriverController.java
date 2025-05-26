package org.project.easyf1.controllers.rest;


import org.project.easyf1.client.DriverClient;
import org.project.easyf1.models.dto.DriverDTO;
import org.project.easyf1.models.entity.Driver;
import org.project.easyf1.models.entity.Session;
import org.project.easyf1.repositories.DriverRepository;
import org.project.easyf1.repositories.SessionRepository;
import org.project.easyf1.services.DriverService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/driver")
public class DriverController {

    private final DriverService driverService;

    private final DriverRepository driverRepository;

    private final DriverClient driverClient;

    private final SessionRepository sessionRepository;

    public DriverController(DriverService driverService, DriverRepository driverRepository, DriverClient driverClient, SessionRepository sessionRepository) {
        this.driverService = driverService;
        this.driverRepository = driverRepository;
        this.driverClient = driverClient;
        this.sessionRepository = sessionRepository;
    }

    @GetMapping("lastSession")
    public ResponseEntity<List<DriverDTO>> getLastSessionDrivers() {
        Session lastSession = sessionRepository.findFirstByOrderByEndDateDesc();

        if(lastSession == null) {
            return ResponseEntity.noContent().build();
        }

        return getDriversBySessionKey(lastSession.getSessionKey());
    }

    @GetMapping("session")
    public ResponseEntity<List<DriverDTO>> getDriversBySessionKey(@RequestParam("sessionKey") Integer sessionKey) {

        List<Driver> drivers = driverRepository.findAllBySession_SessionKey(sessionKey);

        if(drivers.isEmpty()) {
            return ResponseEntity.ok(driverClient.getDrivers(sessionKey));
        } else {
            return ResponseEntity.ok(drivers.stream().map(DriverDTO::new).collect(Collectors.toList()));
        }
    }

    @GetMapping("")
    public ResponseEntity<DriverDTO> getDriver(@RequestParam("sessionKey") Integer sessionKey, @RequestParam("driverNumber") Integer driverNumber) {
        return ResponseEntity.ok(driverClient.getDriver(sessionKey, driverNumber));
    }

    @GetMapping("details")
    public ResponseEntity<DriverDTO> detailDriver(@RequestParam("driver_number") Integer driverNumber) {

        Driver driver = driverRepository.findFirstByDriverNumber(driverNumber);

        DriverDTO driverDTO = new DriverDTO(driver);

        return ResponseEntity.ok(driverDTO);
    }
}
