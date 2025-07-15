package org.project.easyNascar.services;


import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.project.framework.models.entity.Meeting;
import org.project.framework.models.entity.Session;
import org.project.framework.repositories.MeetingRepository;
import org.project.easyNascar.client.EasyNascarLiveSessionClient;
import org.project.framework.models.dto.GeminiRequest;
import org.project.framework.models.dto.PositionDTO;
import org.project.framework.services.LLMService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EasyNascarLLMServiceIMPL implements LLMService {

    private MeetingRepository meetingRepository;

    private EasyNascarLiveSessionClient liveSessionClient;

    @Value("${spring.ai.vertex.ai.gemini.credentials-uri}")
    private String apiKey;

    public EasyNascarLLMServiceIMPL(MeetingRepository meetingRepository, EasyNascarLiveSessionClient liveSessionClient) {
        this.meetingRepository = meetingRepository;
        this.liveSessionClient = liveSessionClient;
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
