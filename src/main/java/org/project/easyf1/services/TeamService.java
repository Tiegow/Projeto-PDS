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
            String response = restTemplate.getForObject(url, String.class);
            List<Map<String, Object>> driverData = mapper.readValue(response, new TypeReference<>() {});

            Map<String, List<Map<String, Object>>> groupedByTeam = driverData.stream()
                    .filter(d -> d.get("team_name") != null)
                    .collect(Collectors.groupingBy(d -> d.get("team_name").toString()));

            List<Team> teamsToSave = new ArrayList<>();

            for (Map.Entry<String, List<Map<String, Object>>> entry : groupedByTeam.entrySet()) {
                String teamName = entry.getKey();
                List<Map<String, Object>> drivers = entry.getValue();

                Integer firstDriver = drivers.size() > 0 ? (Integer) drivers.get(0).get("driver_number") : null;
                Integer secondDriver = drivers.size() > 1 ? (Integer) drivers.get(1).get("driver_number") : null;

                TeamDTO teamDTO = new TeamDTO();
                teamDTO.setTeamName(teamName);
                teamDTO.setFirstDriverNumber(firstDriver);
                teamDTO.setSecondDriverNumber(secondDriver);
                teamDTO.setTeamPoints(0);

                Team team = teamDTO.getTeam();
                teamsToSave.add(team);
            }

            teamRepository.saveAll(teamsToSave);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar ou salvar dados dos times: " + e.getMessage(), e);
        }






    }
}
