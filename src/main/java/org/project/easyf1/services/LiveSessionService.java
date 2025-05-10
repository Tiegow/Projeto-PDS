package org.project.easyf1.services;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import org.project.easyf1.client.LiveSessionClient;
import org.project.easyf1.models.dto.WeatherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class LiveSessionService {
    private final TodaySessionProvider todaySessionProvider;
    private final SimpMessagingTemplate messagingTemplate;
    private final LiveSessionClient liveSessionClient;

    private String sessionKey = null;

    @Autowired
    public LiveSessionService(SimpMessagingTemplate messagingTemplate, LiveSessionClient liveSessionClient, TodaySessionProvider todaySessionProvider) {
        this.todaySessionProvider = todaySessionProvider;
        this.messagingTemplate = messagingTemplate;
        this.liveSessionClient = liveSessionClient;
    }

    @PostConstruct
    public void init() {
        try {
            sessionKey = todaySessionProvider.getTodaySession().getSessionKey().toString();
        } catch (Exception e) {
            System.err.println("Sem sessoes hoje");
        }
    }

    public void sendWeather() {

        if (sessionKey == null) { 
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