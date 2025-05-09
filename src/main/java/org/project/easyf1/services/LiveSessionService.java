package org.project.easyf1.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.project.easyf1.models.dto.WeatherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class LiveSessionService {
    private final SessionService sessionService;
    private final SimpMessagingTemplate messagingTemplate;
    private final RestTemplate restTemplate;

    @Autowired
    public LiveSessionService(SimpMessagingTemplate messagingTemplate, SessionService sessionService) {
        this.sessionService = sessionService;
        this.messagingTemplate = messagingTemplate;
        this.restTemplate = new RestTemplate();
    }

    public void sendWeather() {
        String sessionKey = sessionService.getTodaySession().getSessionKey().toString();
        if (sessionKey == null) {
            System.err.println("Sem sessoes hoje"); 
            return;
        } 

        try {
            // Chamada à API
            String url = "https://api.openf1.org/v1/weather?session_key=" + sessionKey;
            ResponseEntity<WeatherDTO[]> response = restTemplate.getForEntity(url, WeatherDTO[].class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                WeatherDTO[] weatherArray = response.getBody();

                if (weatherArray.length > 0) {
                    WeatherDTO latestWeather = weatherArray[weatherArray.length -1];
                    // Envia os dados para o cliente conectado
                    messagingTemplate.convertAndSend("/topic/weather", latestWeather);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar clima: " + e.getMessage());
        }
    }

    @Scheduled(fixedRate = 60000)
    public void scheduledSendWeather() {
        sendWeather(); 
    }
}
