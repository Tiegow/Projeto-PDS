package org.project.easyf1.services;

import org.project.easyf1.client.CarClient;
import org.project.easyf1.exception.CarNotFoundException;
import org.project.easyf1.exception.SessionNotFoundException;
import org.project.easyf1.models.dto.CarDTO;
import org.project.easyf1.models.entity.Car;
import org.project.easyf1.models.entity.Session;
import org.project.easyf1.repositories.CarRepository;
import org.project.easyf1.repositories.SessionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Service
public class CarService {

    private final CarClient carClient;

    private final CarRepository carRepository;

    private final SessionRepository sessionRepository;

    public CarService(CarClient carClient, CarRepository carRepository, SessionRepository sessionRepository) {
        this.carClient = carClient;
        this.carRepository = carRepository;
        this.sessionRepository = sessionRepository;
    }

    public CarDTO getCar(Integer sessionKey, Integer driverNumber) {
        Car car = carRepository.findCarBySessionKeyAndDriveNumber(sessionKey, driverNumber);

        if(car == null) {
            throw new CarNotFoundException("Carro não encontrado!");
        }

        return new CarDTO(car);
    }

    public List<CarDTO> getAllCars(Integer sessionKey) {
        List<CarDTO> cars = carRepository.findCarsBySessionKey(sessionKey).
                stream().filter(Objects::nonNull).map(CarDTO::new).toList();

        if(cars.isEmpty()) {
            throw new CarNotFoundException("Nenhum carro foi encontrado!");
        }

        return cars;
    }

    public CarDTO getLastCarByDriver(Integer driverNumber){
        Session session = sessionRepository.findFirstByOrderByEndDateDesc();

        if(session == null) {
            throw new SessionNotFoundException("Sessão não encontrada!");
        }

        return getCar(session.getSessionKey(), driverNumber);
    }

}
