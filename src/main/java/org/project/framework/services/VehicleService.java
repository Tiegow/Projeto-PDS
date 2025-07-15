package org.project.framework.services;

import org.project.framework.exception.CarNotFoundException;
import org.project.framework.exception.SessionNotFoundException;
import org.project.framework.models.dto.VehicleDTO;
import org.project.framework.models.entity.Vehicle;
import org.project.framework.models.entity.Session;
import org.project.framework.repositories.SessionRepository;
import org.project.framework.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    private final SessionRepository sessionRepository;

    public VehicleService(VehicleRepository vehicleRepository, SessionRepository sessionRepository) {
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
