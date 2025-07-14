package org.project.easyf1.services;

import org.project.easyf1.client.CarClient;
import org.project.easyf1.repositories.VehicleRepository;
import org.project.framework.exception.CarNotFoundException;
import org.project.framework.exception.SessionNotFoundException;
import org.project.easyf1.models.dto.VehicleDTO;
import org.project.easyf1.models.entity.Vehicle;
import org.project.easyf1.models.entity.Session;
import org.project.framework.repositories.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class VehicleService {

    private final CarClient carClient;

    private final VehicleRepository vehicleRepository;

    private final SessionRepository sessionRepository;

    public VehicleService(CarClient carClient, VehicleRepository vehicleRepository, SessionRepository sessionRepository) {
        this.carClient = carClient;
        this.vehicleRepository = vehicleRepository;
        this.sessionRepository = sessionRepository;
    }

    public VehicleDTO getCar(Integer sessionKey, Integer driverNumber) {
        Vehicle vehicle = vehicleRepository.findCarBySessionKeyAndDriveNumber(sessionKey, driverNumber);

        if(vehicle == null) {
            throw new CarNotFoundException("Carro não encontrado!");
        }

        return new VehicleDTO(vehicle);
    }

    public List<VehicleDTO> getAllCars(Integer sessionKey) {
        List<VehicleDTO> cars = vehicleRepository.findCarsBySessionKey(sessionKey).
                stream().filter(Objects::nonNull).map(VehicleDTO::new).toList();

        if(cars.isEmpty()) {
            throw new CarNotFoundException("Nenhum carro foi encontrado!");
        }

        return cars;
    }

    public VehicleDTO getLastCarByDriver(Integer driverNumber){
        Session session = sessionRepository.findFirstByOrderByEndDateDesc();

        if(session == null) {
            throw new SessionNotFoundException("Sessão não encontrada!");
        }

        return getCar(session.getSessionKey(), driverNumber);
    }

}
