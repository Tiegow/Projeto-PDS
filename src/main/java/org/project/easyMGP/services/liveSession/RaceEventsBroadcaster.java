package org.project.easyMGP.services.liveSession;

import java.util.Collections;
import java.util.List;

import org.project.easyMGP.client.EasyMGPLiveSessionClient;
import org.project.easyMGP.models.dto.liveSession.RaceControlDTOImpl;
import org.project.framework.exception.EventsNotFountException;
import org.project.framework.services.TodaySessionHelper;
import org.project.framework.services.liveSession.SessionDataBroadcaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RaceEventsBroadcaster extends SessionDataBroadcaster {
    
    private final EasyMGPLiveSessionClient liveSessionClient;
    private final SimpMessagingTemplate messagingTemplate;    
    private final TodaySessionHelper todaySessionHelper;

    @Autowired
    RaceEventsBroadcaster(EasyMGPLiveSessionClient liveSessionClient, SimpMessagingTemplate messagingTemplate, TodaySessionHelper todaySessionHelper) {
        this.liveSessionClient = liveSessionClient;
        this.messagingTemplate = messagingTemplate;        
        this.todaySessionHelper = todaySessionHelper;
    }

    @Override
    @Scheduled(fixedRate = 30000)
    public void broadcast() {
        if (this.sessionKey == null) {
            sessionKey = todaySessionHelper.getTodaySession().getSessionKey();
        }
                
        try {
            List<RaceControlDTOImpl> raceEvents = liveSessionClient.getRaceEvents(sessionKey);

            if (!raceEvents.isEmpty()) {
                Collections.reverse(raceEvents);
                messagingTemplate.convertAndSend("/topic/race_events", raceEvents);
            }            
        } catch (Exception e) {
            throw new EventsNotFountException();
        }
    }
}
