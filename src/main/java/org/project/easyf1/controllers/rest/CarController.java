package org.project.easyf1.controllers.rest;

import org.project.easyf1.client.CarClient;
import org.project.easyf1.models.dto.CarDTO;
import org.project.easyf1.models.entity.Car;
import org.project.easyf1.models.entity.Session;
import org.project.easyf1.repositories.CarRepository;
import org.project.easyf1.repositories.SessionRepository;
import org.project.easyf1.services.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/car")
public class CarController {

    private final CarRepository carRepository;

    private final CarClient carClient;

    private final CarService carService;

    private final SessionRepository sessionRepository;

    public CarController(CarRepository carRepository, CarClient carClient, CarService carService, SessionRepository sessionRepository) {
        this.carRepository = carRepository;
        this.carClient = carClient;
        this.carService = carService;
        this.sessionRepository = sessionRepository;
    }

    @GetMapping()
    public ResponseEntity<CarDTO> getCar(@RequestParam("sessionKey") Integer sessionKey, @RequestParam("driverNumber") Integer driverNumber) {
        Car car = carRepository.findCarBySessionKeyAndDriveNumber(sessionKey, driverNumber);
        return ResponseEntity.ok(new CarDTO(car));
    }

    @GetMapping("all")
    public ResponseEntity<List<CarDTO>> getAllCars(@RequestParam("sessionKey") Integer sessionKey) {
        List<CarDTO> cars = carRepository.findCarsBySessionKey(sessionKey).
                stream().filter(Objects::nonNull).map(CarDTO::new).toList();

        return ResponseEntity.ok(cars);
    }

    @GetMapping("driver")
    public ResponseEntity<CarDTO> getLastCarByDriver(@RequestParam("driverNumber") Integer driverNumber){
        Session session = sessionRepository.findFirstByOrderByEndDateDesc();

        return getCar(session.getSessionKey(), driverNumber);
    }
}
