package org.project.easyf1.services.liveSession;

import java.util.List;

import org.project.easyf1.client.LiveSessionClient;
import org.project.easyf1.exception.WeatherNotFoundException;
import org.project.easyf1.models.dto.WeatherDTO;
import org.project.framework.services.liveSession.ISessionDataBroadcaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherBroadcaster implements ISessionDataBroadcaster {

    private final LiveSessionClient liveSessionClient;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WeatherBroadcaster(LiveSessionClient liveSessionClient, SimpMessagingTemplate messagingTemplate) {
        this.liveSessionClient = liveSessionClient;
        this.messagingTemplate = messagingTemplate;
    }    

    @Override
    @Scheduled(fixedRate = 60000)
    public void broadcast(Integer sessionKey) {
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
