package org.project.easyf1.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import jakarta.annotation.PostConstruct;
import org.project.easyf1.exception.DriverDetailError;
import org.project.easyf1.models.dto.*;
import org.project.easyf1.models.entity.DriverDetail;
import org.project.easyf1.models.entity.Session;
import org.project.easyf1.models.entity.Team;
import org.project.easyf1.models.entity.TeamDetail;
import org.project.easyf1.repositories.DriverRepository;
import org.project.easyf1.repositories.SessionRepository;
import org.project.easyf1.repositories.TeamDetailRepository;
import org.project.easyf1.repositories.TeamRepository;
import org.project.easyf1.services.Interfaces.LLMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final SessionRepository sessionRepository;

    private final TeamRepository teamRepository;

    private final TeamDetailRepository teamDetailRepository;

    private final LLMService llmService;
    private final DriverRepository driverRepository;

    @Autowired
    public TeamService(SessionRepository sessionRepository, TeamRepository teamRepository, TeamDetailRepository teamDetailRepository, LLMService llmService, DriverRepository driverRepository) {
        this.sessionRepository = sessionRepository;
        this.teamRepository = teamRepository;
        this.teamDetailRepository = teamDetailRepository;
        this.llmService = llmService;
        this.driverRepository = driverRepository;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team getTeamById(Long id) {
        return teamRepository.findById(id).orElse(null);
    }

    public Team getTeamByName(String name) {
        return teamRepository.findAll().stream()
                           .filter(t -> t.getTeamName().equalsIgnoreCase(name))
                           .findFirst()
                           .orElse(null);
    }

    public TeamDetailDTO getDetailTeam(String teamName) {

        TeamDetail teamDetail = teamDetailRepository.findByTeamName(teamName);

        Team time = teamRepository.findByTeamName(teamName).get();

        java.sql.Date date = Date.valueOf(LocalDate.now());

        if(teamDetail != null && Math.abs(ChronoUnit.MONTHS.between(teamDetail.getDate().toLocalDate(), date.toLocalDate())) < 1) {
            teamDetail.setDriver1(new DriverDTO(driverRepository.findFirstByDriverNumber(time.getFirstDriverNumber())));
            teamDetail.setDriver2(new DriverDTO(driverRepository.findFirstByDriverNumber(time.getSecondDriverNumber())));
            return new TeamDetailDTO(teamDetail);
        }

        String dados = llmService.DetailsTeam(teamName).replaceAll("(?s)```json|```", "").trim();

        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        TeamDetailDTO toReturn;

        try {
            GeminiResponse request = mapper.readValue(dados, GeminiResponse.class);
            toReturn = mapper.readValue(request.getCandidates().getFirst().getContent().getParts().getFirst().getText(), TeamDetailDTO.class);
        } catch (JsonProcessingException e) {
            throw new DriverDetailError(e.getMessage());
        }

        TeamDetail team = toReturn.getTeam();
        team.setDate(date);
        teamDetailRepository.save(team);

        toReturn.setDriver1(new DriverDTO(driverRepository.findFirstByDriverNumber(time.getFirstDriverNumber())));
        toReturn.setDriver2(new DriverDTO(driverRepository.findFirstByDriverNumber(time.getSecondDriverNumber())));

        return toReturn;


    }
}