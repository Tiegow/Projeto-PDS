package org.project.easyf1.services.liveSession;

import java.util.Collections;
import java.util.List;

import org.project.easyf1.client.LiveSessionClient;
import org.project.easyf1.exception.EventsNotFountException;
import org.project.easyf1.models.dto.RaceControlDTO;
import org.project.framework.services.liveSession.ISessionDataBroadcaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;

public class RaceEventsBroadcaster implements ISessionDataBroadcaster {
    
    private final LiveSessionClient liveSessionClient;
    private final SimpMessagingTemplate messagingTemplate;    

    @Autowired
    RaceEventsBroadcaster(LiveSessionClient liveSessionClient, SimpMessagingTemplate messagingTemplate) {
        this.liveSessionClient = liveSessionClient;
        this.messagingTemplate = messagingTemplate;        
    }

    @Override
    @Scheduled(fixedRate = 30000)
    public void broadcast(Integer sessionKey) {
        try {
            List<RaceControlDTO> raceEvents = liveSessionClient.getRaceEvents(sessionKey);

            if (!raceEvents.isEmpty()) {
                Collections.reverse(raceEvents);
                messagingTemplate.convertAndSend("/topic/race_events", raceEvents);
            }            
        } catch (Exception e) {
            throw new EventsNotFountException();
        }
    }
}
