package org.project.easyMGP.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.project.framework.providers.TeamProvider;
import org.project.framework.models.entity.Meeting;
import org.project.framework.models.dto.TeamDTO;
import org.project.framework.models.entity.Driver;
import org.project.framework.models.entity.Session;
import org.project.framework.models.entity.Team;
import org.project.framework.models.dto.GeminiResponse;
import org.project.framework.repositories.DriverRepository;
import org.project.framework.models.dto.DriverDTO;
import org.project.framework.repositories.MeetingRepository;
import org.project.framework.repositories.SessionRepository;
import org.project.framework.repositories.TeamRepository;
import org.project.framework.services.LLMService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TeamsInitializer {

    private final SessionRepository sessionRepository;

    private final TeamRepository teamRepository;

    private final DriverRepository driverRepository;

    private final TeamProvider driverClient;

    private final MeetingRepository meetingRepository;

    private final LLMService llmService;

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


    public TeamsInitializer(SessionRepository sessionRepository, TeamRepository teamRepository, DriverRepository driverRepository, TeamProvider driverClient, LLMService llmService, MeetingRepository meetingRepository) {
        this.sessionRepository = sessionRepository;
        this.teamRepository = teamRepository;
        this.driverRepository = driverRepository;
        this.llmService = llmService;
        this.driverClient = driverClient;
        this.meetingRepository = meetingRepository;
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
                        criarTimesFalsos(),
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

                try {
                    teamRepository.saveAll(teamsToSave);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    private String criarTimesFalsos() {

        StringBuilder s = new StringBuilder("Quero que você me retorne um array JSON contendo 20 objetos representando times do MotoGP. ");
        s.append("Cada objeto deve conter os seguintes campos: ");
        s.append("team_name (nome do time), ");
        s.append("first_driver_number (número do primeiro piloto do time, entre 0 e 99), ");
        s.append("second_driver_number (número do segundo piloto do time, diferente do primeiro, também entre 0 e 99), ");
        s.append("team_points (pontuação total acumulada pelo time na temporada atual, valor inteiro), ");
        s.append("team_color (uma string representando a cor primária do time, ex: 'azul', 'vermelho', 'preto', ou em inglês se preferir, ex: 'blue', 'red'). ");
        s.append("Evite repetições de nomes ou números entre os times. ");
        s.append("Retorne apenas o JSON, sem explicações ou comentários.");

        String retorno = llmService.callGeminiAPI(s.toString());

        ObjectMapper objectMapper = new ObjectMapper();
        GeminiResponse geminiResponse = null;
        try{
            geminiResponse = objectMapper.readValue(retorno, GeminiResponse.class);
        } catch (Exception e){
            e.printStackTrace();
        }

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String rawJson = geminiResponse.getCandidates()
                .getFirst()
                .getContent()
                .getParts()
                .getFirst()
                .getText();

       return rawJson.replaceAll("(?s)```json\\s*", "")
                .replaceAll("```", "")
                .trim();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void addALlDrivers () {

        if (driverRepository.existsAnyDriver()) {
            return;
        }

        List<String> idsMeetings = pegarIdsMeetings();
        List<String> idsSessions = pegarIdsSessions();
        List<String> idsEquipes = pegarIdsEquipes();

        List<DriverDTO> driversDTO = this.listarPilotos(idsMeetings, idsSessions, idsEquipes);

        List<Driver> drivers = driversDTO.stream()
                .map(DriverDTO::getDriver)
                .filter(Objects::nonNull)
                .toList();

        try{
            driverRepository.saveAll(drivers);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private List<String> pegarIdsEquipes() {
        List<String> idsEquipes = new ArrayList<>();
        int count = 0;
        for(Team tean : teamRepository.findAll()){
            idsEquipes.add(tean.getTeamName());
            if(count == 20){
                break;
            } else {
                count++;
            }
        }

        return idsEquipes;
    }

    private List<String> pegarIdsMeetings() {
        List<String> idsEquipes = new ArrayList<>();
        int count = 0;
        for(Meeting meeting : meetingRepository.findAll()){
            idsEquipes.add(String.valueOf(meeting.getMeetingKey()));
            if(count == 20){
                break;
            } else {
                count++;
            }
        }

        return idsEquipes;
    }

    private List<String> pegarIdsSessions() {
        List<String> idsEquipes = new ArrayList<>();
        int count = 0;
        for(Session session : sessionRepository.findAll()){
            idsEquipes.add(String.valueOf(session.getSessionKey()));
            if(count == 20){
                break;
            } else {
                count++;
            }
        }

        return idsEquipes;
    }

    private List<DriverDTO> listarPilotos(List<String> idsMeeting, List<String> idsSessions, List<String> nomesEquipes){

        StringBuilder s = new StringBuilder("Quero que você me retorne um array JSON contendo 20 objetos representando pilotos da MotoGP. ");
        s.append("Cada objeto deve conter os seguintes campos: ");
        s.append("broadcast_name (nome usado para transmissão, como 'J. Doe' ou 'M. Johnson'), ");
        s.append("country_code (código do país no padrão alpha-3, como 'USA' ou 'BRA'), ");
        s.append("driver_number (número inteiro único entre 0 e 99 que identifica o piloto), ");
        s.append("first_name (primeiro nome do piloto), ");
        s.append("last_name (sobrenome do piloto), ");
        s.append("headshot_url (URL da imagem do rosto do piloto), ");
        s.append("meeting_key (número inteiro representando o evento principal de corrida), ");
        s.append("name_acronym (sigla do nome do piloto, como 'JDO' para John Doe), ");
        s.append("session_key (número inteiro da sessão em que o piloto participou), ");
        s.append("team_colour (cor primária da equipe, como 'blue', 'red', etc), ");
        s.append("team_name (nome da equipe do piloto) valores restritos a(retorne 2 por equipe): " + String.join(", ", nomesEquipes) + ", ");
        s.append("id (identificador único do piloto para ranking), ");
        s.append("name (nome completo do piloto para ranking), ");
        s.append("abbr (abreviação do nome para ranking, igual a name_acronym), ");
        s.append("image (mesmo valor de headshot_url). ");
        s.append("Todos os dados devem ser realistas e consistentes entre si. ");
        s.append("Os valores de meeting_key devem estar restritos a: " + String.join(", ", idsMeeting) + ". ");
        s.append("Os valores de session_key devem estar restritos a retorne 10 pilotos por session_key: " + String.join(", ", idsSessions) + ". ");
        s.append("Evite repetir nomes, números ou imagens. Retorne apenas o JSON, sem explicações ou comentários.");


        String retorno = llmService.callGeminiAPI(s.toString());

        ObjectMapper objectMapper = new ObjectMapper();
        GeminiResponse geminiResponse = null;
        try{
            geminiResponse = objectMapper.readValue(retorno, GeminiResponse.class);
        } catch (Exception e){
            e.printStackTrace();
        }

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String rawJson = geminiResponse.getCandidates()
                .getFirst()
                .getContent()
                .getParts()
                .getFirst()
                .getText();

        String cleanJson = rawJson.replaceAll("(?s)```json\\s*", "")
                .replaceAll("```", "")
                .trim();

        try{
            return objectMapper.readValue(cleanJson, new TypeReference<List<DriverDTO>>() {});
        } catch (Exception e){
            return null;
        }
    }
}
