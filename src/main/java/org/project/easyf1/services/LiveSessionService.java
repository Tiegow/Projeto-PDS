package org.project.easyf1.services;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.project.easyf1.client.DriverClient;
import org.project.easyf1.client.LiveSessionClient;
import org.project.easyf1.models.dto.DriverDTO;
import org.project.easyf1.models.dto.PositionDTO;
import org.project.easyf1.models.dto.WeatherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class LiveSessionService {
    private final TodaySessionProvider todaySessionProvider;
    private final LivePositionsProvider livePositionsProvider;

    private final SimpMessagingTemplate messagingTemplate;
    private final LiveSessionClient liveSessionClient;
    private final DriverClient driverClient;

    private Integer sessionKey = null;
    private List<DriverDTO> sessionDrivers = null;

    @Autowired
    public LiveSessionService(SimpMessagingTemplate messagingTemplate, LiveSessionClient liveSessionClient, TodaySessionProvider todaySessionProvider, LivePositionsProvider livePositionsProvider, DriverClient driverClient) {
        this.todaySessionProvider = todaySessionProvider;
        this.messagingTemplate = messagingTemplate;
        this.liveSessionClient = liveSessionClient;
        this.livePositionsProvider = livePositionsProvider;
        this.driverClient = driverClient;
    }

    @PostConstruct
    public void init() {
        initSessionData();
    }

    // Tenta buscar os dados iniciais da sessão.
    // É chamado caso algum dado essencial da sessão ainda não estiver definido.
    private void initSessionData() {
        try {
            sessionKey = todaySessionProvider.getTodaySession().getSessionKey();
        } catch (Exception e) {
            sessionKey = null;
            System.err.println("Sem sessoes hoje");
        }

        try {
            sessionDrivers = driverClient.getDrivers(sessionKey);
        } catch (Exception e) {
            sessionDrivers = null;
            System.err.println("Erro ao buscar pilotos para esta corrida: " + e);
        }
    }

    public void sendWeather() {
        if (sessionKey == null) { 
            initSessionData();
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

    public void sendPositions() {
        if (sessionKey == null) { 
            initSessionData();
            return;
        } 

        try {
            PositionDTO[] positionsArray = liveSessionClient.getPositions(sessionKey);

            if (positionsArray.length > 0) {
                // Atualiza o Map auxiliar com (driverNumber -> position)
                livePositionsProvider.updatePositions(positionsArray);
                Map<Integer, Integer> updatedPositions = livePositionsProvider.getDriversPositions();

                if (sessionDrivers != null) {
                    // Ordena os pilotos com base na posição no mapa
                    sessionDrivers.sort(Comparator.comparingInt(driver ->
                        updatedPositions.getOrDefault(driver.getDriverNumber(), Integer.MAX_VALUE)
                    ));

                    // Envia os pilotos ordenados diretamente ao cliente
                    messagingTemplate.convertAndSend("/topic/positions", sessionDrivers);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar posições: " + e.getMessage());
        }
    }

    @Scheduled(fixedRate = 30000)
    public void scheduledSendPositions() {
        sendPositions(); 
    }
}