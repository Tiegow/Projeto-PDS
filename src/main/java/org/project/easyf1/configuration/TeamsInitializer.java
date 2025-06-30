package org.project.easyf1.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.easyf1.client.DriverClient;
import org.project.easyf1.models.dto.DriverDTO;
import org.project.easyf1.models.dto.SessionDTO;
import org.project.easyf1.models.dto.TeamDTO;
import org.project.easyf1.models.entity.Driver;
import org.project.easyf1.models.entity.Session;
import org.project.easyf1.models.entity.Team;
import org.project.easyf1.repositories.DriverRepository;
import org.project.easyf1.repositories.SessionRepository;
import org.project.easyf1.repositories.TeamRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TeamsInitializer {

    private final SessionRepository sessionRepository;

    private final TeamRepository teamRepository;

    private final DriverRepository driverRepository;

    private final DriverClient driverClient;

    private static final Map<String, String> TEAM_COLORS = new HashMap<>();

    static {
        TEAM_COLORS.put("Mercedes", "#00A99D");
        TEAM_COLORS.put("Red Bull Racing", "#0600EF");
        TEAM_COLORS.put("Ferrari", "#DC0000");
        TEAM_COLORS.put("McLaren", "#FF8700");
        TEAM_COLORS.put("Aston Martin", "#006F62");
        TEAM_COLORS.put("Alpine", "#0090FF");
        TEAM_COLORS.put("Williams", "#005AFF");
        TEAM_COLORS.put("RB", "#2B4562");
        TEAM_COLORS.put("Sauber", "#52E252");
        TEAM_COLORS.put("Haas F1 Team", "#B6B6B6");
    }


    public TeamsInitializer(SessionRepository sessionRepository, TeamRepository teamRepository, DriverRepository driverRepository, DriverClient driverClient) {
        this.sessionRepository = sessionRepository;
        this.teamRepository = teamRepository;
        this.driverRepository = driverRepository;
        this.driverClient = driverClient;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void populateNewestTeamsFromApi() throws UnsupportedEncodingException { // Renomeado para clareza na resposta anterior

        Session lastRaceSession = sessionRepository.findFirstByOrderByEndDateDesc();

        if (lastRaceSession == null) {
            // Considerar logar um aviso ou erro aqui também
            System.err.println("Nenhuma sessão race encontrada para popular as equipes."); // Exemplo simples de log
            return; // Retornar para evitar NullPointerException abaixo
            // Ou throw new RuntimeException("Nenhuma sessão race encontrada"); se for crítico para a inicialização
        }

        String url = "https://api.openf1.org/v1/drivers?session_key=" + lastRaceSession.getSessionKey();


        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        if (teamRepository.count() == 0) {
            try {
                List<Map<String, Object>> drivers = mapper.readValue(
                        restTemplate.getForObject(url, String.class),
                        new TypeReference<>() {
                        }
                );

                List<Team> teamsToSave = drivers.stream()
                        .filter(d -> d.get("team_name") != null)
                        .collect(Collectors.groupingBy(d -> d.get("team_name").toString()))
                        .entrySet().stream()
                        .map(entry -> {
                            String teamName = entry.getKey();
                            List<Map<String, Object>> teamDrivers = entry.getValue();
                            Integer first = teamDrivers.size() > 0 ? (Integer) teamDrivers.get(0).get("driver_number") : null;
                            Integer second = teamDrivers.size() > 1 ? (Integer) teamDrivers.get(1).get("driver_number") : null;

                            String teamColor = TEAM_COLORS.getOrDefault(teamName, "#CCCCCC");

                            TeamDTO dto = new TeamDTO();
                            dto.setTeamName(teamName);
                            dto.setFirstDriverNumber(first);
                            dto.setSecondDriverNumber(second);
                            dto.setTeamPoints(0);
                            dto.setTeamColor(teamColor);

                            return dto.getTeam();
                        })
                        .toList();

                teamRepository.saveAll(teamsToSave);
                // Considerar logar sucesso ou número de equipes salvas
                System.out.println(teamsToSave.size() + " equipes salvas/atualizadas.");


            } catch (Exception e) {
                // Logar o erro detalhadamente
                System.err.println("Erro ao buscar ou salvar dados dos times da API: " + e.getMessage());
                // Re-lançar como RuntimeException pode ser apropriado se a falha na inicialização for crítica
                // throw new RuntimeException("Erro ao buscar ou salvar dados dos times: " + e.getMessage(), e);
            }
        }
    }
    @EventListener(ApplicationReadyEvent.class)
    public void addALlDrivers  () {

        if (driverRepository.existsAnyDriver()) {
            return;
        }

        List<DriverDTO> driversDTO = driverClient.getAllDrivers();

        List<Driver> drivers = driversDTO.stream()
                .map(DriverDTO::getDriver)
                .filter(Objects::nonNull)
                .toList();

        driverRepository.saveAll(drivers);
    }
}
