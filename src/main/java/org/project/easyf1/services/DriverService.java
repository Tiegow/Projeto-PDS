package org.project.easyf1.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import jakarta.annotation.PostConstruct;
import org.project.easyf1.client.DriverClient;
import org.project.easyf1.exception.DriverDetailError;
import org.project.easyf1.exception.SessionNotFoundException;
import org.project.easyf1.models.dto.DriverDTO;
import org.project.easyf1.models.dto.DriverDetailDTO;
import org.project.easyf1.models.dto.GeminiRequest;
import org.project.easyf1.models.dto.GeminiResponse;
import org.project.easyf1.models.entity.Driver;
import org.project.easyf1.models.entity.DriverDetail;
import org.project.easyf1.models.entity.Session;
import org.project.easyf1.repositories.DriverDetailRepository;
import org.project.easyf1.repositories.DriverRepository;
import org.project.easyf1.repositories.SessionRepository;
import org.project.easyf1.services.Interfaces.LLMService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    private final DriverClient driverClient;

    private final SessionRepository sessionRepository;

    private final LLMService llmService;

    private final DriverDetailRepository driverDetailRepository;

    public DriverService(DriverRepository driverRepository, DriverClient driverClient, SessionRepository sessionRepository, LLMService llmService, DriverDetailRepository driverDetailRepository) {
        this.driverRepository = driverRepository;
        this.driverClient = driverClient;
        this.sessionRepository = sessionRepository;
        this.llmService = llmService;
        this.driverDetailRepository = driverDetailRepository;
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

    public DriverDetailDTO detailDriver(Integer number) {

        DriverDetail driver = driverDetailRepository.findLastByDate(number);

        Date date = Date.valueOf(LocalDate.now());

        if(driver != null && Math.abs(ChronoUnit.MONTHS.between(driver.getDate().toLocalDate(), date.toLocalDate())) < 1) {
            return new DriverDetailDTO(driver);
        }

        String dados = llmService.detailsDriver(number).replaceAll("(?s)```json|```", "").trim();

        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);


        DriverDetailDTO toReturn = new DriverDetailDTO();

        try {
            GeminiResponse request = mapper.readValue(dados, GeminiResponse.class);
            toReturn = mapper.readValue(request.getCandidates().getFirst().getContent().getParts().getFirst().getText(), DriverDetailDTO.class);
        } catch (JsonProcessingException e) {
            throw new DriverDetailError(e.getMessage());
        }

        DriverDetail driverDetail = toReturn.getDriverDetail();
        driverDetail.setDate(date);
        driverDetailRepository.save(driverDetail);

        return toReturn;
    }

}
