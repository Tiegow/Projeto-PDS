package org.project.easyf1.services.liveSession;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.project.easyf1.client.EasyF1DriverProvider;
import org.project.easyf1.client.EasyF1LiveSessionClient;
import org.project.framework.exception.PositionsUpdateException;
import org.project.framework.models.dto.DriverDTO;
import org.project.framework.models.dto.PositionDTO;
import org.project.framework.services.TodaySessionHelper;
import org.project.framework.services.liveSession.SessionDataBroadcaster;
import org.project.framework.services.liveSession.LivePositionsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PositionsBroadcaster extends SessionDataBroadcaster {

    private final EasyF1LiveSessionClient liveSessionClient;

    private final EasyF1DriverProvider driverProvider;
    private final LivePositionsHelper livePositionsHelper;
    private final TodaySessionHelper todaySessionHelper;

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public PositionsBroadcaster(
        EasyF1LiveSessionClient liveSessionClient, 
        SimpMessagingTemplate messagingTemplate, 
        LivePositionsHelper livePositionsHelper, 
        EasyF1DriverProvider driverProvider,
        TodaySessionHelper todaySessionHelper
        ) {
        this.liveSessionClient = liveSessionClient;
        this.messagingTemplate = messagingTemplate;
        this.livePositionsHelper = livePositionsHelper;
        this.driverProvider = driverProvider;
        this.todaySessionHelper = todaySessionHelper;
    }    
    
    @Override
    @Scheduled(fixedRate = 25000)
    public void broadcast() {
        if (this.sessionKey == null) {
            sessionKey = todaySessionHelper.getTodaySession().getSessionKey();
        }

        try {
            List<DriverDTO> sessionDrivers = driverProvider.getDrivers(sessionKey);
            List<PositionDTO> positionsArray = liveSessionClient.getPositions(sessionKey);

            if (!positionsArray.isEmpty()) {
                livePositionsHelper.updatePositions(positionsArray);
                Map<Integer, Integer> updatedPositions = livePositionsHelper.getDriversPositions();

                sessionDrivers.sort(Comparator.comparingInt(driver ->
                    updatedPositions.getOrDefault(driver.getDriverNumber(), Integer.MAX_VALUE)
                ));

                messagingTemplate.convertAndSend("/topic/positions", sessionDrivers);
            }            
        } catch (Exception e) {
            throw new PositionsUpdateException();
        }        
    }
}
