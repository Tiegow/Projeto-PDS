package org.project.easyf1.services;

import org.project.easyf1.client.CarClient;
import org.project.easyf1.repositories.CarRepository;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    private final CarClient carClient;

    private final CarRepository carRepository;

    public CarService(CarClient carClient, CarRepository carRepository) {
        this.carClient = carClient;
        this.carRepository = carRepository;
    }

}
