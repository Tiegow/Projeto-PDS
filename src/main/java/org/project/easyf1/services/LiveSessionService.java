package org.project.easyf1.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.project.easyf1.client.LiveSessionClient;
import org.project.easyf1.models.dto.WeatherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class LiveSessionService {
    private final SessionService sessionService;
    private final SimpMessagingTemplate messagingTemplate;
    private final LiveSessionClient liveSessionClient;

    @Autowired
    public LiveSessionService(SimpMessagingTemplate messagingTemplate, SessionService sessionService, LiveSessionClient liveSessionClient) {
        this.sessionService = sessionService;
        this.messagingTemplate = messagingTemplate;
        this.liveSessionClient = liveSessionClient;
    }

    public void sendWeather() {
        String sessionKey = sessionService.getTodaySession().getSessionKey().toString();
        if (sessionKey == null) {
            System.err.println("Sem sessoes hoje"); 
            return;
        } 

        try {
            // Chamada à API
            WeatherDTO[] weatherArray = liveSessionClient.getWeather(sessionKey);

            if (weatherArray.length > 0) {
                WeatherDTO latestWeather = weatherArray[weatherArray.length -1];
                // Envia os dados para o cliente conectado
                messagingTemplate.convertAndSend("/topic/weather", latestWeather);
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
