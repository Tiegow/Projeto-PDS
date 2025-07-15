package org.project.easyNascar.services.liveSession;

import java.util.List;

import org.project.easyNascar.client.EasyNascarLiveSessionClient;
import org.project.framework.exception.WeatherNotFoundException;
import org.project.framework.models.dto.WeatherDTO;
import org.project.framework.services.TodaySessionHelper;
import org.project.framework.services.liveSession.SessionDataBroadcaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherBroadcaster extends SessionDataBroadcaster {

    private final EasyNascarLiveSessionClient liveSessionClient;
    private final SimpMessagingTemplate messagingTemplate;
    private final TodaySessionHelper todaySessionHelper;

    @Autowired
    public WeatherBroadcaster(EasyNascarLiveSessionClient liveSessionClient, SimpMessagingTemplate messagingTemplate, TodaySessionHelper todaySessionHelper) {
        this.liveSessionClient = liveSessionClient;
        this.messagingTemplate = messagingTemplate;
        this.todaySessionHelper = todaySessionHelper;
    }    

    @Override
    @Scheduled(fixedRate = 60000)
    public void broadcast() {
        if (this.sessionKey == null) {
            sessionKey = todaySessionHelper.getTodaySession().getSessionKey();
        }
                
        try {
            List<WeatherDTO> weatherArray = liveSessionClient.getWeather(sessionKey);

            if (!weatherArray.isEmpty()) {
                WeatherDTO latestWeather = weatherArray.get(weatherArray.size() - 1);
                messagingTemplate.convertAndSend("/topic/weather", latestWeather);
            }            
        } catch (Exception e) {
            throw new WeatherNotFoundException();
        }
    }
    
}
