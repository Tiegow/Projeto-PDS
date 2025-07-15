package org.project.framework.controllers;


import org.project.framework.models.dto.VehicleDTO;
import org.project.framework.services.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/car")
public class VehicleController {

    private final VehicleService vehicleService;
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping()
    public ResponseEntity<VehicleDTO> getCar(@RequestParam("sessionKey") Integer sessionKey,
                                             @RequestParam("driverNumber") Integer driverNumber) {
        VehicleDTO vehicle = vehicleService.getCar(sessionKey, driverNumber);

        return ResponseEntity.ok(vehicle);
    }

    @GetMapping("all")
    public ResponseEntity<List<VehicleDTO>> getAllCars(@RequestParam("sessionKey") Integer sessionkey) {
        List<VehicleDTO> vehicles = vehicleService.getAllCars(sessionkey);

        return ResponseEntity.ok(vehicles);

    }

    @GetMapping("driver")
    public ResponseEntity<VehicleDTO> getLastCarByDriver(@RequestParam("driverNumber") Integer driverNumber) {
        VehicleDTO vehicle = vehicleService.getLastCarByDriver(driverNumber);

        return ResponseEntity.ok(vehicle);
    }



}
