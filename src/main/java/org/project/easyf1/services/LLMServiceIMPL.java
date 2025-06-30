package org.project.easyf1.services;


import org.project.easyf1.models.dto.DriverDTO;
import org.project.easyf1.models.entity.Driver;
import org.project.easyf1.models.entity.Team;
import org.project.easyf1.repositories.DriverRepository;
import org.project.easyf1.repositories.SessionRepository;
import org.project.easyf1.repositories.TeamRepository;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.project.easyf1.client.LiveSessionClient;
import org.project.easyf1.models.dto.GeminiRequest;
import org.project.easyf1.models.dto.PositionDTO;
import org.project.easyf1.models.entity.Meeting;
import org.project.easyf1.models.entity.Session;
import org.project.easyf1.repositories.MeetingRepository;
import org.project.easyf1.services.Interfaces.LLMService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LLMServiceIMPL implements LLMService {

    private MeetingRepository meetingRepository;

    private LiveSessionClient liveSessionClient;

    private SessionRepository sessionRepository;

    private TeamRepository teamRepository;

    @Value("${spring.ai.vertex.ai.gemini.credentials-uri}")
    private String apiKey;

    public LLMServiceIMPL(MeetingRepository meetingRepository, LiveSessionClient liveSessionClient, SessionRepository sessionRepository, TeamRepository teamRepository) {
        this.meetingRepository = meetingRepository;
        this.liveSessionClient = liveSessionClient;
        this.sessionRepository = sessionRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public String WhosGonnaWin(Integer meetingID) {

        Optional<Meeting> meeting = meetingRepository.findById(meetingID);
        List<Session> sessions;

        if(meeting.isPresent()) {
            sessions = meeting.get().getSessions();
        } else {
            return "Não foi possivel encontrar o GP";
        }

        HashMap<String, List<PositionDTO>> positions = new HashMap<>();

        StringBuilder sb = new StringBuilder();

        for (Session session : sessions) {
            List<PositionDTO> position = liveSessionClient.getPositions(session.getSessionKey());
            positions.put(session.getSessionName(), position);
        }

        sb.append("Previsão de corrida F1\n");

        sb.append("GP: ").append(sessions.get(0).getLocation()).append("\n");
        sb.append("Resultados dos treinos e qualificação:\n");

        for (Map.Entry<String, List<PositionDTO>> entry : positions.entrySet()) {
            if(entry.getKey().equals("race")) {
                break;
            }
            sb.append(entry.getKey()).append(": ");
            List<PositionDTO> posList = entry.getValue();

            for (int i = 0; i < Math.min(10, posList.size()); i++) {
                PositionDTO pos = posList.get(i);
                sb.append((i + 1)).append(" - #").append(pos.getDriverNumber()).append("\n");
            }
            sb.append("\n");
        }

        sb.append("Com base nesses resultados, quem deve vencer a corrida?\n");
        sb.append("Pesquise na internet quem está melhor na temporada ou outras informações que pode te ajudar nisso.\n");
        sb.append("Retorne o mais provavel top 5, em um texto pequeno, apenas com o top 5\n");
        sb.append("Não fale nada sobre falta de informação, quero que você mande um texto afirmativo com certeza, é uma mensagem direta para usuario do sistema, quero uma mensagem limpa e clara apenas falando os pilotos com melhor chance de vitória.");

        return callGeminiAPI(sb.toString());
    }

    @Override
    public HashMap<String, Integer> RankingDrivers() {
        StringBuilder sb = new StringBuilder();

       Session session = sessionRepository.findFirstByOrderByEndDateDesc();

        List<DriverDTO> drivers = session.getDrivers().stream().map(DriverDTO::new).toList();
        List<String> driversNames = new ArrayList<>(20);

        sb.append("Fale o ranking dos pilotos, no atual campeonato 2025 da formula, que irei mandar a seguir, me envie no seguinte modelo, e coloque os pontos com 3 digitos, caso não tenha, complete com 0 a esquerda (Com os mesmos nomes que eu te enviar IMPORTANTE!): 'NOME:PONTOS'\n");

        for (DriverDTO driver : drivers) {
            sb.append(driver.getFirstName()).append(" ").append(driver.getLastName()).append("\n");
            driversNames.add(driver.getFirstName() + " " + driver.getLastName());
        }

        HashMap<String, Integer> ranking = new HashMap<>();

        String response = callGeminiAPI(sb.toString());

        for (String name : driversNames) {
            int inicio = response.indexOf(name) + name.length() + 1;

            ranking.put(name, Integer.parseInt(response.substring(inicio, inicio + 3)));
        }

        return ranking;
    }

    @Override
    public  HashMap<String, Integer> RankingTeams() {

        StringBuilder sb = new StringBuilder();

        List<Team> drivers = teamRepository.getAll();
        List<String> teamNames = new ArrayList<>(10);

        sb.append("Fale o ranking dos times, no atual campeonato da formula, que irei mandar a seguir, me envie no seguinte modelo, e coloque os pontos com 3 digitos, caso não tenha, complete com 0 a esquerda (Com os mesmos nomes que eu te enviar IMPORTANTE!): 'TIME:PONTOS'\n");

        for (Team team : drivers) {
            sb.append(team.getTeamName()).append("\n");
            teamNames.add(team.getTeamName());
        }

        HashMap<String, Integer> ranking = new HashMap<>();

        String response = callGeminiAPI(sb.toString());

        for (String name : teamNames) {
            int inicio = response.indexOf(name) + name.length() + 1;

            ranking.put(name, Integer.parseInt(response.substring(inicio, inicio + 3)));
        }

        return ranking;
    }

    @Override
    public String detailsDriver(Integer driverNumber) {

        String sb = "Quero que você me forneça um objeto JSON com os seguintes dados de um piloto de Fórmula 1, com base no número do piloto. O JSON deve conter informações pessoais, desempenho na temporada, dados do carro e informações da equipe. Forneça os dados reais mais recentes disponíveis para o piloto com o número " + driverNumber + " em 2025. A estrutura deve seguir exatamente este modelo (em snake_case):\n" +
                "{\n" +
                "  \"first_name\": \"\",\n" +
                "  \"last_name\": \"\",\n" +
                "  \"driver_number\": 0,\n" +
                "  \"country_code\": \"\",\n" +
                "  \"headshot_url\": \"\",\n" +
                "  \"team_name\": \"\",\n" +
                "  \"team_colour\": \"\",\n" +
                "  \"team_full_name\": \"\",\n" +
                "  \"team_base\": \"\",\n" +
                "  \"team_principal\": \"\",\n" +
                "  \"team_championship_position\": 0,\n" +
                "  \"championship_position\": 0,\n" +
                "  \"points\": 0,\n" +
                "  \"best_position\": 0,\n" +
                "  \"worst_position\": 0,\n" +
                "  \"victories\": 0,\n" +
                "  \"car_model\": \"\",\n" +
                "  \"engine\": \"\",\n" +
                "  \"power_hp\": 0,\n" +
                "  \"weight_kg\": 0\n" +
                "}" +
                "\n" +
                "Substitua todos os valores com os dados reais do piloto e do carro. Caso alguma informação exata não esteja disponível, use o valor mais aproximado com base na temporada atual.";

        return callGeminiAPI(sb);
    }

    @Override
    public String DetailsTeam(String teamName) {
        String prompt = "Quero que você me forneça um objeto JSON com os seguintes dados de um time de Fórmula 1, com base no nome do time. " +
                "O JSON deve conter informações relevantes sobre a equipe, incluindo nome completo, base, chefe de equipe, posição no campeonato de construtores e cor da equipe. " +
                "Forneça os dados reais mais recentes disponíveis para o time \"" + teamName + "\" em 2025. " +
                "A estrutura deve seguir exatamente este modelo (em snake_case):\n" +
                "{\n" +
                "  \"team_name\": \"\",\n" +
                "  \"team_full_name\": \"\",\n" +
                "  \"team_base\": \"\",\n" +
                "  \"team_principal\": \"\",\n" +
                "  \"team_championship_position\": 0,\n" +
                "  \"team_colour\": \"\"\n" +
                "  \"team_points\": \"\"\n" +
                "}\n" +
                "Substitua todos os valores com os dados reais do time. Caso alguma informação exata não esteja disponível, use o valor mais aproximado com base na temporada atual.";

        return callGeminiAPI(prompt);
    }

    public String callGeminiAPI(String prompt) {

        GeminiRequest.Part part = new GeminiRequest.Part(prompt);
        GeminiRequest.Content content = new GeminiRequest.Content(List.of(part));
        GeminiRequest request = new GeminiRequest(List.of(content));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<GeminiRequest> entity = new HttpEntity<>(request, headers);

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + apiKey;

        ResponseEntity<String> response = new RestTemplate().postForEntity(url, entity, String.class);

        return response.getBody();
    }
}
