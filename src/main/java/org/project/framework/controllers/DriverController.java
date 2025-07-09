package org.project.framework.controllers;

import org.project.easyf1.models.entity.Session;
import org.project.framework.models.dto.DriverDTO;
import org.project.framework.repositories.SessionRepository;
import org.project.framework.services.DriverService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/driver")
public class DriverController {

    private final DriverService driverService;


    private final SessionRepository sessionRepository;

    public DriverController(DriverService driverService, SessionRepository sessionRepository) {
        this.driverService = driverService;
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

        List<DriverDTO> drivers = driverService.getDriversBySessionKey(sessionKey);

        return ResponseEntity.ok(drivers);
    }

    @GetMapping("")
    public ResponseEntity<DriverDTO> getDriver(@RequestParam("sessionKey") Integer sessionKey, @RequestParam("driverNumber") Integer driverNumber) {
        return ResponseEntity.ok(driverService.getDriver(sessionKey, driverNumber));
    }

    @GetMapping("details")
    public ResponseEntity<DriverDTO> detailDriver(@RequestParam("driver_number") Integer driverNumber) {

        DriverDTO driverDTO = driverService.detailDriver(driverNumber);

        return ResponseEntity.ok(driverDTO);
    }
}
