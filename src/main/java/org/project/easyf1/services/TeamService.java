package org.project.easyf1.services;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.project.easyf1.models.dto.TeamDTO;
import org.project.easyf1.models.entity.Session;
import org.project.easyf1.models.entity.Team;
import org.project.easyf1.repositories.SessionRepository;
import org.project.easyf1.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final SessionRepository sessionRepository;

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(SessionRepository sessionRepository, TeamRepository teamRepository) {
        this.sessionRepository = sessionRepository;
        this.teamRepository = teamRepository;
    }

    @PostConstruct
    public void getNewestTeams() throws UnsupportedEncodingException {

        Session lastRaceSession = sessionRepository.findFirstBySessionTypeOrderByEndDateDesc("Race");

        if(lastRaceSession == null){
            throw new RuntimeException("Nenhuma sessão race encontrada");
        }

        String url =  "https://api.openf1.org/v1/drivers?session_key=" + lastRaceSession.getSessionKey();


        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<Map<String, Object>> drivers = mapper.readValue(
                    restTemplate.getForObject(url, String.class),
                    new TypeReference<>() {}
            );

            List<Team> teamsToSave = drivers.stream()
                    .filter(d -> d.get("team_name") != null)
                    .collect(Collectors.groupingBy(d -> d.get("team_name").toString()))
                    .entrySet().stream()
                    .map(entry -> {
                        List<Map<String, Object>> teamDrivers = entry.getValue();
                        Integer first = teamDrivers.size() > 0 ? (Integer) teamDrivers.get(0).get("driver_number") : null;
                        Integer second = teamDrivers.size() > 1 ? (Integer) teamDrivers.get(1).get("driver_number") : null;

                        TeamDTO dto = new TeamDTO();
                        dto.setTeamName(entry.getKey());
                        dto.setFirstDriverNumber(first);
                        dto.setSecondDriverNumber(second);
                        dto.setTeamPoints(0);

                        return dto.getTeam();
                    })
                    .toList();

            teamRepository.saveAll(teamsToSave);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar ou salvar dados dos times: " + e.getMessage(), e);
        }
    }
}
