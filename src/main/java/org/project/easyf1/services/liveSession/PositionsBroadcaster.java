package org.project.easyf1.services.liveSession;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.project.easyf1.client.DriverClient;
import org.project.easyf1.client.LiveSessionClient;
import org.project.easyf1.exception.PositionsUpdateException;
import org.project.easyf1.models.dto.DriverDTO;
import org.project.easyf1.models.dto.PositionDTO;
import org.project.framework.services.liveSession.ISessionDataBroadcaster;
import org.project.framework.services.liveSession.LivePositionsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PositionsBroadcaster implements ISessionDataBroadcaster {

    private final LiveSessionClient liveSessionClient;
    private final DriverClient driverClient;
    private final LivePositionsHelper livePositionsHelper;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public PositionsBroadcaster(LiveSessionClient liveSessionClient, SimpMessagingTemplate messagingTemplate, LivePositionsHelper livePositionsHelper, DriverClient driverClient) {
        this.liveSessionClient = liveSessionClient;
        this.messagingTemplate = messagingTemplate;
        this.livePositionsHelper = livePositionsHelper;
        this.driverClient = driverClient;
    }    
    
    @Override
    @Scheduled(fixedRate = 25000)
    public void broadcast(Integer sessionKey) {
        try {
            List<DriverDTO> sessionDrivers = driverClient.getDrivers(sessionKey);
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
